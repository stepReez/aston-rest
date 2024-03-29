CREATE TABLE "users" (
  "user_id" varchar(36),
  "user_name" varchar,
  "records_id" integer
);

CREATE TABLE "records" (
  "record_id" varchar(36),
  "author_id" integer,
  "title" varchar,
  "text" varchar
);

CREATE TABLE "likes" (
  "user_id" varchar(36),
  "record_id" varchar(36)
);

ALTER TABLE "records" ADD FOREIGN KEY ("record_id") REFERENCES "users" ("records_id");

ALTER TABLE "records" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("user_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("record_id") REFERENCES "records" ("record_id");