#!/usr/bin/env sh

# Gradle wrapper startup script
# Do not modify unless you know what you're doing.

DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
