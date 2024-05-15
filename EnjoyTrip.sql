CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `join_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `content_type`(
    `content_type_id` INT NOT NULL,
    `content_type` varchar(50) NOT NULL,
    PRIMARY KEY (`content_type_id`)
);

ALTER TABLE `attraction_info`
ADD CONSTRAINT `fk_content_type_id`
FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`) ON DELETE RESTRICT;

CREATE TABLE plan (
    plan_no INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    titles TEXT,
    addresses TEXT,
    latitudes TEXT,
    longitudes TEXT,
 foreign key(`user_id`) references `members` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `board` (
  `board_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `hits` int DEFAULT '0',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_no`),
  key `board_to_members_user_id_fk` (`user_id`),
  CONSTRAINT `board_to_members_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
);

CREATE TABLE hotplace (
    place_no INT AUTO_INCREMENT PRIMARY KEY,
    place_name VARCHAR(255),
    user_id VARCHAR(255),
    content_id INTEGER,
    content TEXT,
    date DATE,
    `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`content_id`) REFERENCES content_type(`content_type_id`),
    FOREIGN KEY (`user_id`) REFERENCES `members`(`user_id`)
);

