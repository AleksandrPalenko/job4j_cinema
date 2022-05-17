CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE

);

CREATE TABLE if not exists sessions (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE if not exists ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    line INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT unique_ticket UNIQUE (session_id, line, cell)
);

INSERT INTO sessions(name) VALUES ('Брат');

INSERT INTO sessions(name) VALUES ('Брат-2');

INSERT INTO sessions(name) VALUES ('Сестры');

insert into users(username, email, phone) values('alex', 'alex@mail.ru', 89993652145);
insert into users(username, email, phone) values('ivan', 'ivan@mail.ru', 89997894120);
insert into users(username, email, phone) values('petr', 'petr@mail.ru', 89998529687);


insert into ticket(session_id, line, cell, user_id) values(1, 2, 4, 1);
insert into ticket(session_id, line, cell, user_id) values(2, 4, 6, 2);
insert into ticket(session_id, line, cell, user_id) values(3, 7, 9, 3);