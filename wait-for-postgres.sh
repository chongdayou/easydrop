#!/bin/sh

host="$1"
shift

echo "Waiting for PostgreSQL at $host:5432..."

while ! nc -z "$host" 5432; do
  echo "Postgres is unavailable - sleeping"
  sleep 1
done

echo "Postgres is up - executing command"
exec "$@"