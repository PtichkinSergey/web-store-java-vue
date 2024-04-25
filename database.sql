DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS good_order;
DROP TABLE IF EXISTS good_category;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS status;

CREATE TABLE users (
	user_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	first_name varchar(255),
	second_name varchar(255),
	email varchar(255),
	is_admin boolean
);

CREATE TABLE goods (
	good_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	name VARCHAR(255),
	cost int NOT NULL,
	count int NOT NULL
);

CREATE TABLE categories (
	category_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	name VARCHAR(255),
	parent_id INT
);

CREATE TABLE comments (
	comment_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	user_id INT,
	good_id INT,
	text VARCHAR(255),
	rating int NOT NULL,
	image_path VARCHAR(255),
	FOREIGN KEY(user_id) REFERENCES users(user_id),
	FOREIGN KEY(good_id) REFERENCES goods(good_id)
);

CREATE TABLE status (
	status_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	name VARCHAR(255)
);

CREATE TABLE orders (
	order_id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
	user_id INT,
	status_id INT,
	FOREIGN KEY(status_id) REFERENCES status(status_id)
);

INSERT INTO users(first_name, second_name, email, is_admin)
VALUES('Сергей', 'Птичкин', 'sergey.ptichkin@gmail.com', true);

INSERT INTO goods(name, cost, count) VALUES
('Айфон', 100000, 15),
('SUMSUNG A32', 54000, 7);

INSERT INTO categories(name, parent_id) VALUES
('Смартфоны', NULL),
('Компьютеры', NULL);

CREATE TABLE good_category (
	good_id INT NOT NULL,
	category_id INT NOT NULL,
	PRIMARY KEY (good_id, category_id),
	CONSTRAINT good_category_ibfk_1 FOREIGN KEY(good_id) REFERENCES goods(good_id),
	CONSTRAINT good_category_ibfk_2 FOREIGN KEY(category_id) REFERENCES categories(category_id)
);

CREATE TABLE good_order (
	good_id INT NOT NULL,
	order_id INT NOT NULL,
	PRIMARY KEY (good_id, order_id),
	FOREIGN KEY(good_id) REFERENCES goods(good_id),
	FOREIGN KEY(order_id) REFERENCES orders(order_id)
);

INSERT INTO comments(user_id, good_id, text, rating)
VALUES(1, 1, 'Cool', 5);

INSERT INTO status(name)
VALUES('Оплачен');

-- INSERT INTO good_category(good_id, category_id) VALUES
-- (1, 1),
-- (1, 2);