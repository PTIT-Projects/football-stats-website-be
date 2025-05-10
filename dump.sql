USE football_stat_web;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: app-phamhoangcena-2ac7.l.aivencloud.com    Database: defaultdb
-- ------------------------------------------------------
-- Server version	8.0.30

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '12c24bad-2a88-11f0-927b-862ccfb025e6:1-252,
93043cce-dca2-11ef-b35b-9ec77dd215eb:1-48';

--
-- Table structure for table `club_season_table`
--

DROP TABLE IF EXISTS `club_season_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_season_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `diff` int DEFAULT NULL,
  `goal_conceded` int DEFAULT NULL,
  `goal_scores` int DEFAULT NULL,
  `num_draws` int DEFAULT NULL,
  `num_losses` int DEFAULT NULL,
  `num_wins` int DEFAULT NULL,
  `points` int DEFAULT NULL,
  `ranked` int DEFAULT NULL,
  `club_id` bigint DEFAULT NULL,
  `season_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm6wpknpvkxhcnx07eqtev1prh` (`season_id`,`club_id`),
  KEY `FKmisqf4c7rpshy9gjfe3ilwtjn` (`club_id`),
  CONSTRAINT `FKm7x8jwlymhgjga3fmqgtper9j` FOREIGN KEY (`season_id`) REFERENCES `league_season` (`id`),
  CONSTRAINT `FKmisqf4c7rpshy9gjfe3ilwtjn` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_season_table`
--

LOCK TABLES `club_season_table` WRITE;
/*!40000 ALTER TABLE `club_season_table` DISABLE KEYS */;
INSERT INTO `club_season_table` VALUES (1,57,27,84,7,1,30,97,1,2,1),(2,19,46,65,9,6,23,78,2,1,1),(3,9,37,46,12,9,17,63,4,5,1),(4,0,31,31,15,3,20,75,3,6,1),(5,17,23,40,15,4,20,75,2,1,2),(6,46,12,58,7,3,29,94,1,2,2),(7,0,0,0,0,0,0,0,1,3,4),(8,-1,3,2,0,1,0,0,4,1,3),(9,1,2,3,0,0,1,3,1,2,3),(10,0,1,1,1,0,0,1,3,5,3),(11,0,1,1,1,0,0,1,2,6,3),(14,0,0,0,0,0,0,0,1,4,5);
/*!40000 ALTER TABLE `club_season_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clubs`
--

DROP TABLE IF EXISTS `clubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `stadium_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubs`
--

LOCK TABLES `clubs` WRITE;
/*!40000 ALTER TABLE `clubs` DISABLE KEYS */;
INSERT INTO `clubs` VALUES (1,'England','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746543964/club/1746543962014-mc.png','Manchester City','Eithad'),(2,'EngLand','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726480/club/1746726476897-MU.png','Manchester United','OldTrafford'),(3,'Spain','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726498/club/1746726496683-barca.png','Barcelona','Camp Nou'),(4,'France','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726530/club/1746726528482-psg.png','Paris Saint German','Parc De Princes'),(5,'England','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746762925/club/1746762924018-images.png','Chelsea','Stamford Bridge'),(6,'England','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746762946/club/1746762944724-arsenal.png','Arsenal','Emirates'),(7,'Germany','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746801844/club/1746801842777-dortmund.png','Dortmund','Signal Iduna Park');
/*!40000 ALTER TABLE `clubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coach_citizenship`
--

DROP TABLE IF EXISTS `coach_citizenship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coach_citizenship` (
  `head_coach_id` bigint NOT NULL,
  `citizenship` varchar(255) DEFAULT NULL,
  KEY `FK99fhoyh22vxs7evjftmdudfty` (`head_coach_id`),
  CONSTRAINT `FK99fhoyh22vxs7evjftmdudfty` FOREIGN KEY (`head_coach_id`) REFERENCES `head_coaches` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coach_citizenship`
--

LOCK TABLES `coach_citizenship` WRITE;
/*!40000 ALTER TABLE `coach_citizenship` DISABLE KEYS */;
INSERT INTO `coach_citizenship` VALUES (3,'Portugal'),(1,'Spain'),(5,'Spain'),(6,'Spain'),(4,'Germany'),(2,'Spain');
/*!40000 ALTER TABLE `coach_citizenship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coach_club`
--

DROP TABLE IF EXISTS `coach_club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coach_club` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `club_id` bigint DEFAULT NULL,
  `head_coach_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkhdg3ex346y7qjpqdv82504xu` (`club_id`),
  KEY `FKnhe4mtphh3ly6hkodofx0l20m` (`head_coach_id`),
  CONSTRAINT `FKkhdg3ex346y7qjpqdv82504xu` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`),
  CONSTRAINT `FKnhe4mtphh3ly6hkodofx0l20m` FOREIGN KEY (`head_coach_id`) REFERENCES `head_coaches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coach_club`
--

LOCK TABLES `coach_club` WRITE;
/*!40000 ALTER TABLE `coach_club` DISABLE KEYS */;
INSERT INTO `coach_club` VALUES (1,'2026-01-08','2021-01-08',6,1),(2,'2027-12-12','2024-12-12',1,2),(3,'2028-07-15','2024-07-15',2,3),(4,'2026-01-01','2023-01-01',3,4),(5,'2026-12-01','2022-12-01',5,5),(6,'2027-08-12','2023-08-12',4,6);
/*!40000 ALTER TABLE `coach_club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `head_coaches`
--

DROP TABLE IF EXISTS `head_coaches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `head_coaches` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `head_coaches`
--

LOCK TABLES `head_coaches` WRITE;
/*!40000 ALTER TABLE `head_coaches` DISABLE KEYS */;
INSERT INTO `head_coaches` VALUES (1,'1985-05-06','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746763002/coach/1746762993579-arteta.jpg','Arteta'),(2,'1980-04-03','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726565/coach/1746726562747-Pep_2017_%28cropped%29.jpg','Pep'),(3,'1988-07-11','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726590/coach/1746726587447-RubenAmorim4.png','Ruben'),(4,'1979-05-20','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726623/coach/1746726620228-2022_Hansi_Flick_%28cropped%29.jpg','Hansi Flick'),(5,'1980-02-06','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746763065/coach/1746763063170-Enzo_Maresca_profile_2024-25_avatar-removebg.png','Enzo Maresca'),(6,'1979-01-12','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746764195/coach/1746764191207-enrique.png','Luis Enrique');
/*!40000 ALTER TABLE `head_coaches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `league_season`
--

DROP TABLE IF EXISTS `league_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `league_season` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `league_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeh56dd1sc0kskcxkh3crtrb9t` (`league_id`),
  CONSTRAINT `FKeh56dd1sc0kskcxkh3crtrb9t` FOREIGN KEY (`league_id`) REFERENCES `leagues` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `league_season`
--

LOCK TABLES `league_season` WRITE;
/*!40000 ALTER TABLE `league_season` DISABLE KEYS */;
INSERT INTO `league_season` VALUES (1,'2023-05-25',' 2022-2023','2022-08-01',1),(2,'2024-05-25',' 2023-2024','2023-08-01',1),(3,'2025-05-25',' 2024-2025','2024-08-01',1),(4,'2012-05-25','2011-2012','2011-08-01',2),(5,'2024-05-19','2023-2024','2023-08-11',3);
/*!40000 ALTER TABLE `league_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leagues`
--

DROP TABLE IF EXISTS `leagues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leagues` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leagues`
--

LOCK TABLES `leagues` WRITE;
/*!40000 ALTER TABLE `leagues` DISABLE KEYS */;
INSERT INTO `leagues` VALUES (1,'https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726747/league/1746726745232-epl.png','EPL'),(2,'https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726703/league/1746726701428-laliga.png','LaLiga'),(3,'https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746726717/league/1746726715321-leauge1.png','League1'),(4,'https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746763143/league/1746763141553-seria.png','Seri A'),(5,'https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746763162/league/1746763161367-Bundesliga_logo_%282017%29.png','Bundesliga');
/*!40000 ALTER TABLE `leagues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_action`
--

DROP TABLE IF EXISTS `match_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match_action` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `minute` int NOT NULL,
  `match_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2kthjkggihmig4al9tw8eywe` (`match_id`),
  KEY `FKhwwyrornqkr1lyvemgcjyner2` (`player_id`),
  CONSTRAINT `FKh2kthjkggihmig4al9tw8eywe` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`),
  CONSTRAINT `FKhwwyrornqkr1lyvemgcjyner2` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_action`
--

LOCK TABLES `match_action` WRITE;
/*!40000 ALTER TABLE `match_action` DISABLE KEYS */;
INSERT INTO `match_action` VALUES (1,'GOAL',12,1,8),(2,'OWN_GOAL',20,1,6),(3,'GOAL',34,1,5),(4,'GOAL',56,1,2),(5,'GOAL',78,1,9),(6,'YELLOW_CARD',90,1,12);
/*!40000 ALTER TABLE `match_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matches` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `away_score` int NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `host_score` int NOT NULL,
  `round` int NOT NULL,
  `away_id` bigint DEFAULT NULL,
  `host_id` bigint DEFAULT NULL,
  `season_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9vqu7347mgyqqjuyf6mjqumle` (`away_id`),
  KEY `FKcpo9b45p5n2n7ikrfxkiroopb` (`host_id`),
  KEY `FKiklv11qrf4ucv9s257u841tcr` (`season_id`),
  CONSTRAINT `FK9vqu7347mgyqqjuyf6mjqumle` FOREIGN KEY (`away_id`) REFERENCES `clubs` (`id`),
  CONSTRAINT `FKcpo9b45p5n2n7ikrfxkiroopb` FOREIGN KEY (`host_id`) REFERENCES `clubs` (`id`),
  CONSTRAINT `FKiklv11qrf4ucv9s257u841tcr` FOREIGN KEY (`season_id`) REFERENCES `league_season` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
INSERT INTO `matches` VALUES (1,2,'2025-05-09 15:49:45.000000',3,1,1,2,1),(2,1,'2025-05-09 15:50:02.000000',1,1,5,6,1),(3,0,'2025-05-09 15:53:03.000000',3,1,1,2,2),(4,2,'2025-05-09 15:59:43.000000',3,2,5,2,1),(5,3,'2025-05-09 16:00:27.000000',3,2,1,5,1),(6,1,'2025-05-10 11:34:48.000000',1,1,5,6,3),(7,2,'2025-05-10 11:35:03.000000',3,1,1,2,3);
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_citizenship`
--

DROP TABLE IF EXISTS `player_citizenship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_citizenship` (
  `player_id` bigint NOT NULL,
  `citizenship` varchar(255) DEFAULT NULL,
  KEY `FKjr2dffd1ba5so23j49tfxrumr` (`player_id`),
  CONSTRAINT `FKjr2dffd1ba5so23j49tfxrumr` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_citizenship`
--

LOCK TABLES `player_citizenship` WRITE;
/*!40000 ALTER TABLE `player_citizenship` DISABLE KEYS */;
INSERT INTO `player_citizenship` VALUES (3,'Portugal'),(4,'Spain'),(5,'Spain'),(8,'England'),(10,'Netherland'),(6,'England'),(11,'England'),(12,'Ivory of Coast'),(7,'Portugal'),(9,'Belgium'),(1,'Argentina'),(2,'Portugal'),(13,'Switzerland'),(14,'England'),(14,'Jamaica');
/*!40000 ALTER TABLE `player_citizenship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_position`
--

DROP TABLE IF EXISTS `player_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_position` (
  `player_id` bigint NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  KEY `FKg5r9b5rky6f6lp19or0kps066` (`player_id`),
  CONSTRAINT `FKg5r9b5rky6f6lp19or0kps066` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_position`
--

LOCK TABLES `player_position` WRITE;
/*!40000 ALTER TABLE `player_position` DISABLE KEYS */;
INSERT INTO `player_position` VALUES (3,'ST'),(4,'CM'),(5,'CDM'),(8,'RW'),(10,'CM'),(6,'CAM'),(11,'CF'),(12,'RW'),(7,'CM'),(9,'CAM'),(1,'CAM'),(2,'CAM'),(13,'CB'),(14,'LW'),(14,'RW');
/*!40000 ALTER TABLE `player_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `market_value` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `shirt_number` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'1987-06-24','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746543930/player/1746543928433-messi.jpg',100,'Messi',10),(2,'1995-05-09','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746762552/player/1746762549287-Bruno_Fernandes_Portugal%2C_2018.jpg',111,'Bruno Fernandes',12),(3,'1983-05-02','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719296/player/1746719296395-Kha-nang-Ronaldo-roi-Al-Nassr-gay-soc-tro-lai-Real-de-du-FIFA-Club-World-Cup-cristianoronaldo-mundial-clubes-realmadrid_1200_80-1743518356-690-width740height493.jpg',150,'Ronaldo',7),(4,'2000-05-01','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719597/player/1746719596524-pedri-0847.jpg',18,'Pedri',8),(5,'1995-07-18','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719656/player/1746719656013-rodri-man-city1.jpg',12,'Rodri',6),(6,'1999-02-14','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719749/player/1746719748498-foden-1.jpg',67,'Foden',10),(7,'1995-08-09','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746764285/player/1746764277723-vitinha-portugal-2024-1718211255-139478.jpg',90,'Vitinha',14),(8,'2000-05-01','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719846/player/1746719846083-rashford3.jpg',50,'Rashford',10),(9,'1994-07-04','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746719904/player/1746719904193-kevin.jpg',90,'Kevin',15),(10,'2000-04-08','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746720013/player/1746720012587-frenkie-de-jong-barc-1731545049.jpg',101,'Frenkie De Jong',27),(11,'1997-03-07','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746762653/player/1746762650816-mount.jpg',78,'Mason Mount',7),(12,'1999-01-02','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746762759/player/1746762756949-amad-diallo.jpg',99,'Amad Diallo',56),(13,'1995-07-19','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746801711/player/1746801708653-akanji.jpg',32,'Manuel Akanji',25),(14,'1994-12-08','https://res.cloudinary.com/dnx7kiqs1/image/upload/v1746802496/player/1746802495189-sterling.jpg',15,'Raheem Sterling',30);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transfer_history`
--

DROP TABLE IF EXISTS `transfer_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transfer_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `fee` double NOT NULL,
  `player_value` double NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `club_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8h1f3bh9nltnunk0waiighbu1` (`club_id`),
  KEY `FKpw4wnb3i37bthjq1k7enbpqdl` (`player_id`),
  CONSTRAINT `FK8h1f3bh9nltnunk0waiighbu1` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`),
  CONSTRAINT `FKpw4wnb3i37bthjq1k7enbpqdl` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transfer_history`
--

LOCK TABLES `transfer_history` WRITE;
/*!40000 ALTER TABLE `transfer_history` DISABLE KEYS */;
INSERT INTO `transfer_history` VALUES (1,'2004-01-01',0,100,'Youth Promote',3,1),(2,'2020-08-05',0,100,'Free Transfer',4,1),(3,'2020-01-08',0,71,'Free Transfer',2,2),(4,'2006-05-09',0,45,'Permanent',2,3),(5,'2018-01-01',23,88,'Permanent',3,4),(6,'2025-05-01',32,12,'Permanent',1,5),(7,'2015-09-04',0,1,'Youth Promote',1,6),(8,'2017-12-12',45,50,'Permanent',4,7),(9,'2011-01-02',0,50,'Youth Promote',2,8),(10,'2017-05-07',35,40,'Permanent',1,9),(11,'2019-05-15',0,91,'Free Transfer',3,10),(12,'2023-07-01',0,55,'Free Transfer',2,11),(13,'2018-09-01',0,99,'Youth Promote',2,12),(14,'2022-09-01',20,30,'Permanent',1,13),(15,'2018-01-15',0,32,'Free Transfer',7,13),(16,'2015-07-14',0,40,'Free Transfer',1,14),(17,'2022-07-13',56.2,70,'Permanent',5,14),(18,'2021-05-08',0,100,'Free Transfer',2,1);
/*!40000 ALTER TABLE `transfer_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `refreshtoken` mediumtext,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@gmail.com','ADMIN','$2a$10$Uz2fFR2SXEvt1MNZDZnOwu/6/T3MFYyHnOqZhZGrlcEgOeeCQptU6',NULL,'ADMIN'),(2,'user@gmail.com','User','$2a$10$cj/OY1S3aI2/3Z3cU7rD.OaAwVUo8k7Y5Hi4jFBzBt1syrQ3Qub4G','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGdtYWlsLmNvbSIsImV4cCI6MTc1NTUxMjI3OCwiaWF0IjoxNzQ2ODcyMjc4LCJ1c2VyIjp7ImlkIjoyLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwibmFtZSI6IlVzZXIiLCJyb2xlIjpudWxsfX0.BTs9LdL681Tg_UcC7AoxRNsg5nTe8Yg7zLBRepalYAOXxvm75MZbD2XFK0BKj_MqaX-gwL3G7pnusiaUc8sFJQ','USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-10 17:29:39