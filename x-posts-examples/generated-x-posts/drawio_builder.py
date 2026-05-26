#!/usr/bin/env python3
"""Build polished diagrams.net (.drawio) architecture diagrams."""

from __future__ import annotations

import html
import uuid


def _esc(text: str) -> str:
    return html.escape(text, quote=True).replace("\n", "&#xa;")


# ── Color palette (modern, high-contrast) ─────────────────────────────────────
C = {
    "page_bg": "#F8FAFC",
    "panel_bg": "#FFFFFF",
    "panel_border": "#E2E8F0",
    "title": "#0F172A",
    "subtitle": "#64748B",
    "eureka_fill": "#4F46E5",
    "eureka_stroke": "#3730A3",
    "eureka_text": "#FFFFFF",
    "books_fill": "#10B981",
    "books_stroke": "#059669",
    "author_fill": "#8B5CF6",
    "author_stroke": "#7C3AED",
    "review_fill": "#F59E0B",
    "review_stroke": "#D97706",
    "cb_fill": "#F97316",
    "cb_stroke": "#EA580C",
    "fallback_fill": "#EF4444",
    "fallback_stroke": "#DC2626",
    "closed_fill": "#22C55E",
    "open_fill": "#EF4444",
    "half_fill": "#EAB308",
    "edge": "#64748B",
    "edge_call": "#0EA5E9",
    "edge_fail": "#EF4444",
    "good": "#16A34A",
    "bad": "#DC2626",
    "code_outer": "#0F172A",
    "code_header": "#1E293B",
    "code_body": "#0F172A",
    "code_border": "#334155",
    "code_text": "#E2E8F0",
}


def _shape(fill: str, stroke: str, text_color: str = "#FFFFFF", bold: bool = True) -> str:
    fw = "1" if bold else "0"
    return (
        f"rounded=1;whiteSpace=wrap;html=1;arcSize=12;"
        f"fillColor={fill};strokeColor={stroke};strokeWidth=2;"
        f"fontColor={text_color};fontSize=13;fontStyle={fw};"
        f"shadow=1;align=center;verticalAlign=middle;"
    )


def _text_style(size: int = 12, color: str = C["subtitle"], align: str = "left", bold: bool = False) -> str:
    fw = "1" if bold else "0"
    return (
        f"text;html=1;strokeColor=none;fillColor=none;"
        f"align={align};verticalAlign=middle;"
        f"fontSize={size};fontStyle={fw};fontColor={color};"
    )


def _edge_style(color: str = C["edge"], width: int = 2, dashed: bool = False) -> str:
    s = (
        f"edgeStyle=orthogonalEdgeStyle;rounded=1;orthogonalLoop=1;"
        f"jettySize=auto;html=1;endArrow=block;endFill=1;startArrow=none;"
        f"strokeColor={color};strokeWidth={width};"
        f"fontColor={C['subtitle']};fontSize=11;"
    )
    if dashed:
        s += "dashed=1;dashPattern=8 6;"
    return s


class DrawioBuilder:
    def __init__(self, name: str, w: int = 1160, h: int = 1280) -> None:
        self.name = name
        self.w, self.h = w, h
        self._nid = 2
        self.cells: list[str] = []

    def _id(self) -> str:
        i = str(self._nid)
        self._nid += 1
        return i

    def box(self, x, y, w, h, label, style, parent="1") -> str:
        i = self._id()
        self.cells.append(
            f'        <mxCell id="{i}" value="{_esc(label)}" style="{style}" vertex="1" parent="{parent}">'
            f'<mxGeometry x="{x}" y="{y}" width="{w}" height="{h}" as="geometry"/></mxCell>'
        )
        return i

    def edge(self, src, tgt, label="", color=None, dashed=False, points: list[tuple[float, float]] | None = None) -> str:
        i = self._id()
        pts_xml = ""
        if points:
            pts = "\n".join(f'            <mxPoint x="{px}" y="{py}"/>' for px, py in points)
            pts_xml = f"<Array as=\"points\">\n{pts}\n          </Array>"
        self.cells.append(
            f'        <mxCell id="{i}" value="{_esc(label)}" style="{_edge_style(color or C["edge"], dashed=dashed)}" '
            f'edge="1" parent="1" source="{src}" target="{tgt}">'
            f'<mxGeometry relative="1" as="geometry">{pts_xml}</mxGeometry></mxCell>'
        )
        return i

    def code_panel(self, x, y, w, title: str, code: str) -> None:
        """Single rectangular code block with header bar + formatted body."""
        lines = code.strip().split("\n")
        line_h = 16
        body_h = max(120, len(lines) * line_h + 24)
        total_h = 36 + body_h + 8

        # Outer frame
        self.box(
            x, y, w, total_h, "",
            f"rounded=1;whiteSpace=wrap;html=1;arcSize=8;"
            f"fillColor={C['code_outer']};strokeColor={C['code_border']};strokeWidth=2;shadow=1;",
        )
        # Header (mac-style dots + title)
        self.box(
            x + 2, y + 2, w - 4, 32, "",
            f"rounded=1;whiteSpace=wrap;html=1;fillColor={C['code_header']};"
            f"strokeColor=none;arcSize=6;align=left;",
        )
        self.box(x + 14, y + 12, 10, 10, "", "ellipse;fillColor=#FF5F57;strokeColor=#E0443E;")
        self.box(x + 30, y + 12, 10, 10, "", "ellipse;fillColor=#FFBD2E;strokeColor=#DEA123;")
        self.box(x + 46, y + 12, 10, 10, "", "ellipse;fillColor=#27C93F;strokeColor=#1AAB29;")
        self.box(
            x + 70, y + 4, w - 80, 28, title,
            _text_style(12, C["code_text"], "left", True),
        )
        # Code body — one rectangle
        formatted = _esc(code.strip())
        self.box(
            x + 8, y + 38, w - 16, body_h,
            formatted,
            f"rounded=1;whiteSpace=wrap;html=1;align=left;verticalAlign=top;"
            f"fillColor={C['code_body']};strokeColor=#475569;strokeWidth=1;"
            f"fontColor={C['code_text']};fontFamily=Courier New;fontSize=11;"
            f"spacingLeft=14;spacingTop=12;spacingBottom=12;",
        )

    def section(self, x, y, w, h, title: str) -> float:
        """Architecture panel background; returns inner y offset."""
        self.box(x, y, w, h, "", f"rounded=1;fillColor={C['panel_bg']};strokeColor={C['panel_border']};strokeWidth=2;shadow=0;")
        self.box(x + 20, y + 14, w - 40, 28, title, _text_style(15, C["title"], "center", True))
        return y + 52

    def build(self) -> str:
        body = "\n".join(self.cells)
        return f"""<?xml version="1.0" encoding="UTF-8"?>
<mxfile host="app.diagrams.net" agent="codingstrain" version="22.1.8" type="device">
  <diagram id="{uuid.uuid4()}" name="{_esc(self.name)}">
    <mxGraphModel dx="1600" dy="900" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="{self.w}" pageHeight="{self.h}" math="0" shadow="0" background="{C['page_bg']}">
      <root>
        <mxCell id="0"/>
        <mxCell id="1" parent="0"/>
{body}
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>"""


def build_eureka_drawio() -> str:
    """Tweet #52 — diagrams.net architecture only (code → CodePen)."""
    b = DrawioBuilder("Tweet 52 · Diagram", w=1160, h=520)
    M = 40
    PW = 1080

    b.box(M, 16, 520, 32, "Tweet #52 · Eureka Service Discovery", _text_style(20, C["title"], bold=True))
    b.box(M, 48, 400, 22, "spring-cloud-discovery · codingstrain.com", _text_style(11, C["subtitle"]))

    inner_y = b.section(M, 78, PW, 400, "Service discovery flow")

    # Hub at top center
    eureka = b.box(
        M + 430, inner_y + 20, 220, 88,
        "Eureka Server&#xa;:8761&#xa;service registry",
        _shape(C["eureka_fill"], C["eureka_stroke"]),
    )

    # Services row — evenly spaced, no overlap
    sy = inner_y + 200
    books = b.box(M + 60, sy, 200, 82, "books-service&#xa;:8081", _shape(C["books_fill"], C["books_stroke"]))
    author = b.box(M + 440, sy, 200, 82, "author-service&#xa;:8082", _shape(C["author_fill"], C["author_stroke"]))
    review = b.box(M + 820, sy, 200, 82, "review-service&#xa;:8083", _shape(C["review_fill"], C["review_stroke"]))

    # Registration edges — vertical, routed via waypoints above services
    mid_y = inner_y + 175
    b.edge(books, eureka, "register", C["edge"], points=[(M + 160, mid_y), (M + 500, mid_y)])
    b.edge(author, eureka, "register", C["edge"], points=[(M + 540, mid_y)])
    b.edge(review, eureka, "register", C["edge"], points=[(M + 920, mid_y), (M + 620, mid_y)])

    # Service call — horizontal, below boxes
    call_y = sy + 100
    b.edge(books, author, "GET /authors/{id}", C["edge_call"], points=[(M + 160, call_y), (M + 540, call_y)])

    # Comparison labels
    ly = inner_y + 330
    b.box(M + 50, ly, 420, 36, "✗  http://192.168.1.42:8082  (hard-coded IP)", _text_style(12, C["bad"], bold=True))
    b.box(M + 520, ly, 480, 36, "✓  http://author-service/authors/1  (logical name)", _text_style(12, C["good"], bold=True))
    return b.build()


def build_circuit_breaker_drawio() -> str:
    """Tweet #53 — diagrams.net architecture only (code → CodePen)."""
    b = DrawioBuilder("Tweet 53 · Diagram", w=1160, h=580)
    M = 40
    PW = 1080

    b.box(M, 16, 560, 32, "Tweet #53 · OpenFeign + Circuit Breaker", _text_style(20, C["title"], bold=True))
    b.box(M, 48, 520, 22, "libraryapp-openfeign-discovery-circuitbreaker", _text_style(11, C["subtitle"]))

    inner_y = b.section(M, 78, PW, 440, "Resilience4j + OpenFeign")

    # Horizontal pipeline — generous spacing
    cy = inner_y + 80
    books = b.box(M + 40, cy, 190, 78, "books-service&#xa;@EnableFeignClients", _shape(C["books_fill"], C["books_stroke"]))
    cb = b.box(M + 300, cy - 10, 240, 98, "Circuit Breaker&#xa;CircuitBreakerApi", _shape(C["cb_fill"], C["cb_stroke"]))
    author = b.box(M + 610, cy, 190, 78, "author-service&#xa;(may fail / timeout)", _shape(C["author_fill"], C["author_stroke"]))

    b.edge(books, cb, "Feign", C["edge_call"])
    b.edge(cb, author, "HTTP", C["edge"])

    # States inside CB panel area — below circuit breaker, not overlapping
    sy = cy + 115
    b.box(M + 310, sy, 95, 32, "CLOSED", _shape(C["closed_fill"], "#15803D", bold=True))
    b.box(M + 420, sy, 95, 32, "OPEN", _shape(C["open_fill"], "#B91C1C", bold=True))
    b.box(M + 530, sy, 110, 32, "HALF-OPEN", _shape(C["half_fill"], "#A16207", bold=True))

    # State flow annotation
    b.box(
        M + 280, sy + 44, 580, 28,
        "CLOSED  →  failures &gt; 50%  →  OPEN  →  HALF-OPEN  →  CLOSED",
        _text_style(11, C["subtitle"], "center"),
    )

    # Fallback — centered under CB, dashed edge only
    fy = sy + 90
    fallback = b.box(M + 355, fy, 230, 62, "fallbackMethod()&#xa;&quot;Fallback content&quot;", _shape(C["fallback_fill"], C["fallback_stroke"]))
    b.edge(cb, fallback, "circuit OPEN", C["edge_fail"], dashed=True, points=[(M + 420, sy + 32), (M + 470, fy - 8)])

    # Without / with
    ly = inner_y + 360
    b.box(M + 40, ly, 480, 34, "Without CB: threads blocked · cascade failures", _text_style(12, C["bad"], bold=True))
    b.box(M + 560, ly, 480, 34, "With CB: fail fast · degraded response", _text_style(12, C["good"], bold=True))
    return b.build()


# Full snippets for CodePen pens (tweets #52 / #53)
EUREKA_CODE_SNIPPET = """// ── 1) Eureka Server (registry) ──────────────────────
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApp {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApp.class, args);
    }
}

// ── 2) Client microservice ─────────────────────────────
// application.yml
//   spring.application.name: books-service

// ── 3) Call another service by name (no fixed IP) ─────
@FeignClient(name = "author-service")
public interface AuthorClient {
    @GetMapping("/authors/{id}")
    Author getAuthor(@PathVariable String id);
}"""

CIRCUIT_BREAKER_CODE_SNIPPET = """// ── Feign client + circuit breaker on remote call ───
@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/authors/getInstance")
    @CircuitBreaker(name = "CircuitBreakerService")
    String getInstance();
}

// ── Controller with fallback when circuit is OPEN ───
@RestController
public class BookController {

    @GetMapping("/getAuthorServiceInstance")
    @CircuitBreaker(name = "CircuitBreakerApi",
            fallbackMethod = "getAuthorServiceInstanceFallback")
    public String getAuthorServiceInstance() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceFallback(Exception ex) {
        return "Fallback content";
    }
}

// ── application.yml (Resilience4j) ───────────────────
// resilience4j.circuitbreaker.instances.CircuitBreakerApi:
//   failure-rate-threshold: 50
//   wait-duration-in-open-state: 5s"""
