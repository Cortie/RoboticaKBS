-- MariaDB dump 10.19  Distrib 10.4.19-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: domotica_database
-- ------------------------------------------------------
-- Server version	10.4.19-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `song_log`
--

DROP TABLE IF EXISTS `song_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `song_log` (
  `log_id` int(8) NOT NULL AUTO_INCREMENT,
  `account_id` int(8) DEFAULT NULL,
  `song_name` varchar(45) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `song_log_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_log`
--

LOCK TABLES `song_log` WRITE;
/*!40000 ALTER TABLE `song_log` DISABLE KEYS */;
INSERT INTO `song_log` VALUES (1,1,'Pacman','2021-05-27 13:55:39'),(2,1,'Game of Thrones','2021-05-27 13:55:42'),(3,1,'Imperial March','2021-05-27 13:55:44'),(4,1,'Pacman','2021-05-27 13:55:45'),(5,1,'Tetris 3','2021-05-27 13:55:46'),(6,1,'At Doom\'s Gate','2021-05-27 13:55:48'),(7,1,'Pacman','2021-05-27 14:08:45'),(8,1,'Game of Thrones','2021-05-27 14:08:47'),(9,1,'Tetris 3','2021-05-27 14:08:48'),(10,1,'Pacman','2021-05-27 14:11:15'),(11,1,'Tetris 3','2021-05-27 18:09:37'),(12,1,'Game of Thrones','2021-05-27 18:09:39'),(13,1,'Pacman','2021-05-27 18:09:41'),(14,1,'Imperial March','2021-05-27 18:09:43'),(15,1,'Game of Thrones','2021-05-27 18:09:45'),(16,1,'Pacman','2021-05-27 18:09:48'),(17,1,'At Dooms Gate','2021-05-27 18:37:22'),(18,1,'At Dooms Gate','2021-05-27 18:39:33'),(19,1,'At Dooms Gate','2021-05-27 18:39:49'),(20,1,'At Dooms Gate','2021-05-27 18:40:15'),(21,1,'At Dooms Gate','2021-05-27 18:40:46'),(22,1,'Pacman','2021-05-27 18:40:51'),(23,1,'Game of Thrones','2021-05-27 18:41:52'),(24,1,'Tetris 3','2021-05-27 18:41:53'),(25,1,'At Dooms Gate','2021-05-27 18:41:55'),(26,1,'Tetris 3','2021-05-27 18:41:56'),(27,1,'At Dooms Gate','2021-05-27 18:42:47'),(28,1,'At Dooms Gate','2021-05-27 18:42:48'),(29,1,'At Dooms Gate','2021-05-27 18:43:32'),(30,1,'Tetris 3','2021-05-27 18:43:33'),(31,1,'Game of Thrones','2021-05-27 18:43:34'),(32,1,'Pacman','2021-05-27 18:43:36'),(33,1,'Tetris 3','2021-05-27 19:07:57'),(34,1,'Game of Thrones','2021-05-27 19:18:46'),(35,1,'Tetris 3','2021-05-27 19:18:53'),(36,1,'Tetris 3','2021-05-27 19:18:59'),(37,1,'Game of Thrones','2021-05-27 19:20:03'),(38,1,'Tetris 3','2021-05-27 19:20:07'),(39,1,'Tetris 3','2021-05-27 19:20:12'),(40,1,'Tetris 3','2021-05-27 19:20:15'),(41,1,'Game of Thrones','2021-05-27 19:20:46'),(42,1,'Tetris 3','2021-05-27 19:20:49'),(43,1,'Tetris 3','2021-05-27 19:20:50'),(44,1,'Tetris 3','2021-05-27 19:20:51'),(45,1,'Tetris 3','2021-05-27 19:20:52'),(46,1,'Tetris 3','2021-05-27 19:20:53'),(47,1,'Game of Thrones','2021-05-27 19:22:43'),(48,1,'Tetris 3','2021-05-27 19:22:46'),(49,1,'Tetris 3','2021-05-27 19:22:48'),(50,1,'Tetris 3','2021-05-27 19:22:48'),(51,1,'Tetris 3','2021-05-27 19:22:49'),(52,1,'Game of Thrones','2021-05-27 19:23:25'),(53,1,'Tetris 3','2021-05-27 19:23:30'),(54,1,'Tetris 3','2021-05-27 19:23:34'),(55,1,'Tetris 3','2021-05-27 19:23:37'),(56,1,'Game of Thrones','2021-05-27 19:24:54'),(57,1,'Tetris 3','2021-05-27 19:25:01'),(58,1,'Game of Thrones','2021-05-27 19:25:33'),(59,1,'Tetris 3','2021-05-27 19:25:40'),(60,1,'Tetris 3','2021-05-27 19:25:47'),(61,1,'Game of Thrones','2021-05-27 19:26:33'),(62,1,'Tetris 3','2021-05-27 19:26:40'),(63,1,'Tetris 3','2021-05-27 19:26:47'),(64,1,'Game of Thrones','2021-05-27 19:27:47'),(65,1,'Tetris 3','2021-05-27 19:27:54'),(66,1,'Game of Thrones','2021-05-27 19:28:12'),(67,1,'Game of Thrones','2021-05-27 19:38:38'),(68,1,'Game of Thrones','2021-05-27 19:39:14'),(69,1,'Game of Thrones','2021-05-27 19:39:20'),(70,1,'Game of Thrones','2021-05-27 19:40:14'),(71,1,'Game of Thrones','2021-05-27 19:40:16'),(72,1,'Game of Thrones','2021-05-27 19:41:05'),(73,1,'Game of Thrones','2021-05-27 19:41:09'),(74,1,'Game of Thrones','2021-05-27 19:41:10'),(75,1,'Game of Thrones','2021-05-27 19:41:12'),(76,1,'Game of Thrones','2021-05-27 19:44:50'),(77,1,'Game of Thrones','2021-05-27 19:44:52'),(78,1,'Game of Thrones','2021-05-27 19:44:53'),(79,1,'Game of Thrones','2021-05-27 19:46:43'),(80,1,'Tetris 3','2021-05-27 19:46:52'),(81,1,'Game of Thrones','2021-05-27 19:47:21'),(82,1,'Game of Thrones','2021-05-27 19:48:51'),(83,1,'Game of Thrones','2021-05-27 19:50:26'),(84,1,'Game of Thrones','2021-05-27 19:50:57'),(85,1,'Game of Thrones','2021-05-27 19:51:24'),(86,1,'Game of Thrones','2021-05-27 19:52:30'),(87,1,'Game of Thrones','2021-05-27 19:53:04'),(88,1,'Tetris 3','2021-05-27 19:53:10'),(89,1,'Game of Thrones','2021-05-27 19:53:13'),(90,1,'Game of Thrones','2021-05-27 19:55:54'),(91,1,'Tetris 3','2021-05-27 19:55:56'),(92,1,'Game of Thrones','2021-05-27 19:55:57'),(93,1,'Tetris 3','2021-05-27 19:55:58'),(94,1,'Game of Thrones','2021-05-27 19:55:59'),(95,1,'Game of Thrones','2021-05-27 19:56:02'),(96,1,'Game of Thrones','2021-05-27 19:56:19'),(97,1,'Tetris 3','2021-05-27 19:56:21'),(98,1,'Tetris 3','2021-05-27 19:56:22'),(99,1,'Tetris 3','2021-05-27 19:56:23'),(100,1,'Tetris 3','2021-05-27 19:56:25'),(101,1,'Game of Thrones','2021-05-27 20:03:29'),(102,1,'Tetris 3','2021-05-27 20:03:32'),(103,1,'Tetris 3','2021-05-27 20:03:33'),(104,1,'Tetris 3','2021-05-27 20:03:35'),(105,1,'Tetris 3','2021-05-27 20:03:37'),(106,1,'Game of Thrones','2021-05-27 20:03:41'),(107,1,'Tetris 3','2021-05-27 20:04:34'),(108,1,'Game of Thrones','2021-05-27 20:09:14'),(109,1,'Tetris 3','2021-05-27 20:09:23'),(110,1,'Tetris 3','2021-05-27 20:09:24'),(111,1,'Tetris 3','2021-05-27 20:09:26'),(112,1,'Tetris 3','2021-05-27 20:09:30'),(113,1,'Game of Thrones','2021-05-27 20:13:25'),(114,1,'Pacman','2021-05-27 20:13:28'),(115,1,'Game of Thrones','2021-05-27 20:13:29'),(116,1,'Game of Thrones','2021-05-27 20:14:20'),(117,1,'Tetris 3','2021-05-27 20:14:22'),(118,1,'Pacman','2021-05-27 20:14:23'),(119,1,'At Dooms Gate','2021-05-27 20:14:27'),(120,1,'Game of Thrones','2021-05-27 20:15:21'),(121,1,'Tetris 3','2021-05-27 20:15:24'),(122,1,'Pacman','2021-05-27 20:15:25'),(123,1,'Game of Thrones','2021-05-27 20:15:53'),(124,1,'Tetris 3','2021-05-27 20:15:55'),(125,1,'Pacman','2021-05-27 20:15:56'),(126,1,'Game of Thrones','2021-05-27 20:15:57'),(127,1,'Tetris 3','2021-05-27 20:15:58'),(128,1,'Pacman','2021-05-27 20:15:59'),(129,1,'Game of Thrones','2021-05-27 20:16:00'),(130,1,'Tetris 3','2021-05-27 20:16:01'),(131,1,'Pacman','2021-05-27 20:16:02'),(132,1,'Game of Thrones','2021-05-27 20:16:03'),(133,1,'Tetris 3','2021-05-27 20:16:03'),(134,1,'Pacman','2021-05-27 20:16:04'),(135,1,'Game of Thrones','2021-05-27 20:16:05'),(136,1,'At Dooms Gate','2021-05-27 20:16:16'),(137,1,'At Dooms Gate','2021-05-27 20:17:06'),(138,1,'At Dooms Gate','2021-05-27 20:18:19'),(139,1,'Game of Thrones','2021-05-27 20:19:10'),(140,1,'Tetris 3','2021-05-27 20:19:12'),(141,1,'Pacman','2021-05-27 20:19:13'),(142,1,'Game of Thrones','2021-05-27 20:19:14'),(143,1,'Game of Thrones','2021-05-27 20:27:03'),(144,1,'Tetris 3','2021-05-27 20:27:04'),(145,1,'Pacman','2021-05-27 20:27:06'),(146,1,'Game of Thrones','2021-05-27 20:30:32'),(147,1,'Game of Thrones','2021-05-27 20:31:14'),(148,1,'Game of Thrones','2021-05-27 20:31:15'),(149,1,'Game of Thrones','2021-05-27 20:31:36'),(150,1,'Game of Thrones','2021-05-27 20:31:59'),(151,1,'Game of Thrones','2021-05-27 20:32:00'),(152,1,'Game of Thrones','2021-05-27 20:32:04'),(153,1,'At Dooms Gate','2021-05-27 20:38:13'),(154,1,'At Dooms Gate','2021-05-27 20:39:07'),(155,1,'At Dooms Gate','2021-05-27 20:39:13'),(156,1,'At Dooms Gate','2021-05-27 20:39:31'),(157,1,'At Dooms Gate','2021-05-27 20:39:32'),(158,1,'At Dooms Gate','2021-05-27 20:40:08'),(159,1,'At Dooms Gate','2021-05-27 20:40:09'),(160,1,'At Dooms Gate','2021-05-27 20:40:29'),(161,1,'At Dooms Gate','2021-05-27 20:40:30'),(162,1,'At Dooms Gate','2021-05-27 20:41:15'),(163,1,'At Dooms Gate','2021-05-27 20:41:16'),(164,1,'At Dooms Gate','2021-05-27 20:42:26'),(165,1,'At Dooms Gate','2021-05-27 20:42:27'),(166,1,'At Dooms Gate','2021-05-27 20:43:18'),(167,1,'At Dooms Gate','2021-05-27 20:43:19'),(168,1,'At Dooms Gate','2021-05-27 20:44:14'),(169,1,'At Dooms Gate','2021-05-27 20:44:15'),(170,1,'At Dooms Gate','2021-05-27 20:44:45'),(171,1,'At Dooms Gate','2021-05-27 20:44:46'),(172,1,'At Dooms Gate','2021-05-27 20:45:17'),(173,1,'At Dooms Gate','2021-05-27 20:45:18'),(174,1,'At Dooms Gate','2021-05-27 20:45:58'),(175,1,'At Dooms Gate','2021-05-27 20:45:58'),(176,1,'At Dooms Gate','2021-05-27 20:46:23'),(177,1,'At Dooms Gate','2021-05-27 20:46:25'),(178,1,'At Dooms Gate','2021-05-27 20:46:39'),(179,1,'Game of Thrones','2021-05-27 20:46:40'),(180,1,'At Dooms Gate','2021-05-27 20:47:43'),(181,1,'Game of Thrones','2021-05-27 20:47:45'),(182,1,'Tetris 3','2021-05-27 20:47:50'),(183,1,'Game of Thrones','2021-05-27 20:47:51'),(184,1,'Tetris 3','2021-05-27 20:47:52'),(185,1,'Pacman','2021-05-27 20:47:53'),(186,1,'Tetris 3','2021-05-27 20:47:54'),(187,1,'Game of Thrones','2021-05-27 20:47:54'),(188,1,'At Dooms Gate','2021-05-27 20:51:10'),(189,1,'Game of Thrones','2021-05-27 20:51:19'),(190,1,'Tetris 3','2021-05-27 20:51:21'),(191,1,'Pacman','2021-05-27 20:51:21'),(192,1,'Tetris 3','2021-05-27 20:51:22'),(193,1,'Game of Thrones','2021-05-27 20:51:22');
/*!40000 ALTER TABLE `song_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-27 21:00:28
