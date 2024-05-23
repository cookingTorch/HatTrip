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
-- Table structure for table `hotplace_info`
--

DROP TABLE IF EXISTS `hotplace_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotplace_info`
--

LOCK TABLES `hotplace_info` WRITE;
/*!40000 ALTER TABLE `hotplace_info` DISABLE KEYS */;
INSERT INTO `hotplace_info` VALUES (23,'cookingTorch',14,'카카오 스페이스','제주특별자치도 제주시 첨단로 242',NULL,'01012345678','/hotplace/23/',39,4,33.45065560751890000,126.57063992361040000),(24,'cookingTorch',14,'멀티캠퍼스','서울특별시 강남구 테헤란로 212',NULL,'01012345678','/hotplace/24/',1,1,37.50128069088717000,127.03959335185388000),(25,'cookingTorch',38,'역삼역','서울특별시 강남구 테헤란로 지하 156',NULL,'01012345678','/hotplace/25/',1,1,37.50070505908047000,127.03644912705018000),(30,'cookingTorch',39,'이츠야','서울특별시 마포구 양화로6길 99-9',NULL,'02-336-9244','/hotplace/30/',1,13,37.54876102215346400,126.91993417907511000),(31,'cookingTorch',39,'비야게레로','서울특별시 강남구 봉은사로78길 12',NULL,'02-538-8915','/hotplace/31/',1,1,37.51196144633562000,127.05279865668135000),(33,'cookingTorch',14,'PC&COOK','서울특별시 강남구 언주로93길 30',NULL,'02-557-7674','/hotplace/33/',1,1,37.50340931081547000,127.03961144011065000),(34,'cookingTorch',39,'한솥','서울특별시 강남구 언주로 427',NULL,'0507-1496-1150','/hotplace/34/',1,1,37.50152281253650000,127.04288728159463000),(35,'testtest',25,'언주역','서울특별시 강남구 봉은사로 지하 201',NULL,'01001000100','/hotplace/35/',1,1,37.50735072150714400,127.03394152235704000);
/*!40000 ALTER TABLE `hotplace_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 16:42:40
