-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: HotelFinder
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `cid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'br1','Chinese Kitchen',8.99,'Boston','b'),(2,'br2','Quincy Place',12.989999771118164,'Boston','b'),(3,'br3','Red Apple Buffet',10.989999771118164,'Boston','b'),(4,'nr1','Chinese Buffet',11.989999771118164,'New York','n'),(5,'nr2','Gyu Kaku Japanese BBQ ',16.989999771118164,'New York','n'),(6,'nr3','New Grand Buffet',13,'New York','n'),(7,'nr4','Bukhara Brazil Grill',14,'New York','n'),(8,'pr1','Delmonico Mexican Buffet',9.989999771118164,'Philadelphia','p'),(9,'pr2','Old Country Buffet',8.989999771118164,'Philadelphia','p'),(10,'mr1','Chef Chen Chinese Buffet',9,'Miami','m'),(11,'mr2','Inari Sushi Buffet',14,'Miami','m'),(12,'mr3','Ashoka Indian Cuisine',11,'Miami','m'),(13,'sr1','Mandarin Buffet & Grill',12.989999771118164,'Seattle','s'),(14,'sr2','Umma\'s Japanese Lunch Box',10.989999771118164,'Seattle','s'),(15,'sr3','Seafood Buffet',13.989999771118164,'Seattle','s'),(16,'dr1','Oyaki Sushi',11.989999771118164,'Detroit','d'),(17,'dr2','Texas de Brazil',10.989999771118164,'Detroit','d'),(18,'cr1','China Buffet',9.989999771118164,'Chicago','c'),(19,'cr2','Royal Sushi Buffet',13.989999771118164,'Chicago','c'),(20,'cr3','Mo\'s Asian Bistro',12,'Chicago','c'),(21,'cr4','Little Sheep Hot Pot Buffet',16,'Chicago','c'),(22,'hr1','Shabu House',16.989999771118164,'Houston','h'),(23,'hr2','Thai Spice Restaurant',10.989999771118164,'Houston','h'),(24,'fr1','Fiery Hot Pot Buffet',17.989999771118164,'San Francisco','f'),(25,'fr2','Beque BBQ Grill',10.989999771118164,'San Francisco','f'),(26,'fr3','Tao Sushi Buffet',16,'San Francisco','f'),(27,'lr1','ILCHA Korean BBQ ',15.989999771118164,'Los Angeles','l'),(28,'lr2','King Chinese Buffet',9,'Los Angeles','l'),(29,'lr3','Vegas Seafood Buffet',13,'Los Angeles','l'),(30,'lr4','Sushi Ippo Buffet',13,'Los Angeles','l');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-11  2:16:57
