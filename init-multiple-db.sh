#!/bin/bash
set -e

# Danh sách database muốn tạo
databases=("movie_review_auth" "movie_review_profile" "movie_review_movie" "movie_review_rating")

for db in "${databases[@]}"; do
  echo "Creating database: $db"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
      CREATE DATABASE $db;
EOSQL
done