CREATE TABLE user (
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

CREATE TABLE subject (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100)
);

CREATE TABLE quiz (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(100),
    is_active bit,
    subject_id int,

    FOREIGN KEY (subject_id) REFERENCES subject(id)
);

CREATE TABLE question (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    'order' int,
    quiz_id int,

    FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);

CREATE TABLE answer (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    body varchar(255),
    is_active bit,
    is_correct bit,
    'order' int,
    question_id int,

    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE user_answer (
    user_id int,
    answer_id int,

    PRIMARY KEY (user_id, answer_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (answer_id) REFERENCES answer(id)
);

CREATE TABLE course (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    score int,
    started_at date,
    finished_at date,
    quiz_id int,

    FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);

