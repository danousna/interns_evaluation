CREATE TABLE utilisateur (
	id int PRIMARY KEY NOT NULL,
	email varchar(50),
	mot_de_passe varchar(50),
	nom varchar(50),
	societe varchar(50),
	telephone varchar(20),
	date_creation date,
	actif bit,
	est_admin bit
)

CREATE TABLE sujet (
	id int PRIMARY KEY NOT NULL,
	nom varchar(50)
)

CREATE TABLE questionnaire (
	id int PRIMARY KEY NOT NULL,
	nom varchar(50),
	actif bit,
	id_sujet int,
	FOREIGN KEY id_sujet REFERENCES sujet(id)
)

CREATE TABLE question (
	id int PRIMARY KEY NOT NULL,
	enonce varchar(255),
	actif bit,
	ordre bit,
	id_questionnaire int,
	FOREIGN KEY id_questionnaire REFERENCES questionnaire(id)
)

CREATE TABLE reponse (
	id int PRIMARY KEY NOT NULL,
	valeur varchar(255),
	actif bit,
	correct bit,
	ordre int,
	id_question int,
	FOREIGN KEY id_question REFERENCES question(id)
)

CREATE TABLE reponse_utilisateur (
	id_utilisateur int,
	id_reponse int,
	PRIMARY KEY (id_utilisateur, id_reponse),
	FOREIGN KEY id_utilisateur REFERENCES utilisateur(id),
	FOREIGN KEY id_reponse REFERENCES reponse(id)
)

CREATE TABLE parcours (
	id int,
	score int,
	date_debut date,
	date_fin date,
	id_questionnaire int,
	FOREIGN KEY id_questionnaire REFERENCES questionnaire(id)
)
