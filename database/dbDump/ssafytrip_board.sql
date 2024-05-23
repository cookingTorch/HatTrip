-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ssafytrip
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (4,'ssafy','aa','aa',0,'2024-04-05 03:07:48'),(5,'ssafy','aabbcc','aabbcc',3,'2024-04-05 03:07:58'),(6,'ssafy','dd','dd',0,'2024-04-05 03:08:27'),(7,'ssafy','dd','dd',0,'2024-04-05 04:31:41'),(9,'ssafy','dddd','dddd',1,'2024-04-05 04:35:06'),(10,'ssafy','ddddd','dd',0,'2024-04-05 04:35:10'),(12,'ssafy','abcabc','abcabc',0,'2024-04-05 04:58:55'),(13,'ssafy','abcdabc','abc',2,'2024-04-05 04:59:26'),(15,'ssafy','안녕adsf안녕','안녕adsf안녕',3,'2024-04-05 05:13:17'),(16,'cookingTorch','아하하','ㅇㅋ',2,'2024-04-26 00:37:49'),(18,'cookingTorch','테스트','Test2',7,'2024-05-13 05:46:12'),(19,'cookingTorch','테스트2','테스트 입니다.',33,'2024-05-13 05:47:37'),(20,'cookingTorch','안녕하세요','제곧내',13,'2024-05-21 06:18:04'),(21,'test','키움히어로즈는왜야구를못할까','쟤네가 해도 지고 내가 해도 지면 내가 들어가서 돈받으면 안되나? ',3,'2024-05-23 06:09:41'),(22,'testtest','수괐슴다','수고하셨습니다?',4,'2024-05-23 06:19:29');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 16:42:39
