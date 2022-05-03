CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR UNIQUE,
  phone VARCHAR UNIQUE
);

CREATE TABLE if not exists sessions (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE if not exists ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT session_unique UNIQUE (session_id),
    CONSTRAINT row_unique UNIQUE (row),
    CONSTRAINT cell_unique UNIQUE (cell)
);