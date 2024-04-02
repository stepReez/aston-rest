CREATE TABLE users (
  user_id varchar(36),
  user_name varchar,
   CONSTRAINT user_id PRIMARY KEY (user_id)
);

CREATE TABLE records (
  record_id varchar(36),
  author_id integer,
  title varchar,
  text varchar,
  CONSTRAINT record PRIMARY KEY (record_id)
);

CREATE TABLE likes (
  user_id varchar(36),
  record_id varchar(36),
    CONSTRAINT user_like PRIMARY KEY (user_id),
    CONSTRAINT record_like PRIMARY KEY (record_id)
);

ALTER TABLE records ADD FOREIGN KEY (author_id) REFERENCES "users" (user_id);

ALTER TABLE likes ADD FOREIGN KEY (user_id) REFERENCES "users" (user_id);

ALTER TABLE likes ADD FOREIGN KEY (record_id) REFERENCES "records" (record_id);