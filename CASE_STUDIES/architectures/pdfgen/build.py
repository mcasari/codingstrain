"""Render the four PCA target-architecture papers to PDF."""

from __future__ import annotations

import subprocess
import sys
from pathlib import Path

from pypdf import PdfReader

HERE = Path(__file__).resolve().parent
OUT = HERE.parent
CHROME = Path(r"C:\Program Files\Google\Chrome\Application\chrome.exe")

sys.path.insert(0, str(HERE))

from altostrat import document as altostrat  # noqa: E402
from cymbal import document as cymbal  # noqa: E402
from ehr import document as ehr  # noqa: E402
from knight import document as knight  # noqa: E402
from render import render  # noqa: E402


def main() -> None:
    if not CHROME.exists():
        raise SystemExit(f"Chrome not found at {CHROME}")
    html_dir = HERE / "_html"
    html_dir.mkdir(exist_ok=True)
    for build in (altostrat, cymbal, ehr, knight):
        doc = build()
        html_path = html_dir / f"{doc['slug']}.html"
        pdf_path = OUT / doc["filename"]
        html_path.write_text(render(doc), encoding="utf-8")
        uri = html_path.resolve().as_uri()
        subprocess.run(
            [
                str(CHROME),
                "--headless=new",
                "--disable-gpu",
                "--no-pdf-header-footer",
                f"--print-to-pdf={pdf_path}",
                uri,
            ],
            check=True,
        )
        reader = PdfReader(str(pdf_path))
        print(f"{pdf_path.name}: {len(reader.pages)} pages, {pdf_path.stat().st_size} bytes")


if __name__ == "__main__":
    main()
