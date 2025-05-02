-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: bank_system
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `account_holder_name` varchar(100) NOT NULL,
  `encrypted_password` blob NOT NULL,
  `salt` blob NOT NULL,
  `balance` decimal(15,2) DEFAULT '0.00',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'shaun',_binary 'password',_binary 'password',42.00),(2,'shaun',_binary 'password',_binary 'password',42.00),(3,'shaun',_binary 'password',_binary 'password',42.00),(4,'shaun',_binary 'password',_binary 'password',42.00),(5,'shaunruss',_binary '+\Êx6ÓªänqæSSBÇ°n).',_binary '{\ :ÏÜö:ù',0.00),(6,'shaun',_binary 'A˜D\Èâwê(Éçœô\ F\÷.AX\—',_binary 'É∫rı5∞\Ì',0.00),(7,'jack',_binary 'û°öÎó¶\ÊÙû\∆güP¨(ˆ\ÿ',_binary 'ë\»Y\‡n%',203.00),(8,'shaunr',_binary '˙áy†˙sM∆ØKï4ó†\„\◊\ﬁh',_binary 'Û2ª\ÁHfcø',100000.00),(9,'3333',_binary 'ILß\Ãn5\Í7ŒÇ7\√¿œ≥„ô∂\n|',_binary '∂\œs∞E§º\Ê',0.00),(10,'name1',_binary '9Ñ\”hÄ´∞˜ù@πµ\√\ G\¬\›$',_binary 'n¢êà©\Õ“™',0.00),(11,'james',_binary '&*)Dåâ°Un~†2Ú∑îo\’hV',_binary 'åQ#ô\ﬁ8\Ê',0.00),(12,'mark',_binary 'B\“\ÌdEÄn\€–©àVê\Ô|\Œ\œ!I',_binary '\n=™≈µ∂ôo',477.00);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-02 16:12:39
