#!/usr/bin/env python3
"""Topic-based diagrams.net architecture diagrams for every tweet."""

from __future__ import annotations

import re
from pathlib import Path
from typing import Any

_DIAGRAMS_DIR = Path(__file__).resolve().parent.parent / "diagrams"

from drawio_builder import (
    C,
    DrawioBuilder,
    _shape,
    _text_style,
    build_circuit_breaker_drawio,
    build_eureka_drawio,
)

# Node colors by role
N = {
    "client": ("#3B82F6", "#1D4ED8"),
    "controller": ("#10B981", "#059669"),
    "service": ("#8B5CF6", "#7C3AED"),
    "repo": ("#F59E0B", "#D97706"),
    "db": ("#EF4444", "#DC2626"),
    "kafka": ("#F97316", "#EA580C"),
    "config": ("#6366F1", "#4F46E5"),
    "advice": ("#EC4899", "#DB2777"),
    "cache": ("#14B8A6", "#0D9488"),
    "file": ("#64748B", "#475569"),
    "pool": ("#A855F7", "#9333EA"),
}


def _hook(tweet: dict) -> str:
    line = tweet.get("body", "").strip().split("\n")[0]
    line = re.sub(r"^[🚀💡🧵]\s*", "", line)
    return (line[:58] + "…") if len(line) > 60 else line


def _bullets(tweet: dict, max_n: int = 3) -> list[str]:
    out: list[str] = []
    for ln in tweet.get("body", "").split("\n"):
        ln = ln.strip().lstrip("•-* ")
        if ln.startswith("✅"):
            out.append(ln[1:].strip()[:70])
        elif ln and not ln.startswith("🚀") and len(out) < max_n and len(ln) > 12:
            out.append(ln[:70])
    return out[:max_n]


def infer_diagram_type(tweet: dict) -> str:
    explicit = tweet.get("diagram_type")
    if explicit in ("eureka", "circuit_breaker", "sealed_classes"):
        return explicit

    module = tweet.get("module", "").lower()
    body = tweet.get("body", "").lower()
    code = tweet.get("code", "").lower()
    text = f"{module} {body} {code}"

    if "circuit" in text or "feign" in text or "resilience" in text:
        return "circuit_breaker"
    if "kafka" in text:
        return "kafka"
    if "transactionaleventlistener" in text or "publisherevent" in text or "transactional-event" in module:
        return "event"
    if "async" in module or "@async" in code:
        return "async"
    if "redis" in text or "cacheable" in text or "cache" in module:
        return "cache"
    if "cors" in module:
        return "cors"
    if "spring-batch" in module or "itemreader" in code or "chunk(" in code:
        return "batch"
    if "csv" in module and "batch" not in module:
        return "csv"
    if "error-handling" in module or "exceptionhandler" in code or "restcontrolleradvice" in code:
        return "exception"
    if "validated-config" in module or "configurationproperties" in code:
        return "config"
    if "cloud-config" in module:
        return "config_server"
    if "streaming" in module or "stream<" in code or "em.clear" in code:
        return "stream"
    if "sql-annotation" in module or "jparepository" in code or "@sql" in code:
        return "jpa"
    if "mock-reset" in module or "springboottest" in code or "mockbean" in code:
        return "testing"
    if "pact" in text or "gatling" in text:
        return "microservice_test"
    if "actuator" in module or "actuator/health" in body:
        return "actuator"
    if "codingstrain" in module and tweet["id"] in (48, 55, 56):
        return "repo"
    if "java-17-sealed" in module or ("sealed" in text and "permits" in text):
        return "sealed_classes"
    if "java-examples" in module or "java-miscellaneous" in module or "builder" in text:
        return "java_pattern"
    if "restcontroller" in code or "getmapping" in code or "minimal-rest" in module or "paged-rest" in module:
        return "rest"
    return "concept"


def _header(b: DrawioBuilder, tweet: dict, M: int = 40) -> None:
    tid = tweet["id"]
    b.box(M, 16, 760, 32, f"Tweet #{tid} · {_hook(tweet)}", _text_style(20, C["title"], bold=True))
    b.box(M, 48, 520, 22, f"{tweet.get('module', '')} · codingstrain", _text_style(11, C["subtitle"]))


def _pipeline(
    tweet: dict,
    section: str,
    nodes: list[tuple[str, str]],
    labels: list[str] | None = None,
    footers: list[tuple[str, str]] | None = None,
    h: int = 480,
) -> str:
    """Horizontal pipeline: list of (label, role_key)."""
    b = DrawioBuilder(f"Tweet {tweet['id']} · Diagram", w=1160, h=h)
    M, PW = 40, 1080
    _header(b, tweet)
    inner_y = b.section(M, 78, PW, h - 160, section)

    n = len(nodes)
    gap = (PW - 40) // n
    bw = min(200, gap - 24)
    cy = inner_y + 70
    ids: list[str] = []

    for i, (label, role) in enumerate(nodes):
        fill, stroke = N.get(role, N["service"])
        x = M + 20 + i * gap + (gap - bw) // 2
        ids.append(b.box(x, cy, bw, 78, label, _shape(fill, stroke)))

    lbls = labels or []
    for i in range(len(ids) - 1):
        b.edge(ids[i], ids[i + 1], lbls[i] if i < len(lbls) else "")

    if footers:
        ly = inner_y + 200
        for i, (txt, kind) in enumerate(footers):
            color = C["good"] if kind == "good" else C["bad"] if kind == "bad" else C["subtitle"]
            b.box(M + 40 + i * 520, ly, 500, 34, txt, _text_style(12, color, bold=kind != "note"))

    return b.build()


def _concept_diagram(tweet: dict) -> str:
    """Tip / checklist tweets — flow of key ideas."""
    bullets = _bullets(tweet) or [_hook(tweet)]
    b = DrawioBuilder(f"Tweet {tweet['id']} · Diagram", w=1160, h=420)
    M, PW = 40, 1080
    _header(b, tweet)
    inner_y = b.section(M, 78, PW, 300, "Concept flow")

    prev = b.box(M + 80, inner_y + 60, 200, 70, "Problem / context", _shape(*N["client"]))
    for i, bullet in enumerate(bullets):
        x = M + 320 + i * 260
        node = b.box(x, inner_y + 60, 240, 70, bullet, _shape(*N["service"]))
        b.edge(prev, node, "→" if i == 0 else "")
        prev = node

    outcome = b.box(M + PW - 280, inner_y + 180, 240, 56, "✓ See CodePen for implementation", _text_style(12, C["good"], "center", True))
    b.edge(prev, outcome, "")
    return b.build()


def build_sealed_classes_drawio() -> str:
    candidates = (
        _DIAGRAMS_DIR / "java-17-sealed-classes.drawio",
        Path(__file__).resolve().parent / "drawio" / "tweet-61.drawio",
    )
    for path in candidates:
        if path.is_file():
            return path.read_text(encoding="utf-8")
    raise FileNotFoundError(
        "Sealed-classes diagram missing. Add diagrams/java-17-sealed-classes.drawio "
        "or run a prior export to drawio/tweet-61.drawio."
    )


def build_tweet_diagram(tweet: dict) -> str:
    kind = infer_diagram_type(tweet)

    if kind == "eureka":
        return build_eureka_drawio()
    if kind == "circuit_breaker":
        return build_circuit_breaker_drawio()
    if kind == "sealed_classes":
        return build_sealed_classes_drawio()

    builders: dict[str, Any] = {
        "rest": lambda: _pipeline(
            tweet,
            "REST API layers",
            [
                ("HTTP Client", "client"),
                ("@RestController", "controller"),
                ("Service", "service"),
                ("Repository", "repo"),
                ("H2 / DB", "db"),
            ],
            ["GET/POST", "business logic", "JPA", "SQL"],
            [("✓ Constructor injection · layered design", "good")],
        ),
        "exception": lambda: _pipeline(
            tweet,
            "Global exception handling",
            [
                ("HTTP Client", "client"),
                ("Controller", "controller"),
                ("@RestControllerAdvice", "advice"),
                ("ErrorResponse JSON", "client"),
            ],
            ["request", "throws", "maps to 4xx/5xx"],
            [("✗ Stack trace to client", "bad"), ("✓ Stable JSON error body", "good")],
            h=460,
        ),
        "config": lambda: _pipeline(
            tweet,
            "Validated configuration",
            [
                ("application.yml", "config"),
                ("@ConfigurationProperties", "config"),
                ("@Validated", "advice"),
                ("Spring Context", "service"),
            ],
            ["bind", "Bean Validation", "startup"],
            [("✓ Fail at startup, not in production", "good")],
        ),
        "jpa": lambda: _pipeline(
            tweet,
            "Spring Data JPA",
            [
                ("Service / Test", "service"),
                ("JpaRepository", "repo"),
                ("Entity", "repo"),
                ("Database", "db"),
            ],
            ["CRUD", "mapping", "SQL"],
        ),
        "stream": lambda: _pipeline(
            tweet,
            "Streaming large datasets",
            [
                ("1M+ rows", "db"),
                ("Stream&lt;User&gt;", "repo"),
                ("process()", "service"),
                ("em.clear() every N", "pool"),
            ],
            ["@Query", "forEach", "free memory"],
            [("✗ findAll() → OOM", "bad"), ("✓ Stream + clear()", "good")],
            h=460,
        ),
        "kafka": lambda: _pipeline(
            tweet,
            "Kafka event flow",
            [
                ("MessageProducer", "service"),
                ("Kafka Topic", "kafka"),
                ("@KafkaListener", "controller"),
                ("Consumer logic", "service"),
            ],
            ["send()", "demo-topic", "groupId"],
        ),
        "event": lambda: _pipeline(
            tweet,
            "Transactional events",
            [
                ("createOrder()", "service"),
                ("DB commit", "db"),
                ("ApplicationEvent", "kafka"),
                ("@TransactionalEventListener", "advice"),
            ],
            ["@Transactional", "publishEvent", "AFTER_COMMIT"],
        ),
        "async": lambda: _pipeline(
            tweet,
            "Async execution",
            [
                ("Caller bean", "client"),
                ("@Async executor", "pool"),
                ("Background task", "service"),
            ],
            ["proxy call", "thread pool"],
            [("✗ @Async from same class — ignored", "bad"), ("✓ Call from another bean", "good")],
            h=440,
        ),
        "cache": lambda: _pipeline(
            tweet,
            "Redis caching",
            [
                ("Request", "client"),
                ("@Cacheable", "cache"),
                ("Redis", "db"),
                ("External API", "service"),
            ],
            ["lookup", "hit / miss", "fetch"],
        ),
        "cors": lambda: _pipeline(
            tweet,
            "CORS configuration",
            [
                ("Browser", "client"),
                ("CorsFilter / WebMvc", "advice"),
                ("@RestController", "controller"),
            ],
            ["preflight", "allowed origins"],
        ),
        "batch": lambda: _pipeline(
            tweet,
            "Spring Batch pipeline",
            [
                ("CSV file", "file"),
                ("ItemReader", "repo"),
                ("ItemProcessor", "service"),
                ("ItemWriter", "db"),
            ],
            ["chunk(5)", "transform", "write"],
        ),
        "csv": lambda: _pipeline(
            tweet,
            "CSV parsing",
            [
                ("CSV file", "file"),
                ("BufferedReader", "repo"),
                ("CSVParser", "service"),
                ("Records", "client"),
            ],
            ["try-with-resources", "withHeader()"],
        ),
        "testing": lambda: _pipeline(
            tweet,
            "Spring Boot testing",
            [
                ("@SpringBootTest", "advice"),
                ("@MockBean / @Sql", "repo"),
                ("Component under test", "service"),
                ("Assertions", "client"),
            ],
            ["context", "stub data"],
        ),
        "config_server": lambda: _pipeline(
            tweet,
            "Spring Cloud Config",
            [
                ("Microservice instances", "client"),
                ("Config Server", "config"),
                ("Config repo (git/native)", "db"),
            ],
            ["bootstrap", "centralized YAML"],
        ),
        "microservice_test": lambda: _pipeline(
            tweet,
            "Microservice testing",
            [
                ("books-service", "service"),
                ("Pact / Gatling", "advice"),
                ("author-service", "service"),
            ],
            ["contract tests", "load tests"],
        ),
        "actuator": lambda: _pipeline(
            tweet,
            "Actuator health checks",
            [
                ("Spring Boot App", "service"),
                ("/actuator/health", "controller"),
                ("Monitor / CI", "client"),
            ],
            ["exposes status", "read before deploy"],
        ),
        "repo": lambda: _pipeline(
            tweet,
            "codingstrain workflow",
            [
                ("git clone", "file"),
                ("pick one module", "config"),
                ("mvn spring-boot:run", "service"),
                ("learn one concept", "client"),
            ],
        ),
        "java_pattern": lambda: _pipeline(
            tweet,
            "Java pattern",
            [
                ("Caller code", "client"),
                ("Builder / API", "service"),
                ("Immutable object", "repo"),
            ],
            ["fluent API", ".build()"],
        ),
    }

    if kind in builders:
        return builders[kind]()
    return _concept_diagram(tweet)
