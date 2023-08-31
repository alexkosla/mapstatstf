-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `maps`
--

DROP TABLE IF EXISTS `maps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maps` (
  `MapID` varchar(36) NOT NULL COMMENT 'ID of Map Object',
  `MapNameShort` varchar(45) NOT NULL COMMENT 'Colloquial name of map',
  `ImgLocation` varchar(100) DEFAULT NULL COMMENT 'location of map image in relation to base directory',
  `MapNameFull` varchar(45) NOT NULL COMMENT 'full name of the map, ie "pl_badwater_final1"',
  PRIMARY KEY (`MapID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps`
--

LOCK TABLES `maps` WRITE;
/*!40000 ALTER TABLE `maps` DISABLE KEYS */;
INSERT INTO `maps` VALUES ('0','Steel','src/main/img/cp_steel_f12-full.png','cp_steel_f12'),('1','Ashville','src/main/img/koth_ashville_final-full.png','koth_ashville_final'),('2','Product','src/main/img/koth_product_final.png','koth_product_rcx'),('3','Proot','src/main/img/koth_proot_b5a.png','koth_proot_b5a'),('4','Swiftwater','src/main/img/pl_swiftwater_final1-full.jpg','pl_swiftwater_final1'),('5','Upward','src/main/img/pl_upward_f10.png','pl_upward_f10'),('6','Vigil','src/main/img/pl_vigil_rc9.png','pl_vigil_rc9');
/*!40000 ALTER TABLE `maps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masterstats`
--

DROP TABLE IF EXISTS `masterstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `masterstats` (
  `StatId` varchar(45) NOT NULL,
  `LogID` bigint NOT NULL COMMENT 'Unique ID used to find match''s ID in logs.tf database',
  `Steam64ID` bigint NOT NULL COMMENT 'Foreign key to user table',
  `Class` varchar(45) NOT NULL COMMENT 'Class the user who created the entry was playing in the match',
  `MapID` varchar(36) NOT NULL COMMENT 'Foreign key to the map the match was played on',
  `Kills` int NOT NULL COMMENT '# of kills user got in the match',
  `Assists` int NOT NULL COMMENT '# of assists the user got in the match',
  `Deaths` int NOT NULL COMMENT '# of deaths the user got in the match',
  `Damage` int NOT NULL COMMENT 'Amount of damage user did in the match',
  `Damage Taken` int NOT NULL COMMENT 'Amount of damage user received in the match',
  `SecondsPlayed` int NOT NULL COMMENT '# of seconds the user played in the match',
  PRIMARY KEY (`StatId`),
  KEY `fk_MasterStats_Users_idx` (`Steam64ID`),
  KEY `fk_MasterStats_Maps1_idx` (`MapID`),
  CONSTRAINT `fk_MasterStats_Maps1` FOREIGN KEY (`MapID`) REFERENCES `maps` (`MapID`),
  CONSTRAINT `fk_MasterStats_Users` FOREIGN KEY (`Steam64ID`) REFERENCES `users` (`Steam64ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masterstats`
--

LOCK TABLES `masterstats` WRITE;
/*!40000 ALTER TABLE `masterstats` DISABLE KEYS */;
INSERT INTO `masterstats` VALUES ('83b216f3-d870-11ed-a657-d8bbc16e7226',2719534,76561198034955422,'Sniper','2',19,1,12,7256,3308,854),('8530315e-d870-11ed-a657-d8bbc16e7226',2719534,76561198068104187,'Demo','2',13,7,13,6589,7421,854),('863b18e4-d870-11ed-a657-d8bbc16e7226',2719534,76561198028658527,'Scout','2',9,3,12,3387,3935,854),('873209fa-d870-11ed-a657-d8bbc16e7226',2759736,76561198123721061,'Spy','6',3,3,9,1238,1627,775),('885b8eda-d870-11ed-a657-d8bbc16e7226',2759736,76561198059227222,'Heavy','6',6,2,7,2097,4394,775),('8b889080-d870-11ed-a657-d8bbc16e7226',2759736,76561198034955422,'Sniper','6',9,2,5,2632,2741,775),('8ca4c007-d870-11ed-a657-d8bbc16e7226',2759736,76561198068104187,'Demo','6',19,1,6,7540,4990,775);
/*!40000 ALTER TABLE `masterstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Steam64ID` bigint NOT NULL COMMENT '64-bit format of steam ID, used to identify users',
  `Steam3ID` varchar(20) NOT NULL COMMENT 'Alternate format of Steam64ID useful to save to avoid needing to constantly calculate it',
  `Username` varchar(20) NOT NULL COMMENT 'User''s personally entered username',
  `PreferredClass` varchar(20) DEFAULT NULL COMMENT 'User''s favorite class',
  `IsAdmin` tinyint NOT NULL COMMENT 'True if the user is an Admin\n',
  PRIMARY KEY (`Steam64ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'dsafdsa','RomKosla','Heavy',1),(11,'dfasdfds','a','Demo',0),(66,'asfdsa','RomKosla','Heavy',1),(555,'asdfdsafads','jsdafiopads','Heavy',1),(8888,'Bob ID','Bob','Scout',0),(12412,'dsfasd','Tua','Heavy',1),(123545,'sjdfioaspd','Tua','Medic',1),(76561198028658527,'[U:1:68392799]','nucket','Scout',0),(76561198034955422,'[U:1:74689694]','tua','Sniper',1),(76561198059227222,'[U:1:98961494]','nurkz','Heavy',0),(76561198068104187,'[U:1:107838459]','Joey Lemons','Demo',0),(76561198123721061,'[U:1:163455333]','stardust','Spy',0);
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

-- Dump completed on 2023-08-31  1:04:35
