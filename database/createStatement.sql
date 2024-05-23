CREATE DATABASE `ssafytrip`;

CREATE TABLE `attraction_description` (
  `content_id` int NOT NULL,
  `homepage` varchar(100) DEFAULT NULL,
  `overview` varchar(10000) DEFAULT NULL,
  `telname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `attraction_detail` (
  `content_id` int NOT NULL,
  `cat1` varchar(3) DEFAULT NULL,
  `cat2` varchar(5) DEFAULT NULL,
  `cat3` varchar(9) DEFAULT NULL,
  `created_time` varchar(14) DEFAULT NULL,
  `modified_time` varchar(14) DEFAULT NULL,
  `booktour` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `attraction_info` (
  `content_id` int NOT NULL,
  `content_type_id` int DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `addr2` varchar(50) DEFAULT NULL,
  `zipcode` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `first_image` varchar(200) DEFAULT NULL,
  `first_image2` varchar(200) DEFAULT NULL,
  `readcount` int DEFAULT NULL,
  `sido_code` int DEFAULT NULL,
  `gugun_code` int DEFAULT NULL,
  `latitude` decimal(20,17) DEFAULT NULL,
  `longitude` decimal(20,17) DEFAULT NULL,
  `mlevel` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  KEY `attraction_to_content_type_id_fk_idx` (`content_type_id`),
  KEY `attraction_to_sido_code_fk_idx` (`sido_code`),
  KEY `attraction_to_gugun_code_fk_idx` (`gugun_code`),
  CONSTRAINT `attraction_to_content_type_id_fk` FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk` FOREIGN KEY (`gugun_code`) REFERENCES `gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk` FOREIGN KEY (`sido_code`) REFERENCES `sido` (`sido_code`),
  CONSTRAINT `fk_content_type_id` FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `board` (
  `board_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `hits` int DEFAULT '0',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_no`),
  KEY `board_to_members_user_id_fk` (`user_id`),
  CONSTRAINT `board_to_members_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `comment_no` int NOT NULL AUTO_INCREMENT,
  `board_no` int DEFAULT NULL,
  `user_id` varchar(16) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_no`),
  KEY `board_no` (`board_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`) ON DELETE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `content_type` (
  `content_type_id` int NOT NULL,
  `content_type` varchar(50) NOT NULL,
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gugun` (
  `gugun_code` int NOT NULL,
  `gugun_name` varchar(30) DEFAULT NULL,
  `sido_code` int NOT NULL,
  PRIMARY KEY (`gugun_code`,`sido_code`),
  KEY `gugun_to_sido_sido_code_fk_idx` (`sido_code`),
  CONSTRAINT `gugun_to_sido_sido_code_fk` FOREIGN KEY (`sido_code`) REFERENCES `sido` (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `hotplace_description` (
  `hotplace_id` int DEFAULT NULL,
  `overview` varchar(10000) DEFAULT NULL,
  KEY `hotplace_description_ibfk_1` (`hotplace_id`),
  CONSTRAINT `hotplace_description_ibfk_1` FOREIGN KEY (`hotplace_id`) REFERENCES `hotplace_info` (`hotplace_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hotplace_info` (
  `hotplace_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `content_type_id` int DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `addr2` varchar(100) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `image_src` varchar(200) DEFAULT NULL,
  `sido_code` int DEFAULT NULL,
  `gugun_code` int DEFAULT NULL,
  `latitude` decimal(20,17) DEFAULT NULL,
  `longitude` decimal(20,17) DEFAULT NULL,
  PRIMARY KEY (`hotplace_id`),
  KEY `user_id` (`user_id`),
  KEY `content_type_id` (`content_type_id`),
  CONSTRAINT `hotplace_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`),
  CONSTRAINT `hotplace_info_ibfk_2` FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hotplace_like` (
  `user_id` varchar(16) DEFAULT NULL,
  `hotplace_id` int DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `hotplace_id` (`hotplace_id`),
  CONSTRAINT `hotplace_like_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `hotplace_like_ibfk_2` FOREIGN KEY (`hotplace_id`) REFERENCES `hotplace_info` (`hotplace_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `join_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plan` (
  `plan_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `titles` text,
  `addresses` text,
  `latitudes` text,
  `longitudes` text,
  PRIMARY KEY (`plan_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plan_seq` (
  `plan_no` int NOT NULL,
  `seq_no` int NOT NULL,
  `content_id` int DEFAULT NULL,
  PRIMARY KEY (`plan_no`,`seq_no`),
  KEY `content_id` (`content_id`),
  CONSTRAINT `plan_seq_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plan_user` (
  `plan_no` int DEFAULT NULL,
  `user_id` varchar(16) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  KEY `plan_no` (`plan_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `plan_user_ibfk_1` FOREIGN KEY (`plan_no`) REFERENCES `plan_seq` (`plan_no`) ON DELETE CASCADE,
  CONSTRAINT `plan_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sido` (
  `sido_code` int NOT NULL,
  `sido_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

