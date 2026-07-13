# X Post Generator

Shared tooling and master `tweets.json` for **260** codingstrain tweets.

## Categories

| Folder | Posts |
|--------|-------|
| [`java-posts/`](java-posts/) | 136 Java tweets |
| [`spring-boot-posts/`](spring-boot-posts/) | 124 Spring Boot tweets |

Open [`index.html`](index.html) to pick a category.

## Regenerate

```bash
python generate_assets.py --skip-png   # fast: codepen + index + per-category tweets.json
python generate_assets.py              # also exports diagram PNGs (slow)
```

## Scripts

| File | Role |
|------|------|
| `generate_assets.py` | Build both category folders from `tweets.json` |
| `codepen_builder.py` | CodePen Carbon embeds |
| `diagram_templates.py` | diagrams.net templates |
| `drawio_builder.py` | Draw.io XML helpers |
