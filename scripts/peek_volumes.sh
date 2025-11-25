#!/bin/bash
# peek_volumes.sh
# This script opens a BusyBox container with files_volume and tmp_volume mounted.

docker run --rm -it \
  -v files_volume:/mnt/files \
  -v tmp_volume:/mnt/tmp \
  busybox sh
