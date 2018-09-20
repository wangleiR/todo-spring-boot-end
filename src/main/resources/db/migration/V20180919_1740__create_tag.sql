CREATE TABLE tag(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR (255),
    user_id bigint
)engine=InnoDB DEFAULT CHARSET=gbk;

