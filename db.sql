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

CREATE TABLE subjects (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100)
);

CREATE TABLE quizzes (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100),
    is_active bit,
    subject_id int,

    FOREIGN KEY (subject_id) REFERENCES subjects(id)
);

CREATE TABLE questions (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    `order` int,
    quiz_id int,

    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

CREATE TABLE answers (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    is_correct bit,
    `order` int,
    question_id int,

    FOREIGN KEY (question_id) REFERENCES questions(id)
);

CREATE TABLE users_answers (
    user_id int,
    answer_id int,

    PRIMARY KEY (user_id, answer_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (answer_id) REFERENCES answers(id)
);

CREATE TABLE courses (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    score int,
    started_at date,
    finished_at date,
    quiz_id int,

    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

