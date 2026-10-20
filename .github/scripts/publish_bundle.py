"""Publish only an artifact verified against this workflow's exact source."""
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess

root = Path(__file__).resolve().parents[2]
repo = os.environ["GITHUB_REPOSITORY"]
assert repo == "YongkeDet/piko-custom"
assert os.environ["GITHUB_REF"] == "refs/heads/main"
assert os.environ["GITHUB_EVENT_NAME"] in ("push", "workflow_dispatch")
version = re.search(r"^version\s*=\s*(.+)$", (root / "gradle.properties").read_text(), re.M).group(1).strip()
dist = root / "dist"
record = json.loads((dist / "source-provenance.json").read_text())
assert record["source_sha"] == os.environ["GITHUB_SHA"]
assert record["repository"] == repo and record["target"] == "combined"
assert record["bundle"] == f"patches-{version}.mpp"
bundle = dist / record["bundle"]
assert hashlib.sha256(bundle.read_bytes()).hexdigest() == record["sha256"]
assert (dist / "SHA256SUMS").read_text() == f'{record["sha256"]}  {bundle.name}\n'
for name in ("LICENSE", "NOTICE", "MORPHE-NOTICE", "RELEASE_NOTES.md"):
    assert (dist / name).read_bytes() == (root / name).read_bytes()
tag = "v" + version
prefix = f"repos/{repo}/"
def api(path):
    return json.loads(subprocess.check_output(["gh", "api", prefix + path], text=True))
existing = subprocess.run(["gh", "api", prefix + "releases/tags/" + tag], capture_output=True, text=True)
if existing.returncode:
    # Authentication and server failures are not a reason to create or overwrite a release.
    assert "HTTP 404" in existing.stderr, existing.stderr
    files = [bundle, dist / "SHA256SUMS", dist / "source-provenance.json",
             dist / "LICENSE", dist / "NOTICE", dist / "MORPHE-NOTICE"]
    subprocess.run(["gh", "release", "create", tag, *(str(p) for p in files),
        "--repo", repo, "--target", record["source_sha"], "--latest",
        "--title", "Piko Custom " + version + " — X 12.35 + IG451",
        "--notes-file", str(dist / "RELEASE_NOTES.md")], check=True)
release = api("releases/tags/" + tag)
assert not release["draft"] and not release["prerelease"]
assert release["target_commitish"] == record["source_sha"]
assert api("git/ref/tags/" + tag)["object"]["sha"] == record["source_sha"]
assets = [a for a in release["assets"] if a["name"] == bundle.name]
assert len(assets) == 1 and assets[0]["digest"] == "sha256:" + record["sha256"]
feed = json.loads((root / "patches-bundle.json").read_text())
assert feed["download_url"] == assets[0]["browser_download_url"] and feed["version"] == version
print("Verified published bundle: " + release["html_url"])
