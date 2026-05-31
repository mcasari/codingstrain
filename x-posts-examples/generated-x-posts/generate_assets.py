#!/usr/bin/env python3
"""
Generate tweet assets matching @mario_casari / twitter archive style:
- CodePen Prefill embeds for Java/Spring code snippets
- diagrams.net architecture diagrams for tweets #52–#53
- Tweet text formatted like archive posts (🚀Spring Boot:, ✅ bullets, hashtags)
"""

from __future__ import annotations

import html
import json
import re
import subprocess
import uuid
from pathlib import Path

from codepen_builder import CODEPEN_SCRIPT, build_codepen_embed, build_standalone_pen_page
from diagram_templates import build_tweet_diagram
from drawio_builder import (
    CIRCUIT_BREAKER_CODE_SNIPPET,
    EUREKA_CODE_SNIPPET,
)

ROOT = Path(__file__).resolve().parent
TWEETS_FILE = ROOT / "tweets.json"
DRAWIO_DIR = ROOT / "drawio"
CODEPEN_DIR = ROOT / "codepen"
PNG_DIR = ROOT / "png"
SOURCES_DIR = ROOT / "sources"
INDEX_HTML = ROOT / "index.html"

# Code now lives in each tweet's "code" field (tweets.json), so image_source is
# id-independent and survives renumbering. Kept empty intentionally.
EXTENDED_CODE_SNIPPETS: dict[int, str] = {}

# Carbon.now.sh–style theme (Night Owl editor + gradient canvas)
THEME = {
    "gradient_start": "#667eea",
    "gradient_end": "#764ba2",
    "canvas": "#1a1d2e",
    "window": "#252836",
    "editor": "#011627",
    "dot_red": "#ff5f56",
    "dot_yellow": "#ffbd2e",
    "dot_green": "#27c93f",
    "text": "#d6deeb",
    "keyword": "#c792ea",
    "annotation": "#ffb86c",
    "type": "#ffcb6b",
    "method": "#82aaff",
    "string": "#c3e88d",
    "comment": "#5c6370",
    "number": "#f78c6c",
    "punctuation": "#89ddff",
}

FONT = "ui-monospace, 'Fira Code', 'JetBrains Mono', Consolas, monospace"
FONT_SIZE = 13
LINE_HEIGHT = 20
CHAR_W = 8.35
OUTER_PAD = 48
TITLE_H = 32
INNER_PAD = 18
MAX_LINES = 32

JAVA_KEYWORDS = frozenset({
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while", "var", "record", "true",
    "false", "null",
})


def xml_escape(text: str) -> str:
    return html.escape(text, quote=True)


def normalize_tweet_body(tweet: dict) -> str:
    """Tweet #61 format: hook, blank line, three ✅ bullets, hashtags."""
    raw = tweet.get("body", "").strip()
    tags = tweet.get("tags", "").strip()
    module = tweet.get("module", "").lower()
    lines = [ln.strip() for ln in raw.split("\n") if ln.strip()]

    hook = ""
    bullets: list[str] = []
    for line in lines:
        if line.startswith("✅") or line.startswith("🟢"):
            bullet = line if line.startswith("✅") else "✅ " + line[2:].strip()
            if bullet not in bullets:
                bullets.append(bullet)
        elif line.startswith(("🚀", "💡", "🧵")) and not hook:
            hook = line

    if not hook and lines:
        first = lines[0]
        java_only = (
            ("java" in module or module.startswith("java-"))
            and "spring" not in module
            and "cloud" not in module
        )
        cloudish = "cloud" in module or "feign" in module or "eureka" in raw.lower()
        if java_only:
            hook = first if first.startswith("💡") else f"💡 {first}"
        elif cloudish:
            if first.startswith("🚀"):
                hook = first
            elif first.lower().startswith("spring cloud"):
                hook = f"🚀Spring Cloud: {first.split(':', 1)[-1].strip()}"
            else:
                hook = f"🚀Spring Cloud: {first}"
        elif first.lower().startswith("spring boot"):
            hook = first if first.startswith("🚀") else f"🚀Spring Boot: {first}"
        else:
            hook = first if first.startswith("🚀") else f"🚀Spring Boot: {first}"

    if hook.startswith("🧵"):
        hook = "💡 " + hook[1:].strip()

    skip_phrases = ("example (", "in microservices", "when author-service")
    for line in lines:
        if line == hook or line.startswith(("🚀", "💡", "🧵", "✅", "🟢")):
            continue
        low = line.lower()
        if any(p in low for p in skip_phrases) or len(line) > 120:
            continue
        candidate = f"✅ {line.rstrip('.')}"
        if candidate not in bullets and len(bullets) < 3:
            bullets.append(candidate)

    code = tweet.get("code", "") or ""
    if len(bullets) < 3 and code:
        for ann in re.findall(
            r"@(RestController|RestControllerAdvice|GetMapping|PostMapping|FeignClient|"
            r"CircuitBreaker|Validated|ConfigurationProperties|KafkaListener|Cacheable|"
            r"Async|Transactional|Sql|SpringBootTest|EnableEurekaServer|ExceptionHandler)\b",
            code,
        ):
            if len(bullets) >= 3:
                break
            bullet = f"✅ Uses `@{ann}` in the snippet"
            if not any(ann in b for b in bullets):
                bullets.append(bullet)

    mod_short = tweet.get("module", "codingstrain").split("/")[-1]
    for fallback in (
        f"✅ Runnable sample: `{mod_short}`",
        "✅ Architecture diagram + Carbon CodePen below",
        "✅ From the codingstrain examples repo",
    ):
        if len(bullets) >= 3:
            break
        if fallback not in bullets:
            bullets.append(fallback)

    parts = [hook, ""] + bullets[:3]
    if tags and tags not in raw:
        parts.append(tags)
    return "\n".join(parts)


def apply_tweet61_defaults(tweet: dict) -> None:
    """Align tweet metadata and body with tweet #61 style."""
    tweet["codepen_style"] = "carbon"
    tweet["codepen_large"] = True
    tweet["body"] = normalize_tweet_body(tweet)


def format_twitter_body(tweet: dict) -> str:
    """Format tweet text (tweet #61 layout)."""
    return normalize_tweet_body(tweet)


def image_source(tweet: dict) -> str:
    """Java source shown in CodePen pens and sources/."""
    tid = tweet.get("id")
    if tid in EXTENDED_CODE_SNIPPETS:
        return EXTENDED_CODE_SNIPPETS[tid].rstrip()
    if tweet.get("code"):
        return tweet["code"].rstrip()

    body = tweet["body"]
    module = tweet.get("module", "")

    if "error" in module and "handling" in module:
        return EXPANDED_SNIPPETS["exception_handler"]
    if tweet["id"] == 6:
        return EXPANDED_SNIPPETS["error_response"]

    return "\n".join(f"// {line}" if line.strip() else "//" for line in body.split("\n"))


EXPANDED_SNIPPETS = {
    "exception_handler": """@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }
}

// Controllers stay clean — no try/catch everywhere
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}""",
    "error_response": """public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}""",
}


def tokenize_java_line(line: str) -> list[tuple[str, str]]:
    segments: list[tuple[str, str]] = []
    i, n = 0, len(line)

    while i < n:
        if line[i] in " \t":
            j = i + 1
            while j < n and line[j] in " \t":
                j += 1
            segments.append(("text", line[i:j]))
            i = j
            continue

        if line.startswith("//", i):
            segments.append(("comment", line[i:]))
            break

        if line[i] in '"\'':
            q, j = line[i], i + 1
            while j < n:
                if line[j] == "\\" and j + 1 < n:
                    j += 2
                    continue
                if line[j] == q:
                    j += 1
                    break
                j += 1
            segments.append(("string", line[i:j]))
            i = j
            continue

        if line[i] == "@" and i + 1 < n and line[i + 1].isalpha():
            j = i + 1
            while j < n and (line[j].isalnum() or line[j] in "_."):
                j += 1
            segments.append(("annotation", line[i:j]))
            i = j
            continue

        if line[i].isdigit():
            j = i
            while j < n and (line[j].isdigit() or line[j] in "._"):
                j += 1
            segments.append(("number", line[i:j]))
            i = j
            continue

        if line[i].isalpha() or line[i] == "_":
            j = i
            while j < n and (line[j].isalnum() or line[j] == "_"):
                j += 1
            word = line[i:j]
            nxt = j
            while nxt < n and line[nxt] in " \t":
                nxt += 1
            if word in JAVA_KEYWORDS:
                kind = "keyword"
            elif nxt < n and line[nxt] == "(":
                kind = "method"
            elif word[0].isupper():
                kind = "type"
            else:
                kind = "text"
            segments.append((kind, word))
            i = j
            continue

        segments.append(("punctuation", line[i]))
        i += 1

    return segments


def build_eureka_diagram() -> str:
    """SVG diagram: Eureka registry + service-to-service call by name."""
    t = THEME
    lbl = 'font-family="system-ui, sans-serif"'
    return f'''
  <g id="diagram">
    <rect x="60" y="24" width="780" height="268" rx="10" fill="#252836" stroke="#3d4260" stroke-width="1"/>
    <text x="450" y="48" text-anchor="middle" fill="#8b949e" font-size="12" {lbl}>Service discovery flow</text>
    <!-- Eureka -->
    <rect x="330" y="68" width="240" height="56" rx="8" fill="#1e3a5f" stroke="#3b82f6" stroke-width="2"/>
    <text x="450" y="94" text-anchor="middle" fill="#93c5fd" font-size="14" font-weight="bold" {lbl}>Eureka Server</text>
    <text x="450" y="112" text-anchor="middle" fill="#64748b" font-size="11" {lbl}>:8761 — service registry</text>
    <!-- register arrows -->
    <path d="M 200 130 L 200 148 L 360 148 L 360 124" fill="none" stroke="#64748b" stroke-width="1.5" marker-end="url(#arr)"/>
    <path d="M 450 124 L 450 68" fill="none" stroke="#64748b" stroke-width="1.5" marker-end="url(#arr)"/>
    <path d="M 700 130 L 700 148 L 540 148 L 540 124" fill="none" stroke="#64748b" stroke-width="1.5" marker-end="url(#arr)"/>
    <text x="280" y="162" fill="#64748b" font-size="10" {lbl}>register + heartbeat</text>
    <text x="520" y="162" fill="#64748b" font-size="10" {lbl}>register + heartbeat</text>
    <!-- services -->
    <rect x="80" y="178" width="200" height="72" rx="8" fill="#1e2130" stroke="#22c55e" stroke-width="2"/>
    <text x="180" y="204" text-anchor="middle" fill="#86efac" font-size="13" font-weight="bold" {lbl}>books-service</text>
    <text x="180" y="222" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>:8081 (instance A)</text>
    <text x="180" y="238" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>spring.application.name</text>
    <rect x="350" y="178" width="200" height="72" rx="8" fill="#1e2130" stroke="#a78bfa" stroke-width="2"/>
    <text x="450" y="204" text-anchor="middle" fill="#c4b5fd" font-size="13" font-weight="bold" {lbl}>author-service</text>
    <text x="450" y="222" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>:8082 (instance B)</text>
    <rect x="620" y="178" width="200" height="72" rx="8" fill="#1e2130" stroke="#fbbf24" stroke-width="1.5"/>
    <text x="720" y="204" text-anchor="middle" fill="#fde68a" font-size="13" font-weight="bold" {lbl}>review-service</text>
    <text x="720" y="222" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>:8083 (instance C)</text>
    <!-- call by name -->
    <path d="M 280 214 L 350 214" fill="none" stroke="#38bdf8" stroke-width="2" stroke-dasharray="6 4" marker-end="url(#arrBlue)"/>
    <text x="315" y="206" text-anchor="middle" fill="#38bdf8" font-size="9" {lbl}>GET author-service</text>
    <text x="315" y="258" text-anchor="middle" fill="#38bdf8" font-size="9" {lbl}>LoadBalancer picks instance B</text>
    <!-- without vs with -->
    <text x="120" y="278" fill="#ef4444" font-size="10" {lbl}>✗ http://192.168.1.42:8082</text>
    <text x="620" y="278" fill="#22c55e" font-size="10" {lbl}>✓ http://author-service/authors/1</text>
  </g>'''


def build_circuit_breaker_diagram() -> str:
    """SVG diagram: OpenFeign call + circuit breaker states + fallback."""
    lbl = 'font-family="system-ui, sans-serif"'
    return f'''
  <g id="diagram">
    <rect x="60" y="24" width="780" height="288" rx="10" fill="#252836" stroke="#3d4260" stroke-width="1"/>
    <text x="450" y="48" text-anchor="middle" fill="#8b949e" font-size="12" {lbl}>OpenFeign + Circuit Breaker (Resilience4j)</text>
    <!-- books-service -->
    <rect x="80" y="78" width="160" height="64" rx="8" fill="#1e2130" stroke="#22c55e" stroke-width="2"/>
    <text x="160" y="104" text-anchor="middle" fill="#86efac" font-size="13" font-weight="bold" {lbl}>books-service</text>
    <text x="160" y="122" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>@EnableFeignClients</text>
    <!-- circuit breaker -->
    <rect x="280" y="68" width="340" height="84" rx="8" fill="#1e3a5f" stroke="#f97316" stroke-width="2"/>
    <text x="450" y="92" text-anchor="middle" fill="#fdba74" font-size="13" font-weight="bold" {lbl}>Circuit Breaker</text>
    <text x="450" y="110" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>CircuitBreakerApi / CircuitBreakerService</text>
    <!-- states -->
    <rect x="300" y="122" width="88" height="22" rx="4" fill="#14532d" stroke="#22c55e"/>
    <text x="344" y="137" text-anchor="middle" fill="#86efac" font-size="9" {lbl}>CLOSED</text>
    <rect x="396" y="122" width="88" height="22" rx="4" fill="#450a0a" stroke="#ef4444"/>
    <text x="440" y="137" text-anchor="middle" fill="#fca5a5" font-size="9" {lbl}>OPEN</text>
    <rect x="492" y="122" width="108" height="22" rx="4" fill="#422006" stroke="#fbbf24"/>
    <text x="546" y="137" text-anchor="middle" fill="#fde68a" font-size="9" {lbl}>HALF-OPEN</text>
    <!-- author-service -->
    <rect x="660" y="78" width="160" height="64" rx="8" fill="#1e2130" stroke="#a78bfa" stroke-width="2"/>
    <text x="740" y="104" text-anchor="middle" fill="#c4b5fd" font-size="13" font-weight="bold" {lbl}>author-service</text>
    <text x="740" y="122" text-anchor="middle" fill="#64748b" font-size="10" {lbl}>may fail / timeout</text>
    <!-- arrows -->
    <path d="M 240 110 L 280 110" fill="none" stroke="#38bdf8" stroke-width="2" marker-end="url(#arrBlue)"/>
    <text x="260" y="102" text-anchor="middle" fill="#38bdf8" font-size="9" {lbl}>Feign</text>
    <path d="M 620 110 L 660 110" fill="none" stroke="#64748b" stroke-width="1.5" marker-end="url(#arr)"/>
    <!-- fallback -->
    <path d="M 450 152 L 450 178" fill="none" stroke="#ef4444" stroke-width="1.5" stroke-dasharray="5 3" marker-end="url(#arrRed)"/>
    <rect x="330" y="182" width="240" height="44" rx="6" fill="#1e2130" stroke="#ef4444" stroke-width="1.5"/>
    <text x="450" y="202" text-anchor="middle" fill="#fca5a5" font-size="11" font-weight="bold" {lbl}>fallbackMethod()</text>
    <text x="450" y="218" text-anchor="middle" fill="#64748b" font-size="9" {lbl}>&quot;Fallback content&quot; (circuit OPEN)</text>
    <!-- state flow -->
    <text x="90" y="248" fill="#8b949e" font-size="10" {lbl}>State flow:</text>
    <text x="90" y="266" fill="#86efac" font-size="10" {lbl}>CLOSED</text>
    <text x="150" y="266" fill="#64748b" font-size="10" {lbl}>→ failures &gt; 50%</text>
    <text x="250" y="266" fill="#fca5a5" font-size="10" {lbl}>→ OPEN (fail fast)</text>
    <text x="360" y="266" fill="#fde68a" font-size="10" {lbl}>→ HALF-OPEN (retry)</text>
    <text x="490" y="266" fill="#86efac" font-size="10" {lbl}>→ CLOSED</text>
    <text x="90" y="292" fill="#ef4444" font-size="10" {lbl}>Without CB: threads blocked, timeouts pile up, cascade failure</text>
    <text x="500" y="292" fill="#22c55e" font-size="10" {lbl}>With CB: fail fast + degraded response</text>
  </g>'''


def get_diagram_svg(tweet: dict) -> str:
    dtype = tweet.get("diagram_type", "eureka")
    if dtype == "circuit_breaker":
        return build_circuit_breaker_diagram()
    return build_eureka_diagram()


def render_code_block(source: str, wx: int, wy: int, window_w: int) -> tuple[str, int]:
    """Render carbon code window; return (svg_fragment, total_height)."""
    lines = source.split("\n")
    max_lines = 26
    if len(lines) > max_lines:
        lines = lines[: max_lines - 1] + [lines[max_lines - 1] + " …"]

    num_lines = len(lines)
    window_h = TITLE_H + INNER_PAD * 2 + num_lines * LINE_HEIGHT
    code_y = wy + TITLE_H + INNER_PAD
    code_x = wx + INNER_PAD
    t = THEME
    cls = {
        "keyword": "kw", "annotation": "ann", "string": "str", "type": "typ",
        "method": "met", "comment": "com", "number": "num", "punctuation": "pun",
        "text": "txt",
    }
    parts = [
        f'''  <g filter="url(#shadow)">
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{window_h}" rx="8" fill="{t['window']}"/>
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{TITLE_H}" rx="8" fill="{t['window']}"/>
    <rect x="{wx}" y="{wy + TITLE_H - 6}" width="{window_w}" height="6" fill="{t['window']}"/>
    <circle cx="{wx + 18}" cy="{wy + 16}" r="5.5" fill="{t['dot_red']}"/>
    <circle cx="{wx + 36}" cy="{wy + 16}" r="5.5" fill="{t['dot_yellow']}"/>
    <circle cx="{wx + 54}" cy="{wy + 16}" r="5.5" fill="{t['dot_green']}"/>
    <rect x="{wx}" y="{wy + TITLE_H}" width="{window_w}" height="{window_h - TITLE_H}" fill="{t['editor']}"/>
    <rect x="{wx}" y="{wy + window_h - 8}" width="{window_w}" height="8" rx="8" fill="{t['editor']}"/>
  </g>'''
    ]
    for idx, line in enumerate(lines):
        y = code_y + (idx + 1) * LINE_HEIGHT - 5
        x = code_x
        for kind, text in tokenize_java_line(line):
            css = cls.get(kind, "txt")
            parts.append(
                f'  <text x="{x:.1f}" y="{y}" class="{css}">{xml_escape(text)}</text>'
            )
            x += len(text) * CHAR_W
    total_h = wy + window_h
    return "\n".join(parts), total_h


def build_composite_diagram_svg(tweet: dict) -> str:
    """Image with architecture diagram + code (for tweets with has_diagram)."""
    source = image_source(tweet)
    total_w = 900
    diagram_h = 320 if tweet.get("diagram_type") == "circuit_breaker" else 300
    gap = 24
    wx = OUTER_PAD
    window_w = total_w - OUTER_PAD * 2
    code_wy = OUTER_PAD + diagram_h + gap
    code_xml, bottom = render_code_block(source, wx, code_wy, window_w)
    total_h = bottom + OUTER_PAD
    t = THEME
    style = f"""
      .kw {{ fill:{t['keyword']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .ann {{ fill:{t['annotation']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .str {{ fill:{t['string']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .typ {{ fill:{t['type']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .met {{ fill:{t['method']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .com {{ fill:{t['comment']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .num {{ fill:{t['number']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .pun {{ fill:{t['punctuation']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .txt {{ fill:{t['text']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
    """
    diagram = get_diagram_svg(tweet)
    return f'''<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="{total_w}" height="{total_h}" viewBox="0 0 {total_w} {total_h}">
  <defs>
    <filter id="shadow" x="-15%" y="-15%" width="130%" height="130%">
      <feDropShadow dx="0" dy="10" stdDeviation="14" flood-color="#000" flood-opacity="0.45"/>
    </filter>
    <marker id="arr" markerWidth="8" markerHeight="8" refX="6" refY="3" orient="auto">
      <path d="M0,0 L6,3 L0,6 Z" fill="#64748b"/>
    </marker>
    <marker id="arrBlue" markerWidth="8" markerHeight="8" refX="6" refY="3" orient="auto">
      <path d="M0,0 L6,3 L0,6 Z" fill="#38bdf8"/>
    </marker>
    <marker id="arrRed" markerWidth="8" markerHeight="8" refX="6" refY="3" orient="auto">
      <path d="M0,0 L6,3 L0,6 Z" fill="#ef4444"/>
    </marker>
    <style>{style}</style>
  </defs>
  <rect width="100%" height="100%" fill="{t['canvas']}"/>
{diagram}
{code_xml}
</svg>
'''


def build_svg(tweet: dict) -> str:
    if tweet.get("has_diagram"):
        return build_composite_diagram_svg(tweet)
    return build_carbon_svg(tweet)


def build_carbon_svg(tweet: dict) -> str:
    source = image_source(tweet)
    lines = source.split("\n")
    if len(lines) > MAX_LINES:
        lines = lines[: MAX_LINES - 1] + [lines[MAX_LINES - 1] + " …"]

    num_lines = len(lines)
    max_len = max((len(l) for l in lines), default=40)
    code_inner_w = int(max_len * CHAR_W) + INNER_PAD * 2
    window_w = max(640, min(code_inner_w + INNER_PAD * 2, 1040))
    window_h = TITLE_H + INNER_PAD * 2 + num_lines * LINE_HEIGHT
    total_w = window_w + OUTER_PAD * 2
    total_h = window_h + OUTER_PAD * 2

    wx, wy = OUTER_PAD, OUTER_PAD
    code_y = wy + TITLE_H + INNER_PAD
    code_x = wx + INNER_PAD

    t = THEME
    cls = {
        "keyword": "kw", "annotation": "ann", "string": "str", "type": "typ",
        "method": "met", "comment": "com", "number": "num", "punctuation": "pun",
        "text": "txt",
    }

    style = f"""
      .kw {{ fill:{t['keyword']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .ann {{ fill:{t['annotation']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .str {{ fill:{t['string']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .typ {{ fill:{t['type']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .met {{ fill:{t['method']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .com {{ fill:{t['comment']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .num {{ fill:{t['number']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .pun {{ fill:{t['punctuation']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
      .txt {{ fill:{t['text']}; font-family:{FONT}; font-size:{FONT_SIZE}px; }}
    """

    code_parts: list[str] = []
    for idx, line in enumerate(lines):
        y = code_y + (idx + 1) * LINE_HEIGHT - 5
        x = code_x
        for kind, text in tokenize_java_line(line):
            css = cls.get(kind, "txt")
            code_parts.append(
                f'  <text x="{x:.1f}" y="{y}" class="{css}">{xml_escape(text)}</text>'
            )
            x += len(text) * CHAR_W

    code_xml = "\n".join(code_parts)

    return f'''<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="{total_w}" height="{total_h}" viewBox="0 0 {total_w} {total_h}">
  <defs>
    <linearGradient id="bg" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="{t['gradient_start']}"/>
      <stop offset="100%" stop-color="{t['gradient_end']}"/>
    </linearGradient>
    <filter id="shadow" x="-15%" y="-15%" width="130%" height="130%">
      <feDropShadow dx="0" dy="10" stdDeviation="14" flood-color="#000" flood-opacity="0.45"/>
    </filter>
    <style>{style}</style>
  </defs>
  <rect width="100%" height="100%" fill="url(#bg)"/>
  <g filter="url(#shadow)">
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{window_h}" rx="8" fill="{t['window']}"/>
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{TITLE_H}" rx="8" fill="{t['window']}"/>
    <rect x="{wx}" y="{wy + TITLE_H - 6}" width="{window_w}" height="6" fill="{t['window']}"/>
    <circle cx="{wx + 18}" cy="{wy + 16}" r="5.5" fill="{t['dot_red']}"/>
    <circle cx="{wx + 36}" cy="{wy + 16}" r="5.5" fill="{t['dot_yellow']}"/>
    <circle cx="{wx + 54}" cy="{wy + 16}" r="5.5" fill="{t['dot_green']}"/>
    <rect x="{wx}" y="{wy + TITLE_H}" width="{window_w}" height="{window_h - TITLE_H}" fill="{t['editor']}"/>
    <rect x="{wx}" y="{wy + window_h - 8}" width="{window_w}" height="8" rx="8" fill="{t['editor']}"/>
  </g>
{code_xml}
</svg>
'''


def build_drawio_diagram(tweet: dict) -> str:
    """Native diagrams.net architecture diagram (diagram-only, code in CodePen)."""
    return build_tweet_diagram(tweet)


def build_drawio_simple(tweet: dict) -> str:
    tid = tweet["id"]
    code = xml_escape(image_source(tweet).replace("\n", "&#xa;"))
    footer = xml_escape(tweet.get("module", ""))
    return f'''<mxfile host="app.diagrams.net" agent="codingstrain" version="22.1.0">
  <diagram id="{uuid.uuid4()}" name="Tweet {tid}">
    <mxGraphModel>
      <root>
        <mxCell id="0"/><mxCell id="1" parent="0"/>
        <mxCell id="c" value="{code}" style="rounded=1;fillColor=#1e2130;fontColor=#d6deeb;fontFamily=Courier New;fontSize=11;whiteSpace=wrap;" vertex="1" parent="1">
          <mxGeometry x="20" y="40" width="520" height="360" as="geometry"/>
        </mxCell>
        <mxCell id="f" value="{footer}" style="text;fontSize=10;fontColor=#71767b;" vertex="1" parent="1">
          <mxGeometry x="20" y="10" width="400" height="24" as="geometry"/>
        </mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
'''


def build_combined_drawio(tweets: list[dict]) -> str:
    parts = [
        '<?xml version="1.0" encoding="UTF-8"?>',
        '<mxfile host="app.diagrams.net" agent="codingstrain" version="22.1.8" type="device">',
    ]
    for tweet in tweets:
        content = build_drawio_diagram(tweet)
        m = re.search(r"<diagram[^>]*>.*?</diagram>", content, re.DOTALL)
        if m:
            parts.append(m.group(0))
    parts.append("</mxfile>")
    return "\n  ".join(parts)


INDEX_PAGE_SIZE = 20

INDEX_STYLES = """
    body { font-family: system-ui, sans-serif; max-width: 960px; margin: 2rem auto; padding: 0 1rem; background: #f7f9f9; }
    article { background: #fff; border-radius: 16px; padding: 1.25rem; margin-bottom: 2rem; box-shadow: 0 1px 3px rgba(0,0,0,.08); }
    h2 { margin: 0 0 1rem; font-size: 0.9rem; color: #536471; }
    .tweet-text { white-space: pre-wrap; font-size: 15px; line-height: 1.4; margin: 1rem 0; padding: 1rem; background: #f7f9f9; border-radius: 12px; border: 1px solid #eff3f4; }
    .links a { color: #1d9bf0; }
    .banner { background: #1d9bf0; color: #fff; padding: 1.25rem; border-radius: 12px; margin-bottom: 1rem; }
    .banner p { margin: 0.5rem 0 0; opacity: 0.95; }
    .banner a { color: #fff; }
    .drawio-box { background: #e8f4fc; border: 1px solid #1d9bf0; border-radius: 12px; padding: 1rem; margin-bottom: 1rem; }
    .drawio-box ol { margin: 0.5rem 0 0; padding-left: 1.25rem; }
    .drawio-box li { margin: 0.35rem 0; }
    .asset-label { font-size: 0.85rem; color: #536471; margin: 0.75rem 0 0.35rem; }
    .asset-img { display: block; max-width: 100%; border-radius: 8px; margin-bottom: 0.5rem; border: 1px solid #eff3f4; }
    .codepen-wrap { margin: 0.5rem 0 1rem; border-radius: 8px; overflow: hidden; border: 1px solid #eff3f4; }
    .codepen-wrap--large { max-width: 100%; min-height: 640px; }
    .codepen-links { font-size: 0.8rem; margin: 0.35rem 0 0; padding: 0 0.5rem 0.5rem; }
    .codepen-links a { color: #1d9bf0; }
    .pagination { display: flex; flex-wrap: wrap; align-items: center; gap: 0.5rem 0.75rem; background: #fff; border: 1px solid #eff3f4; border-radius: 12px; padding: 0.75rem 1rem; margin-bottom: 1.5rem; font-size: 0.9rem; }
    .pagination a { color: #1d9bf0; text-decoration: none; }
    .pagination a:hover { text-decoration: underline; }
    .pagination .page-current { font-weight: 700; color: #0f1419; }
    .pagination .page-meta { color: #536471; margin-left: auto; }
    .pagination .page-nav { font-weight: 600; }
    .pagination .page-nav.disabled { color: #aab8c2; pointer-events: none; }
"""


def _index_page_href(page: int) -> str:
    return "index.html" if page == 1 else f"page-{page}.html"


def build_article(t: dict) -> str:
    tid = t["id"]
    stem = f"tweet-{tid:02d}"
    text = xml_escape(format_twitter_body(t))
    pen = build_codepen_embed(t, image_source(t))
    has_png = (PNG_DIR / f"{stem}-diagram.png").is_file()
    has_drawio = (DRAWIO_DIR / f"{stem}.drawio").is_file()
    diagram_block = (
        f"""      <p class="asset-label">Architecture — <a href="https://app.diagrams.net/" target="_blank" rel="noopener">diagrams.net</a></p>
      <img src="png/{stem}-diagram.png" alt="Diagram {tid}" width="720" class="asset-img"/>
"""
        if has_png
        else ""
    )
    drawio_link = (
        f'        <a href="drawio/{stem}.drawio" download>.drawio diagram</a> ·\n'
        if has_drawio
        else ""
    )
    return f"""    <article>
      <h2>Tweet #{tid}</h2>
{diagram_block}      <p class="asset-label">Code — <a href="https://codepen.io/" target="_blank" rel="noopener">CodePen</a> pen</p>
{pen}
      <div class="tweet-text">{text}</div>
      <p class="links">
{drawio_link}        <a href="codepen/{stem}.html" target="_blank" rel="noopener">CodePen preview</a> ·
        <a href="sources/{stem}.java" download>Source .java</a>
      </p>
    </article>"""


def build_pagination_nav(
    page: int, total_pages: int, first_tid: int, last_tid: int, total_tweets: int
) -> str:
    prev_cls = "page-nav disabled" if page <= 1 else "page-nav"
    next_cls = "page-nav disabled" if page >= total_pages else "page-nav"
    prev_href = _index_page_href(page - 1) if page > 1 else "#"
    next_href = _index_page_href(page + 1) if page < total_pages else "#"
    page_links = []
    for p in range(1, total_pages + 1):
        if p == page:
            page_links.append(f'<span class="page-current">{p}</span>')
        else:
            page_links.append(f'<a href="{_index_page_href(p)}">{p}</a>')
    return f"""  <nav class="pagination" aria-label="Tweet pages">
    <a class="{prev_cls}" href="{prev_href}">← Prev</a>
    {' · '.join(page_links)}
    <a class="{next_cls}" href="{next_href}">Next →</a>
    <span class="page-meta">Tweets #{first_tid}–#{last_tid} of {total_tweets} · page {page}/{total_pages}</span>
  </nav>"""


def build_index_page(tweets: list[dict], page: int, page_size: int = INDEX_PAGE_SIZE) -> str:
    total = len(tweets)
    total_pages = max(1, (total + page_size - 1) // page_size)
    page = max(1, min(page, total_pages))
    chunk = tweets[(page - 1) * page_size : page * page_size]
    first_tid = chunk[0]["id"] if chunk else 0
    last_tid = chunk[-1]["id"] if chunk else 0
    nav = build_pagination_nav(page, total_pages, first_tid, last_tid, total)
    articles = "\n".join(build_article(t) for t in chunk)
    title_suffix = f" — page {page}/{total_pages}" if total_pages > 1 else ""
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>codingstrain X Posts{title_suffix}</title>
  <style>{INDEX_STYLES}
  </style>
</head>
<body>
  <div class="banner">
    <h1>Spring Boot &amp; Java X Posts</h1>
    <p>Every tweet: <a href="https://app.diagrams.net/">diagrams.net</a> architecture diagram + <a href="https://codepen.io/">CodePen</a> code pen.</p>
  </div>
{nav}
{articles}
{nav}
  <script async src="{CODEPEN_SCRIPT}"></script>
</body>
</html>
"""


def build_index(tweets: list[dict]) -> str:
    """Single-page preview (page 1 only). Prefer write_index_pages()."""
    return build_index_page(tweets, 1)


def write_index_pages(tweets: list[dict], page_size: int = INDEX_PAGE_SIZE) -> list[Path]:
    """Write paginated index.html + page-2.html … and remove stale page files."""
    total_pages = max(1, (len(tweets) + page_size - 1) // page_size)
    written: list[Path] = []
    for page in range(1, total_pages + 1):
        path = INDEX_HTML if page == 1 else ROOT / f"page-{page}.html"
        path.write_text(build_index_page(tweets, page, page_size), encoding="utf-8")
        written.append(path)
    for stale in ROOT.glob("page-*.html"):
        try:
            n = int(stale.stem.split("-", 1)[1])
        except (IndexError, ValueError):
            continue
        if n > total_pages:
            stale.unlink()
    return written


def export_diagram_png(drawio_path: Path, png_path: Path) -> bool:
    png_path.parent.mkdir(parents=True, exist_ok=True)
    try:
        subprocess.run(
            ["npx", "--yes", "draw.io-export", str(drawio_path), "-o", str(png_path)],
            check=True,
            capture_output=True,
            timeout=120,
            cwd=ROOT,
        )
        return png_path.is_file()
    except (subprocess.CalledProcessError, subprocess.TimeoutExpired, FileNotFoundError):
        return False


def main() -> None:
    import argparse

    parser = argparse.ArgumentParser(description="Generate x-post assets")
    parser.add_argument(
        "--skip-png",
        action="store_true",
        help="Skip draw.io PNG export (faster; reuses existing PNGs)",
    )
    args = parser.parse_args()

    tweets = json.loads(TWEETS_FILE.read_text(encoding="utf-8"))
    for tweet in tweets:
        apply_tweet61_defaults(tweet)
    TWEETS_FILE.write_text(
        json.dumps(tweets, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )

    DRAWIO_DIR.mkdir(parents=True, exist_ok=True)
    CODEPEN_DIR.mkdir(parents=True, exist_ok=True)
    PNG_DIR.mkdir(parents=True, exist_ok=True)
    SOURCES_DIR.mkdir(parents=True, exist_ok=True)

    for tweet in tweets:
        tid = tweet["id"]
        stem = f"tweet-{tid:02d}"
        code = image_source(tweet) + "\n"
        (SOURCES_DIR / f"{stem}.java").write_text(code, encoding="utf-8")
        (CODEPEN_DIR / f"{stem}.html").write_text(
            build_standalone_pen_page(tweet, code.rstrip()), encoding="utf-8"
        )
        drawio_path = DRAWIO_DIR / f"{stem}.drawio"
        drawio_path.write_text(build_drawio_diagram(tweet), encoding="utf-8")
        if not args.skip_png:
            export_diagram_png(drawio_path, PNG_DIR / f"{stem}-diagram.png")
        stale_code_png = PNG_DIR / f"{stem}-code.png"
        if stale_code_png.exists():
            stale_code_png.unlink()

    (DRAWIO_DIR / "all-tweets.drawio").write_text(build_combined_drawio(tweets), encoding="utf-8")
    pages = write_index_pages(tweets)

    readme = f"""# Generated X Post Assets

{len(tweets)} posts styled like **@mario_casari** tweets from `twitter-2026-05-15`.

## Style

| Element | Format |
|---------|--------|
| Tweet text | `💡` / `🚀` hook + 3 `✅` bullets + hashtags (tweet #61 layout) |
| Code | [CodePen](https://codepen.io/) Carbon-style pens (syntax highlighting, large embed) |
| Diagrams | [diagrams.net](https://app.diagrams.net/) `.drawio` (architecture only, all tweets) |
| Java tips | `💡 Java tip: …` |

## Files

| Folder | Contents |
|--------|----------|
| `codepen/` | Standalone HTML page per tweet with a CodePen Prefill embed |
| `png/` | Diagram PNG exports (one per tweet) |
| `drawio/` | diagrams.net architecture diagrams |
| `sources/` | `.java` snippets — copy into a CodePen pen to edit |
| `index.html` | Preview page 1 ({INDEX_PAGE_SIZE} tweets per page) |
| `page-2.html` … | Additional preview pages when there are more than {INDEX_PAGE_SIZE} tweets |

## Regenerate

```bash
python3 generate_assets.py --skip-png   # fast: codepen + index + tweets.json
python3 generate_assets.py              # also exports diagram PNGs (slow)
```

Diagram PNGs: `npx draw.io-export`. Code pens load via [CodePen Prefill embeds](https://blog.codepen.io/documentation/prefill-embeds/).
"""
    (ROOT / "README.md").write_text(readme, encoding="utf-8")
    print(f"Generated assets for {len(tweets)} tweets")
    print(f"  codepen/ → {CODEPEN_DIR}")
    print(f"  diagrams.net → {DRAWIO_DIR}")
    print(f"  png/ → {PNG_DIR} (diagram exports)")
    print(f"  preview → {len(pages)} pages ({INDEX_PAGE_SIZE} tweets/page), start at {INDEX_HTML}")


if __name__ == "__main__":
    main()
