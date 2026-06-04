#!/usr/bin/env python3
"""Build WordPress WXR 1.2 from article HTML — matches codingstrain export format."""

from __future__ import annotations

import re
from datetime import datetime, timezone
from pathlib import Path
from xml.sax.saxutils import escape

ARTICLE_HTML = Path(__file__).resolve().parent / (
    "java-17-sealed-classes-closed-hierarchies-with-permits.html"
)
OUTPUT_WXR = Path(__file__).resolve().parent / (
    "WordPress.java-17-sealed-classes.xml"
)

SITE_URL = "https://codingstrain.com"
AUTHOR_LOGIN = "mario.casari@gmail.com"
AUTHOR_EMAIL = "mario.casari@gmail.com"
AUTHOR_DISPLAY = "mario.casari@gmail.com"

TITLE = "Java 17 Sealed Classes: Close Your Inheritance Graph with permits"
SLUG = "java-17-sealed-classes-permits"
EXCERPT = (
    "With sealed classes you list exactly who may extend a type. "
    "The compiler checks the list — and every permitted subclass must be "
    "final, sealed, or non-sealed."
)
POST_ID = 61001

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
            blocks.append(syntaxhighlighter_block(code, lang))
            continue

        m_pre_plain = re.match(r"^<pre([^>]*)>(.*)</pre>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_pre_plain:
            code = m_pre_plain.group(2)
            code = code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
            blocks.append(syntaxhighlighter_block(code, ""))
            continue

        m_ul = re.match(r"^<ul([^>]*)>(.*)</ul>\s*$", chunk, re.DOTALL | re.IGNORECASE)
        if m_ul:
            inner = m_ul.group(2).strip()
            items = re.findall(r"<li[^>]*>(.*?)</li>", inner, re.DOTALL | re.IGNORECASE)
            if items:
                li_blocks = []
                for item in items:
                    li_blocks.append(
                        "<!-- wp:list-item -->\n"
                        f"<li>{item.strip()}</li>\n"
                        "<!-- /wp:list-item -->"
                    )
                list_body = "\n\n".join(li_blocks)
                blocks.append(
                    "<!-- wp:list -->\n"
                    f'<ul class="wp-block-list">{list_body}</ul>\n'
                    "<!-- /wp:list -->"
                )
            else:
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

        blocks.append(
            "<!-- wp:freeform -->\n"
            f"{chunk}\n"
            "<!-- /wp:freeform -->"
        )

    return "\n\n".join(blocks)


def syntaxhighlighter_block(code: str, lang: str) -> str:
    if lang:
        return (
            f'<!-- wp:syntaxhighlighter/code {{"language":"{lang}"}} -->\n'
            f'<pre class="wp-block-syntaxhighlighter-code">{code}</pre>\n'
            "<!-- /wp:syntaxhighlighter/code -->"
        )
    return (
        "<!-- wp:syntaxhighlighter/code -->\n"
        f'<pre class="wp-block-syntaxhighlighter-code">{code}</pre>\n'
        "<!-- /wp:syntaxhighlighter/code -->"
    )


def build_wxr(gutenberg_content: str) -> str:
    now = datetime.now(timezone.utc)
    post_date = now.strftime("%Y-%m-%d %H:%M:%S")
    post_date_gmt = post_date
    pub_rfc = now.strftime("%a, %d %b %Y %H:%M:%S +0000")
    link = f"{SITE_URL}/{SLUG}/"
    guid = f"{SITE_URL}/?p={POST_ID}"

    cat_xml = "\n".join(
        f'\t\t<category domain="category" nicename="{slugify(c)}">'
        f"<![CDATA[{cdata(c)}]]></category>"
        for c in CATEGORIES
    )
    tag_xml = "\n".join(
        f'\t\t<category domain="post_tag" nicename="{slugify(t)}">'
        f"<![CDATA[{cdata(t)}]]></category>"
        for t in TAGS
    )

    return f"""<?xml version="1.0" encoding="UTF-8" ?>
<!-- This is a WordPress eXtended RSS file generated for codingstrain import. -->
<!-- To import: Tools → Import → WordPress → upload this file. -->

<rss version="2.0"
\txmlns:excerpt="http://wordpress.org/export/1.2/excerpt/"
\txmlns:content="http://purl.org/rss/1.0/modules/content/"
\txmlns:wfw="http://wellformedweb.org/CommentAPI/"
\txmlns:dc="http://purl.org/dc/elements/1.1/"
\txmlns:wp="http://wordpress.org/export/1.2/"
>

<channel>
\t<title></title>
\t<link>{escape(SITE_URL)}</link>
\t<description>All about code</description>
\t<pubDate>{pub_rfc}</pubDate>
\t<language>en-US</language>
\t<wp:wxr_version>1.2</wp:wxr_version>
\t<wp:base_site_url>{escape(SITE_URL)}</wp:base_site_url>
\t<wp:base_blog_url>{escape(SITE_URL)}</wp:base_blog_url>

\t<wp:author><wp:author_id>1</wp:author_id><wp:author_login><![CDATA[{cdata(AUTHOR_LOGIN)}]]></wp:author_login><wp:author_email><![CDATA[{cdata(AUTHOR_EMAIL)}]]></wp:author_email><wp:author_display_name><![CDATA[{cdata(AUTHOR_DISPLAY)}]]></wp:author_display_name><wp:author_first_name><![CDATA[]]></wp:author_first_name><wp:author_last_name><![CDATA[]]></wp:author_last_name></wp:author>

\t<wp:category>
\t\t<wp:term_id>10</wp:term_id>
\t\t<wp:category_nicename><![CDATA[java]]></wp:category_nicename>
\t\t<wp:category_parent><![CDATA[]]></wp:category_parent>
\t\t<wp:cat_name><![CDATA[Java]]></wp:cat_name>
\t</wp:category>

\t<item>
\t\t<title><![CDATA[{cdata(TITLE)}]]></title>
\t\t<link>{escape(link)}</link>
\t\t<pubDate>{pub_rfc}</pubDate>
\t\t<dc:creator><![CDATA[{cdata(AUTHOR_LOGIN)}]]></dc:creator>
\t\t<guid isPermaLink="false">{escape(guid)}</guid>
\t\t<description></description>
\t\t<content:encoded><![CDATA[{cdata(gutenberg_content)}]]></content:encoded>
\t\t<excerpt:encoded><![CDATA[{cdata(EXCERPT)}]]></excerpt:encoded>
\t\t<wp:post_id>{POST_ID}</wp:post_id>
\t\t<wp:post_date><![CDATA[{post_date}]]></wp:post_date>
\t\t<wp:post_date_gmt><![CDATA[{post_date_gmt}]]></wp:post_date_gmt>
\t\t<wp:post_modified><![CDATA[{post_date}]]></wp:post_modified>
\t\t<wp:post_modified_gmt><![CDATA[{post_date_gmt}]]></wp:post_modified_gmt>
\t\t<wp:comment_status><![CDATA[open]]></wp:comment_status>
\t\t<wp:ping_status><![CDATA[open]]></wp:ping_status>
\t\t<wp:post_name><![CDATA[{SLUG}]]></wp:post_name>
\t\t<wp:status><![CDATA[draft]]></wp:status>
\t\t<wp:post_parent>0</wp:post_parent>
\t\t<wp:menu_order>0</wp:menu_order>
\t\t<wp:post_type><![CDATA[post]]></wp:post_type>
\t\t<wp:post_password><![CDATA[]]></wp:post_password>
\t\t<wp:is_sticky>0</wp:is_sticky>
{cat_xml}
{tag_xml}
\t</item>

</channel>
</rss>
"""


def main() -> None:
    html = ARTICLE_HTML.read_text(encoding="utf-8")
    gutenberg = html_to_gutenberg(html)
    OUTPUT_WXR.write_text(build_wxr(gutenberg), encoding="utf-8")
    print(f"Wrote {OUTPUT_WXR}")
    print(f"Gutenberg blocks: {gutenberg.count('<!-- wp:')}")
    print(f"Content length: {len(gutenberg)} chars")
    print(f"Import: WordPress admin → Tools → Import → WordPress → {OUTPUT_WXR.name}")


if __name__ == "__main__":
    main()
