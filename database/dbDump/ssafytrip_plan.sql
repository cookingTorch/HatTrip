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
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (1,'cookingTorch','도산공원,봉은사(서울),서울 선릉(성종과 정현왕후)과 정릉(중종) [유네스코 세계문화유산],압구정 로데오거리,청담공원,다도화랑,갤러리미,청화랑','서울특별시 강남구 도산대로45길 20,서울특별시 강남구 봉은사로 531,서울특별시 강남구 선릉로100길 1,서울특별시 강남구 압구정동일대,서울특별시 강남구 영동대로131길 26(청담동),서울특별시 강남구 논현로159길 24(신사동),서울특별시 강남구 압구정로 461 네이처 포엠312호,서울특별시 강남구 삼성로147길 4(청담동)','37.52146325,37.51587822,37.50742068,37.52687661,37.52115807,37.52298794,37.52531027,37.52273002','127.0338117,127.0577486,127.0471803,127.0388972,127.0526683,127.0260901,127.0472465,127.0476549'),(2,'cookingTorch','양천문화원,서울특별시교육청 양천도서관,목동아이스링크(실내),목동청소년수련관','서울특별시 양천구 목동서로 367(신정동) 양천문화회관 별관 5층,서울특별시 양천구 목동서로 113(목동),서울특별시 양천구 안양천로 939(목동),서울특별시 양천구 목동서로 143','37.51645801,37.53354792,37.52924293,37.53071768','126.8633163,126.8762257,126.8802699,126.8754786'),(4,'ssafy','제19회 추억의 광주충장 월드페스티벌,광주실내빙상장,광주월드컵경기장','광주광역시 동구 서남로 1,광주광역시 서구 금화로 278,광주광역시 서구 금화로 240(풍암동)','35.14549734,35.13660039,35.13376174','126.9231294,126.8791891,126.8749062');
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
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
