#!/bin/bash

set -ex

git tag -d 1.0.0-SNAPSHOT
git push origin :refs/tags/1.0.0-SNAPSHOT
git tag 1.0.0-SNAPSHOT
git push origin refs/tags/1.0.0-SNAPSHOT
