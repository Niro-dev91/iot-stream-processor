#!/bin/sh

# This script waits for the Elasticsearch service to become available before starting the application.
#
# It attempts to connect to the specified host and port (default: elasticsearch:9200) using netcat (nc).
# If Elasticsearch is not yet reachable, it waits and retries every 3 seconds.
# Once Elasticsearch is detected as available, the script proceeds to execute the command passed as arguments.
#
# Usage:
#   ./wait-for-elasticsearch.sh <command-to-start-app>
#

host="elasticsearch"
port=9200

echo "Waiting for Elasticsearch at $host:$port..."

while ! nc -z $host $port; do
  echo "Elasticsearch is unavailable - sleeping"
  sleep 3
done

echo "Elasticsearch is up - starting app"

exec "$@"
