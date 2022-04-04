#! /bin/sh

GRADLE_FILE="gradlew"
if [ ! -f "$GRADLE_FILE" ]; then
  echo "Script should be run from the directory where $GRADLE_FILE file is present."
  exit 1
fi

./gradlew dokkaHtml
./gradlew dokkaGfm
./gradlew ktlintFormat || true
./gradlew ktlintReport || true

rm -rf docs/codelabs/
mkdir -p docs/codelabs/

echo "#Codelabs" > docs/codelabs/index.md
echo "" >> docs/codelabs/index.md
echo "## Index" >> docs/codelabs/index.md

for f in codelabs/*.md; do
  exp="${f%.md}"
  dir="$exp/"
  name="${exp##*/}"
  rm -rf "$dir"
  ~/go/bin/claat export "$f"
  mv "$name" docs/codelabs/
  echo "* [$name](./$name/index.html)" >> docs/codelabs/index.md
done




