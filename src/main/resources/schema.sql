DROP table if exists user_info;

CREATE table user_info(
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(30) NOT NULL,
 last_name VARCHAR(30) DEFAULT NULL,
 email_id VARCHAR(250) DEFAULT NULL,
 age INT NOT NULL
);