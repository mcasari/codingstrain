#!/usr/bin/env python3
"""Build WordPress WXR 1.2 from article HTML with Gutenberg block markup."""

from __future__ import annotations

import re
import sys
from datetime import datetime, timezone
from pathlib import Path
from xml.sax.saxutils import escape

ARTICLE_HTML = Path(__file__).resolve().parent / (
    "java-17-sealed-classes-closed-hierarchies-with-permits.html"
)
OUTPUT_WXR = Path(__file__).resolve().parent / (
    "java-17-sealed-classes-closed-hierarchies-with-permits.wxr.xml"
)

TITLE = "Java 17 Sealed Classes: Close Your Inheritance Graph with permits"
SLUG = "java-17-sealed-classes-permits"
EXCERPT = (
    "Sealed types let you declare exactly which classes may extend a base type. "
    "The compiler enforces permits and requires every permitted subtype to be "
    "final, sealed, or non-sealed."
)
CATEGORIES = ["Java"]
TAGS = [
    "Java 17",
    "sealed classes",
    "permits",
    "object-oriented design",
    "pattern matching",
]


def cdata(s: str) -> str:
    return s.replace("]]>", "]]]]><![CDATA[>")

def slugify(text: str) -> str:
    s = text.lower().strip()
    s = re.sub(r"[^a-z0-9]+", "-", s)
    return s.strip("-") or "tag"


def html_to_gutenberg(html: str) -> str:
    """Convert article HTML into Gutenberg block-serialized post content."""
    html = re.sub(r"^\s*<!--.*?-->\s*", "", html, count=1, flags=re.DOTALL).strip()
    # Split before block-level opening tags (keep the tag on each chunk).
    chunks = re.split(
        r"(?=<(?:p|h[1-6]|pre|ul|ol|table|blockquote|hr)\b)",
        html,
        flags=re.IGNORECASE,
    )
    blocks: list[str] = []

    for chunk in chunks:
        chunk = chunk.strip()
        if not chunk:
            continue

        m_h = re.match(r"^<h([1-6])([^>]*)>(.*)</h\1>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_h:
            level = m_h.group(1)
            inner = m_h.group(3).strip()
            blocks.append(
                f'<!-- wp:heading {{"level":{level}}} -->\n'
                f'<h{level} class="wp-block-heading">{inner}</h{level}>\n'
                f"<!-- /wp:heading -->"
            )
            continue

        m_p = re.match(r"^<p([^>]*)>(.*)</p>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_p:
            inner = m_p.group(2).strip()
            blocks.append(
                "<!-- wp:paragraph -->\n"
                f"<p>{inner}</p>\n"
                "<!-- /wp:paragraph -->"
            )
            continue

        m_pre = re.match(
            r"^<pre([^>]*)><code([^>]*)>(.*)</code></pre>\s*$",
            chunk,
            re.DOTALL | re.IGNORECASE,
        )
        if m_pre:
            attrs = m_pre.group(2) or ""
            lang_m = re.search(r'class="[^"]*language-(\w+)', attrs)
            lang = lang_m.group(1) if lang_m else ""
            code = m_pre.group(3)
            code = code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
            lang_attr = f' lang="{lang}" class="language-{lang}"' if lang else ""
            blocks.append(
                "<!-- wp:code -->\n"
                f'<pre class="wp-block-code"><code{lang_attr}>{code}</code></pre>\n'
                "<!-- /wp:code -->"
            )
            continue

        m_pre_plain = re.match(r"^<pre([^>]*)>(.*)</pre>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_pre_plain:
            code = m_pre_plain.group(2)
            code = code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
            blocks.append(
                "<!-- wp:code -->\n"
                f'<pre class="wp-block-code"><code>{code}</code></pre>\n'
                "<!-- /wp:code -->"
            )
            continue

        m_ul = re.match(r"^<ul([^>]*)>(.*)</ul>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_ul:
            inner = m_ul.group(2).strip()
            blocks.append(
                "<!-- wp:list -->\n"
                f'<ul class="wp-block-list">{inner}</ul>\n'
                "<!-- /wp:list -->"
            )
            continue

        if chunk.lower().startswith("<table"):
            blocks.append(
                "<!-- wp:html -->\n"
                f"{chunk}\n"
                "<!-- /wp:html -->"
            )
            continue

        # Fallback: classic freeform chunk
        blocks.append(
            "<!-- wp:freeform -->\n"
            f"{chunk}\n"
            "<!-- /wp:freeform -->"
        )

    return "\n\n".join(blocks)


def build_wxr(gutenberg_content: str) -> str:
    now = datetime.now(timezone.utc)
    post_date = now.strftime("%Y-%m-%d %H:%M:%S")
    pub_rfc = now.strftime("%a, %d %b %Y %H:%M:%S +0000")
    base = "https://codingstrain.local"
    post_id = 61001
    link = f"{base}/?p={post_id}"

    cat_xml = "\n".join(
        f'    <category domain="category" nicename="{slugify(c)}">'
        f"<![CDATA[{cdata(c)}]]></category>"
        for c in CATEGORIES
    )
    tag_xml = "\n".join(
        f'    <category domain="post_tag" nicename="{slugify(t)}">'
        f"<![CDATA[{cdata(t)}]]></category>"
        for t in TAGS
    )

    return f"""<?xml version="1.0" encoding="UTF-8" ?>
<!--
  WordPress WXR 1.2 — Tools → Import → WordPress → upload this file.
  Content uses Gutenberg block markup (paragraph, heading, code, list, html).
  Re-import replaces draft if you delete the previous import first.
-->
<rss version="2.0"
  xmlns:excerpt="http://wordpress.org/export/1.2/excerpt/"
  xmlns:content="http://wordpress.org/export/1.2/content/"
  xmlns:wfw="http://wellformedweb.org/CommentAPI/1/"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:wp="http://wordpress.org/export/1.2/">

<channel>
  <title>codingstrain — Java 17 Sealed Classes</title>
  <link>{escape(base)}</link>
  <description>codingstrain article export</description>
  <pubDate>{pub_rfc}</pubDate>
  <language>en-US</language>
  <wp:wxr_version>1.2</wp:wxr_version>
  <wp:base_site_url>{escape(base)}</wp:base_site_url>
  <wp:base_blog_url>{escape(base)}</wp:base_blog_url>

  <wp:author>
    <wp:author_id>1</wp:author_id>
    <wp:author_login><![CDATA[codingstrain]]></wp:author_login>
    <wp:author_email><![CDATA[author@example.com]]></wp:author_email>
    <wp:author_display_name><![CDATA[codingstrain]]></wp:author_display_name>
    <wp:author_first_name><![CDATA[]]></wp:author_first_name>
    <wp:author_last_name><![CDATA[]]></wp:author_last_name>
  </wp:author>

  <item>
    <title><![CDATA[{cdata(TITLE)}]]></title>
    <link>{escape(link)}</link>
    <pubDate>{pub_rfc}</pubDate>
    <dc:creator><![CDATA[codingstrain]]></dc:creator>
    <guid isPermaLink="false">{escape(link)}</guid>
    <description></description>
    <content:encoded><![CDATA[{cdata(gutenberg_content)}]]></content:encoded>
    <excerpt:encoded><![CDATA[{cdata(EXCERPT)}]]></excerpt:encoded>
    <wp:post_id>{post_id}</wp:post_id>
    <wp:post_date><![CDATA[{post_date}]]></wp:post_date>
    <wp:post_date_gmt><![CDATA[{post_date}]]></wp:post_date_gmt>
    <wp:post_modified><![CDATA[{post_date}]]></wp:post_modified>
    <wp:post_modified_gmt><![CDATA[{post_date}]]></wp:post_modified_gmt>
    <wp:comment_status><![CDATA[open]]></wp:comment_status>
    <wp:ping_status><![CDATA[open]]></wp:ping_status>
    <wp:post_name><![CDATA[{SLUG}]]></wp:post_name>
    <wp:status><![CDATA[draft]]></wp:status>
    <wp:post_parent>0</wp:post_parent>
    <wp:menu_order>0</wp:menu_order>
    <wp:post_type><![CDATA[post]]></wp:post_type>
    <wp:post_password><![CDATA[]]></wp:post_password>
    <wp:is_sticky>0</wp:is_sticky>
{cat_xml}
{tag_xml}
  </item>

</channel>
</rss>
"""


def main() -> None:
    html = ARTICLE_HTML.read_text(encoding="utf-8")
    gutenberg = html_to_gutenberg(html)
    OUTPUT_WXR.write_text(build_wxr(gutenberg), encoding="utf-8")
    block_count = gutenberg.count("<!-- wp:")
    print(f"Wrote {OUTPUT_WXR}")
    print(f"Gutenberg blocks: {block_count}")
    print(f"Content length: {len(gutenberg)} chars")


if __name__ == "__main__":
    main()
