#!/bin/sh
exec ttyd -i 127.0.0.1 -p 9000 -c "$DIRIGIBLE_TERMINAL_CREDENTIAL" --writable sh
