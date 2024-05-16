use ssafytrip;

CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `join_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
);

alter table members
add column token varchar(1000)
null default null after join_at;

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

CREATE TABLE IF NOT EXISTS comment (
    `comment_no` INT AUTO_INCREMENT PRIMARY KEY,
    `board_no` INT,
    `user_id` varchar(16),
    `content` varchar(1000),
    `create_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (`board_no`) REFERENCES board(`board_no`) on delete cascade,
    FOREIGN KEY (`user_id`) REFERENCES members(`user_id`)
);

CREATE TABLE IF NOT EXISTS plan_seq (
	`plan_no` INT,
    `seq_no` INT,
    `content_id` INT,
    PRIMARY KEY (`plan_no` , `seq_no`),
    FOREIGN KEY (`content_id`) REFERENCES attraction_info(`content_id`) on delete cascade
);

CREATE TABLE IF NOT EXISTS plan_user (
	`plan_no` INT,
    `user_id` varchar(16),
    foreign key (`plan_no`) references plan_seq(`plan_no`) on delete cascade,
    foreign key (`user_id`) references members(`user_id`) on delete cascade
);

CREATE TABLE IF NOT EXISTS hotplace_info (
    hotplace_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(16),
    content_type_id INT,
    title VARCHAR(100),
    addr1 VARCHAR(100),
    addr2 VARCHAR(100),
    tel VARCHAR(50),
    image_src VARCHAR(200),
    sido_code int,
    gugun_code int,
    latitude decimal(20, 17),
    longitude decimal(20, 17),
    FOREIGN KEY (user_id) REFERENCES members(user_id),
    FOREIGN KEY (content_type_id) REFERENCES content_type(content_type_id)
);

CREATE TABLE IF NOT EXISTS hotplace_description (
    hotplace_id INT,
    overview VARCHAR(10000),
    FOREIGN KEY (hotplace_id) REFERENCES hotplace_info(hotplace_id)
);