CREATE TABLE users (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(100),
    password varchar(255),
    name varchar(60),
    company varchar(100),
    phone varchar(30),
    created_at date,
    is_active bit,
    is_admin bit
);
INSERT INTO users VALUES (NULL, 'natan.danous@etu.utc.fr', 'A2S5NjPRf+JPbWdUwSJqVhFU8xV1YkeZvne9HILKbRPqwUHYpB9UPA==', 'Natan Danous', 'Psycle Research', '01 02 03 04 05', '2019-05-30', 1, 1), (NULL, 'john.smith@etu.utc.fr', 'A2S5NjPRf+JPbWdUwSJqVhFU8xV1YkeZvne9HILKbRPqwUHYpB9UPA==', 'John Smith', 'Psycle Research', '01 02 03 04 05', '2019-05-30', 1, 0), (NULL, 'olivia.random@etu.utc.fr', 'A2S5NjPRf+JPbWdUwSJqVhFU8xV1YkeZvne9HILKbRPqwUHYpB9UPA==', 'Olivia Random', 'Apple', '01 02 03 04 05', '2019-05-30', 1, 0);

CREATE TABLE subjects (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100)
);
INSERT INTO subjects VALUES (NULL, 'Programmation'), (NULL, 'Analyse de données'), (NULL, 'Web'), (NULL, 'Économie');

CREATE TABLE quizzes (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100),
    is_active bit,
    subject_id int,

    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);
INSERT INTO quizzes VALUES (NULL, 'Python', 1, 1), (NULL, 'K-Means', 1, 2);

CREATE TABLE questions (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    `order` int,
    quiz_id int,

    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);
INSERT INTO questions VALUES (NULL, 'Quelle est la dernière version de Python ?', 1, 1, 1), (NULL, 'Python est un language ...', 1, 2, 1), (NULL, 'Comment définit on une fonction ?', 1, 3, 1);

CREATE TABLE answers (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    is_correct bit,
    `order` int,
    question_id int,

    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);
INSERT INTO answers VALUES (NULL, 'Python 2', 1, 0, 1, 1), (NULL, 'Python 3', 1, 1, 2, 1), (NULL, 'Python 4', 1, 0, 3, 1), (NULL, 'interprété', 1, 1, 1, 2), (NULL, 'compilé', 1, 0, 2, 2), (NULL, 'def maFonction()', 1, 1, 1, 3), (NULL, 'func maFonction()', 1, 0, 2, 3), (NULL, 'function maFonction()', 1, 0, 3, 3);

CREATE TABLE records (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    quiz_id int,
    user_id int,
    score int,
    started_at datetime,
    finished_at datetime,

    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE users_answers (
    user_id int,
    record_id int,
    question_id int,
    answer_id int,

    PRIMARY KEY (user_id, record_id, question_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (record_id) REFERENCES records(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (answer_id) REFERENCES answers(id) ON DELETE CASCADE
);

