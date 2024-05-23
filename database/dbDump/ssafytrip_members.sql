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
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `join_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('aaa','aaa','aaa','asdf@naver.com','2024-05-22 06:02:19',NULL),('aaaa','aaa','aaa','asdf@naver.com','2024-05-22 06:04:17',NULL),('aaaaa','aaa','aaa','asdf@naver.com','2024-05-22 06:06:40',NULL),('abab','abab','abab','asdf@naver.com','2024-05-22 06:00:12',NULL),('ababa','abab','abab','asdf@naver.com','2024-05-22 06:01:06',NULL),('cookingTorch','토치','Aa16751675!','jhw7181@naver.com','2024-04-26 00:31:18','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNzE2MzM0NTA3LCJleHAiOjE3MTg5MjY1MDcsInVzZXJJZCI6ImNvb2tpbmdUb3JjaCJ9.voEIOpxw8UfZ1dL0ATYNHhbsVZ4eoRwe4gZbuTKfTgw'),('gang','배성수짱','1234','nkh9585@naver.com','2024-03-24 23:29:06',NULL),('qwqw','qwqw','qwqw','asdf@naver.com','2024-05-22 06:16:37',NULL),('ssafy','킹성수대성수빛성수','1234','ssafy@ssafy.com','2024-03-22 06:09:38',NULL),('sssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:09:12',NULL),('ssssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:09:32',NULL),('sssssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:11:05',NULL),('ssssssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:11:14',NULL),('sssssssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:11:21',NULL),('ssssssssss','ssss','sss','ssafy@ssafy.com','2024-05-22 06:11:39',NULL),('test','김테스트','test','test@test.com','2024-05-22 00:42:47',NULL),('testt','ttest','test','asdf@naver.com','2024-05-22 05:57:01',NULL),('testtest','김김김테테스스트트','test','test@test.test','2024-05-23 06:18:40','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNzE2NDQ1MTI3LCJleHAiOjE3MTkwMzcxMjcsInVzZXJJZCI6InRlc3R0ZXN0In0.ElApcqjKRRsmLUqncbvXCE7j6xylnRwjIZRGYpa__tY'),('tete','tete','tete','asdf@naver.com','2024-05-23 00:25:14',NULL),('ttest','ttest','test','asdf@naver.com','2024-05-22 05:56:27',NULL),('tttest','ttest','test','asdf@naver.com','2024-05-22 05:56:37',NULL),('ttttt','ttttt','ttttt','asdf@naver.com','2024-05-23 00:16:48',NULL);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
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
