"""Package only the tested bundle from the current commit."""
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import zipfile

root = Path(__file__).resolve().parents[2]
version = re.search(r"^version\s*=\s*(.+)$", (root / "gradle.properties").read_text(), re.M).group(1).strip()
bundle = root / "patches/build/libs" / f"patches-{version}.mpp"
head = subprocess.check_output(["git", "rev-parse", "HEAD"], cwd=root, text=True).strip()
assert head == os.environ["GITHUB_SHA"]
repository = os.environ["GITHUB_REPOSITORY"]
assert repository == "YongkeDet/piko-custom"
with zipfile.ZipFile(bundle) as archive:
    names = set(archive.namelist())
    assert {"classes.dex", "extensions/newx.mpe", "extensions/instagram.mpe"} <= names
    assert "extensions/twitter.mpe" not in names
    for notice in ("LICENSE", "NOTICE", "MORPHE-NOTICE"):
        assert archive.read("licenses/piko-custom/" + notice) == (root / notice).read_bytes()
digest = hashlib.sha256(bundle.read_bytes()).hexdigest()
feed = json.loads((root / "patches-bundle.json").read_text())
assert feed["version"] == version
assert feed["download_url"] == f"https://github.com/{repository}/releases/download/v{version}/{bundle.name}"
dist = root / "dist"
dist.mkdir(exist_ok=True)
shutil.copyfile(bundle, dist / bundle.name)
for name in ("LICENSE", "NOTICE", "MORPHE-NOTICE", "RELEASE_NOTES.md"):
    shutil.copyfile(root / name, dist / name)
record = {"source_sha": head, "repository": repository, "bundle": bundle.name,
          "target": "combined", "sha256": digest}
(dist / "source-provenance.json").write_text(json.dumps(record, indent=2) + "\n")
(dist / "SHA256SUMS").write_text(f"{digest}  {bundle.name}\n")
print(json.dumps(record))
