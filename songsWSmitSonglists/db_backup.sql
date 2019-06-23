-- MySQL dump 10.13  Distrib 8.0.16, for osx10.14 (x86_64)
--
-- Host: db.f4.htw-berlin.de    Database: _s0557318__SongDB
-- ------------------------------------------------------
-- Server version	5.6.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `list_song`
--

DROP TABLE IF EXISTS `list_song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `list_song` (
  `list_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  KEY `FK8u1h4s50ey3an68pm8irkt9ex` (`song_id`),
  KEY `FKthdn34213jtjfsr81akhu715w` (`list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_song`
--

LOCK TABLES `list_song` WRITE;
/*!40000 ALTER TABLE `list_song` DISABLE KEYS */;
INSERT INTO `list_song` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `list_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songlists`
--

DROP TABLE IF EXISTS `songlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `songlists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `private` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_userId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1hsb6h0xphq31367fob1mwmxv` (`owner_userId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songlists`
--

LOCK TABLES `songlists` WRITE;
/*!40000 ALTER TABLE `songlists` DISABLE KEYS */;
INSERT INTO `songlists` VALUES (1,_binary '','ElenasPrivate','mmuster');
/*!40000 ALTER TABLE `songlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `songs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album` varchar(255) DEFAULT NULL,
  `artist` varchar(255) DEFAULT NULL,
  `released` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,'Trolls','Justin Timberlake',2016,'Can\'t Stop the Feeling'),(2,'Thank You','Meghan Trainor, Kelli Trainor',2016,'Mom'),(3,NULL,'Iggy Azalea',2016,'Team'),(4,'Ghostbusters','Fall Out Boy, Missy Elliott',2016,'Ghostbusters (I\'m not a fraid)'),(5,'Bloom','Camila Cabello, Machine Gun Kelly',2017,'Bad Things'),(6,'At Night, Alone.','Mike Posner',2016,'I Took a Pill in Ibiza'),(7,'Top Hits 2017','Gnash',2017,'i hate u, i love u'),(8,'Thank You','Meghan Trainor',2016,'No'),(9,'Glory','Britney Spears',2016,'Private Show'),(10,'Lukas Graham (Blue Album)','Lukas Graham',2015,'7 Years');
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `userId` varchar(255) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('mmuster','Maxime','Muster','passwd123'),('eschuler','Elena','Schuler','passwd123');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-23 16:32:15
