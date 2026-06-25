#!/usr/bin/env sh
# -----------------------------------------------------------------------------
# Maven Wrapper
# -----------------------------------------------------------------------------

MAVEN_PROJECTBASEDIR="$(dirname "$0")"
MAVEN_PROJECTBASEDIR="$(cd "$MAVEN_PROJECTBASEDIR" && pwd)"

if [ -z "$JAVA_HOME" ]; then
  JAVA_CMD="java"
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

MVNW_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$MVNW_WRAPPER_JAR" ]; then
  echo "ERROR: Maven wrapper jar not found: $MVNW_WRAPPER_JAR"
  exit 1
fi

exec "$JAVA_CMD" -jar "$MVNW_WRAPPER_JAR" "$@"
