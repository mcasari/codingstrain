"""Print shell and small SVG figures for the PCA architecture papers."""

from __future__ import annotations

CSS = r"""
@page {
  size: A4;
  margin: 15mm 13mm 16mm 13mm;
  @bottom-left {
    content: "__FOOTER__";
    font-family: "Segoe UI", Arial, sans-serif;
    font-size: 8.5pt;
    color: #6b6560;
  }
  @bottom-right {
    content: counter(page);
    font-family: "Segoe UI", Arial, sans-serif;
    font-size: 8.5pt;
    color: #6b6560;
  }
}
@page :first {
  margin: 0;
  @bottom-left { content: none; }
  @bottom-right { content: none; }
}
* { box-sizing: border-box; -webkit-print-color-adjust: exact; print-color-adjust: exact; }
html, body { margin: 0; padding: 0; }
body {
  font-family: "Segoe UI", Arial, sans-serif;
  color: #1c1915;
  font-size: 10.5pt;
  line-height: 1.45;
}
.cover {
  height: 296mm;
  overflow: hidden;
  break-after: page;
  page-break-after: always;
  background: __COVER__;
  color: #f7f4ee;
  padding: 22mm 18mm 16mm 18mm;
  position: relative;
}
.cover:before {
  content: "";
  position: absolute;
  left: 0; top: 0; bottom: 0;
  width: 8mm;
  background: __ACCENT2__;
}
.kicker {
  letter-spacing: 0.16em;
  text-transform: uppercase;
  font-size: 9pt;
  color: #ead9a6;
  margin: 0 0 10mm 0;
}
.cover h1 {
  font-size: 36pt;
  line-height: 1.02;
  font-weight: 650;
  margin: 0 0 4mm 0;
  letter-spacing: -0.02em;
}
.cover .subtitle {
  font-size: 14pt;
  line-height: 1.35;
  color: #f0e6d2;
  max-width: 160mm;
  margin: 0 0 8mm 0;
}
.lede {
  font-size: 12pt;
  line-height: 1.45;
  max-width: 165mm;
  color: #f7f4ee;
  margin: 0 0 10mm 0;
}
.chips { margin: 0 0 12mm 0; }
.chip {
  display: inline-block;
  border: 1px solid rgba(247,244,238,0.35);
  border-radius: 99px;
  padding: 1.4mm 3.2mm;
  margin: 0 1.5mm 1.8mm 0;
  font-size: 8.5pt;
  color: #f7f4ee;
}
.cover-meta {
  width: 100%;
  border-collapse: collapse;
  margin-top: 8mm;
}
.cover-meta td {
  border-top: 1px solid rgba(247,244,238,0.25);
  padding: 3.2mm 3mm 3.2mm 0;
  vertical-align: top;
  width: 50%;
}
.cover-meta .mk {
  display: block;
  font-size: 8pt;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: #ead9a6;
}
.cover-meta .mv { font-size: 11pt; color: #fff; }
.cover-label {
  margin: 12mm 0 3.5mm 0;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  font-size: 8.5pt;
  color: #ead9a6;
}
ul.cover-points {
  margin: 0;
  padding-left: 5mm;
  max-width: 168mm;
}
ul.cover-points li {
  font-size: 12pt;
  line-height: 1.35;
  margin: 0 0 2.6mm 0;
  color: #f7f4ee;
}
.cover-foot {
  position: absolute;
  left: 18mm; right: 16mm; bottom: 14mm;
  font-size: 9pt;
  color: #d9d0c2;
}
table.data thead { display: table-header-group; }
h2 {
  font-size: 15pt;
  line-height: 1.2;
  color: __ACCENT__;
  margin: 5.5mm 0 2.2mm 0;
  padding-bottom: 1mm;
  border-bottom: 0.4mm solid __ACCENT__;
  break-after: avoid;
  page-break-after: avoid;
}
h3 {
  font-size: 12pt;
  line-height: 1.25;
  margin: 4mm 0 1.4mm 0;
  color: #1c1915;
  break-after: avoid;
  page-break-after: avoid;
}
p { margin: 0 0 2.4mm 0; }
ul, ol { margin: 0 0 2.6mm 0; padding-left: 5mm; }
li { margin: 0 0 1mm 0; }
strong { font-weight: 650; }
table.data {
  width: 100%;
  border-collapse: collapse;
  margin: 1.5mm 0 3.5mm 0;
  font-size: 9pt;
  line-height: 1.35;
}
table.data th {
  background: __ACCENT__;
  color: #fff;
  text-align: left;
  font-weight: 650;
  padding: 1.6mm 1.8mm;
  vertical-align: bottom;
}
table.data td {
  padding: 1.5mm 1.8mm;
  vertical-align: top;
  border-bottom: 0.2mm solid #e4dfd6;
}
table.data tr { break-inside: avoid; page-break-inside: avoid; }
table.data tbody tr:nth-child(even) td { background: __BAND__; }
table.data .id { white-space: nowrap; font-weight: 650; color: __ACCENT__; width: 14mm; }
aside.note {
  border-left: 1.1mm solid __ACCENT2__;
  background: __BAND__;
  padding: 2.2mm 3.2mm;
  margin: 2mm 0 3mm 0;
  font-size: 10pt;
  break-inside: avoid;
  page-break-inside: avoid;
}
figure.diagram {
  margin: 2mm 0 3.5mm 0;
  break-inside: avoid;
  page-break-inside: avoid;
}
figure.diagram figcaption {
  font-size: 8.5pt;
  color: #5c564c;
  margin-top: 1.2mm;
}
.small { font-size: 9.5pt; color: #3f3a34; }
"""


def esc(text: str) -> str:
    return (
        text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace('"', "&quot;")
    )


def wrap(text: str, width: int) -> list[str]:
    words = text.split()
    lines: list[str] = []
    cur = ""
    for word in words:
        trial = word if not cur else f"{cur} {word}"
        if len(trial) <= width:
            cur = trial
        else:
            if cur:
                lines.append(cur)
            cur = word
    if cur:
        lines.append(cur)
    return lines or [""]


def banded(bands: list[tuple[str, list[tuple[str, str]]]], accent: str, band: str) -> str:
    """Horizontal layer diagram. bands: (layer label, [(title, subtitle), ...])."""
    width = 1000
    label_w = 108
    top = 6
    gap = 6
    parts = []
    y = top
    layouts = []
    for label, boxes in bands:
        n = max(1, len(boxes))
        area_w = width - label_w - 12
        gutter = 7
        box_w = (area_w - gutter * (n - 1)) / n
        max_chars = max(14, int((box_w - 22) / 6.5))
        drawn = []
        text_rows = 1
        for title, sub in boxes:
            t_lines = wrap(title, max_chars)[:2]
            s_lines = wrap(sub, max_chars + 2)[:2]
            drawn.append((t_lines, s_lines))
            text_rows = max(text_rows, len(t_lines) + len(s_lines))
        band_h = 18 + text_rows * 14 + 12
        layouts.append((label, drawn, box_w, gutter, band_h))
    height = top + sum(item[4] + gap for item in layouts)
    parts.append(
        f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {width} {height}" '
        f'width="100%" role="img">'
    )
    parts.append(f'<rect width="{width}" height="{height}" fill="#fffcf8" rx="8"/>')
    for label, drawn, box_w, gutter, band_h in layouts:
        parts.append(f'<rect x="0" y="{y}" width="{width}" height="{band_h}" fill="{band}"/>')
        parts.append(
            f'<text x="12" y="{y + band_h / 2 + 4}" font-family="Segoe UI, Arial" '
            f'font-size="12" font-weight="700" fill="{accent}">{esc(label)}</text>'
        )
        for i, (t_lines, s_lines) in enumerate(drawn):
            bx = label_w + i * (box_w + gutter)
            by = y + 7
            bh = band_h - 14
            parts.append(
                f'<rect x="{bx:.1f}" y="{by}" width="{box_w:.1f}" height="{bh}" rx="6" '
                f'fill="#ffffff" stroke="#e6e0d6"/>'
            )
            parts.append(
                f'<rect x="{bx:.1f}" y="{by}" width="4" height="{bh}" fill="{accent}"/>'
            )
            ty = by + 16
            for line in t_lines:
                parts.append(
                    f'<text x="{bx + 12:.1f}" y="{ty}" font-family="Segoe UI, Arial" '
                    f'font-size="12" font-weight="700" fill="#1c1915">{esc(line)}</text>'
                )
                ty += 14
            ty += 1
            for line in s_lines:
                parts.append(
                    f'<text x="{bx + 12:.1f}" y="{ty}" font-family="Segoe UI, Arial" '
                    f'font-size="10" fill="#5c564c">{esc(line)}</text>'
                )
                ty += 13
        y += band_h + gap
    parts.append("</svg>")
    return "".join(parts)


_MARKER = 0


def _marker(prefix: str, accent: str) -> tuple[str, str]:
    global _MARKER
    _MARKER += 1
    mid = f"{prefix}{_MARKER}"
    defs = (
        f'<defs><marker id="{mid}" markerWidth="6" markerHeight="6" refX="5" refY="3" orient="auto">'
        f'<path d="M0 0 L6 3 L0 6 Z" fill="{accent}"/></marker></defs>'
    )
    return mid, defs


def flow(steps: list[tuple[str, str]], accent: str) -> str:
    width = 1000
    n = len(steps)
    arrow = 16
    box_w = (width - arrow * (n - 1)) / n
    height = 96
    mid, defs = _marker("f", accent)
    parts = [
        f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {width} {height}" width="100%" role="img">',
        defs,
        f'<rect width="{width}" height="{height}" fill="#fffcf8"/>',
    ]
    max_chars = max(12, int((box_w - 16) / 6.4))
    for i, (title, sub) in enumerate(steps):
        bx = i * (box_w + arrow)
        parts.append(
            f'<rect x="{bx:.1f}" y="8" width="{box_w:.1f}" height="80" rx="6" fill="#ffffff" stroke="{accent}"/>'
        )
        t_lines = wrap(title, max_chars)[:2]
        s_lines = wrap(sub, max_chars + 2)[:2]
        ty = 28
        for line in t_lines:
            parts.append(
                f'<text x="{bx + 8:.1f}" y="{ty}" font-family="Segoe UI, Arial" font-size="11" '
                f'font-weight="700" fill="#1c1915">{esc(line)}</text>'
            )
            ty += 13
        for line in s_lines:
            parts.append(
                f'<text x="{bx + 8:.1f}" y="{ty + 2}" font-family="Segoe UI, Arial" font-size="9.5" '
                f'fill="#5c564c">{esc(line)}</text>'
            )
            ty += 12
        if i < n - 1:
            ax = bx + box_w + 2
            parts.append(
                f'<path d="M{ax:.1f} 48 L{ax + 10:.1f} 48" stroke="{accent}" stroke-width="1.4" '
                f'fill="none" marker-end="url(#{mid})"/>'
            )
    parts.append("</svg>")
    return "".join(parts)


def columns(groups: list[tuple[str, list[str]]], accent: str, band: str) -> str:
    width = 1000
    n = len(groups)
    gutter = 26
    col_w = (width - gutter * (n - 1)) / n
    rows = max(len(items) for _, items in groups)
    height = 42 + rows * 30 + 12
    mid, defs = _marker("c", accent)
    parts = [
        f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {width} {height}" width="100%" role="img">',
        defs,
        f'<rect width="{width}" height="{height}" fill="#fffcf8" rx="8"/>',
    ]
    for i, (heading, items) in enumerate(groups):
        x = i * (col_w + gutter)
        parts.append(
            f'<rect x="{x:.1f}" y="0" width="{col_w:.1f}" height="{height}" rx="8" fill="{band}"/>'
        )
        parts.append(
            f'<text x="{x + 12:.1f}" y="24" font-family="Segoe UI, Arial" font-size="12" '
            f'font-weight="700" fill="{accent}">{esc(heading)}</text>'
        )
        for j, item in enumerate(items):
            iy = 36 + j * 30
            parts.append(
                f'<rect x="{x + 10:.1f}" y="{iy}" width="{col_w - 20:.1f}" height="24" rx="4" fill="#ffffff"/>'
            )
            parts.append(
                f'<text x="{x + 18:.1f}" y="{iy + 16}" font-family="Segoe UI, Arial" font-size="11" '
                f'fill="#1c1915">{esc(item)}</text>'
            )
        if i < n - 1:
            mx = x + col_w + 4
            parts.append(
                f'<path d="M{mx:.1f} {height/2:.0f} L{mx + 16:.1f} {height/2:.0f}" stroke="{accent}" '
                f'stroke-width="1.6" marker-end="url(#{mid})"/>'
            )
    parts.append("</svg>")
    return "".join(parts)


def table(headers: list[str], rows: list[list[str]], id_col: bool = False) -> str:
    head = "".join(f"<th>{html}</th>" for html in headers)
    body = []
    for row in rows:
        cells = []
        for i, cell in enumerate(row):
            cls = ' class="id"' if id_col and i == 0 else ""
            cells.append(f"<td{cls}>{cell}</td>")
        body.append("<tr>" + "".join(cells) + "</tr>")
    return (
        '<table class="data"><thead><tr>'
        + head
        + "</tr></thead><tbody>"
        + "".join(body)
        + "</tbody></table>"
    )


def figure(svg: str, caption: str) -> str:
    return f'<figure class="diagram">{svg}<figcaption>{caption}</figcaption></figure>'


def note(html: str) -> str:
    return f'<aside class="note">{html}</aside>'


def render(doc: dict) -> str:
    css = (
        CSS.replace("__FOOTER__", doc["footer"])
        .replace("__COVER__", doc["cover"])
        .replace("__ACCENT2__", doc["accent2"])
        .replace("__ACCENT__", doc["accent"])
        .replace("__BAND__", doc["band"])
    )
    meta_rows = []
    meta = doc["meta"]
    for i in range(0, len(meta), 2):
        pair = meta[i : i + 2]
        cells = []
        for key, value in pair:
            cells.append(f'<td><span class="mk">{esc(key)}</span><span class="mv">{esc(value)}</span></td>')
        if len(cells) == 1:
            cells.append("<td></td>")
        meta_rows.append("<tr>" + "".join(cells) + "</tr>")
    chips = "".join(f'<span class="chip">{esc(chip)}</span>' for chip in doc["chips"])
    cover = f"""
<section class="cover">
  <p class="kicker">{esc(doc["kicker"])}</p>
  <h1>{esc(doc["title"])}</h1>
  <p class="subtitle">{esc(doc["subtitle"])}</p>
  <p class="lede">{doc["lede"]}</p>
  <div class="chips">{chips}</div>
  <table class="cover-meta">{"".join(meta_rows)}</table>
  <p class="cover-label">Architecture in brief</p>
  <ul class="cover-points">{"".join(f"<li>{esc(point)}</li>" for point in doc["cover_points"])}</ul>
  <p class="cover-foot">{esc(doc["cover_foot"])}</p>
</section>
"""
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"/>
<title>{esc(doc["title"])} — target architecture</title>
<style>{css}</style>
</head>
<body>
{cover}
{doc["body"]}
</body>
</html>
"""
