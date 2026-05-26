#!/usr/bin/env python3
"""Build CodePen Prefill embed markup for Java/Spring code snippets."""

from __future__ import annotations

import html
import json
from typing import Any

CODEPEN_CSS = """body {
  margin: 0;
  padding: 1rem 1.25rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
  box-sizing: border-box;
}
.code-snippet {
  margin: 0;
  padding: 1.25rem 1.5rem;
  background: #011627;
  border-radius: 8px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.35);
  overflow: auto;
}
.code-snippet code {
  font-family: "Fira Code", "JetBrains Mono", Consolas, monospace;
  font-size: 13px;
  line-height: 1.55;
  color: #d6deeb;
  white-space: pre;
}
.module-tag {
  font-family: system-ui, sans-serif;
  font-size: 11px;
  color: #94a3b8;
  margin: 0 0 0.75rem;
  letter-spacing: 0.02em;
}
"""

CODEPEN_SCRIPT = "https://public.codepenassets.com/embed/index.js"


def _pre_block(text: str) -> str:
    """Escape only what would break a <pre> wrapper (keep CSS/JS quotes literal)."""
    return text.replace("&", "&amp;").replace("<", "&lt;")


def _prefill_attr(meta: dict[str, Any]) -> str:
    raw = json.dumps(meta, ensure_ascii=False)
    return raw.replace("&", "&amp;").replace("'", "&#39;")


def _embed_height(code: str) -> int:
    lines = code.count("\n") + 1
    return min(720, max(300, 100 + lines * 20))


def build_codepen_embed(tweet: dict, code: str) -> str:
    """HTML fragment: CodePen Prefill embed for a Java snippet."""
    tid = tweet["id"]
    module = tweet.get("module", "codingstrain")
    title = f"Tweet {tid} — {module}"
    height = _embed_height(code)

    meta = {
        "title": title,
        "description": f"Spring Boot / Java snippet from codingstrain ({module})",
        "tags": ["java", "springboot", "codingstrain"],
    }

    escaped = html.escape(code)
    html_block = (
        f'<p class="module-tag">{html.escape(module)}</p>\n'
        f'<pre class="code-snippet"><code>{escaped}</code></pre>'
    )

    return f"""      <div class="codepen-wrap">
        <div class="codepen"
             data-prefill='{_prefill_attr(meta)}'
             data-height="{height}"
             data-theme-id="dark"
             data-default-tab="html,result"
             data-editable="true">
          <pre data-lang="css">{_pre_block(CODEPEN_CSS)}</pre>
          <pre data-lang="html">{html.escape(html_block)}</pre>
        </div>
        <p class="codepen-links">
          <a href="codepen/tweet-{tid:02d}.html" target="_blank" rel="noopener">Open pen preview</a>
          · <a href="https://codepen.io/pen/" target="_blank" rel="noopener">New pen on CodePen</a>
        </p>
      </div>"""


def build_standalone_pen_page(tweet: dict, code: str) -> str:
    """Full HTML page with one Prefill embed (works offline except CodePen script)."""
    tid = tweet["id"]
    embed = build_codepen_embed(tweet, code)
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Tweet {tid} — CodePen</title>
  <style>
    body {{ font-family: system-ui, sans-serif; margin: 2rem; background: #f7f9f9; }}
    h1 {{ font-size: 1.1rem; color: #536471; }}
    .codepen-wrap {{ max-width: 760px; }}
  </style>
</head>
<body>
  <h1>Tweet #{tid} — <a href="https://codepen.io/">CodePen</a> prefill</h1>
{embed}
  <script async src="{CODEPEN_SCRIPT}"></script>
</body>
</html>
"""
