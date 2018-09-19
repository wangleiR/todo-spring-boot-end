CREATE TABLE todo(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (255),
status VARCHAR (255),
due_date DATE,
tags VARCHAR (255)
)engine=InnoDB DEFAULT CHARSET=gbk;

INSERT INTO todo (id, name, status, due_date, tags) VALUES (1, "meet L", "To DO", "2018-9-20", "meeting");
INSERT INTO todo (id, name, status, due_date, tags) VALUES (2, "learn JS", "In Progress" , "2018-9-22", "learning");
INSERT INTO todo (id, name, status, due_date, tags) VALUES (3, "Perpare Slides", "Finished","2018-9-24", "perparation");

