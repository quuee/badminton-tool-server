-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 192.168.2.32    Database: data_badminton
-- ------------------------------------------------------
-- Server version	8.4.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_race_applicant`
--

DROP TABLE IF EXISTS `tb_race_applicant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_applicant` (
  `apply_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_id` bigint unsigned NOT NULL,
  `uid` bigint unsigned NOT NULL,
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='比赛报名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_applicant`
--

LOCK TABLES `tb_race_applicant` WRITE;
/*!40000 ALTER TABLE `tb_race_applicant` DISABLE KEYS */;
INSERT INTO `tb_race_applicant` VALUES (8,1,2),(14,1,24),(15,1,3),(16,1,4),(17,1,5),(18,1,6),(19,1,7),(20,1,8),(21,1,9),(22,1,1),(23,6,24),(24,6,1),(28,12,1),(29,12,24),(30,12,23),(31,12,22),(33,12,20),(34,12,21);
/*!40000 ALTER TABLE `tb_race_applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race_battle`
--

DROP TABLE IF EXISTS `tb_race_battle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_battle` (
  `bid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_id` bigint unsigned NOT NULL,
  `round_num` int unsigned NOT NULL,
  `player_uid1` bigint unsigned NOT NULL COMMENT '组一 选手一',
  `player_uid2` bigint unsigned NOT NULL COMMENT '组一 选手二',
  `first_partner_score` int unsigned NOT NULL COMMENT '组一 比分',
  `player_uid3` bigint unsigned NOT NULL COMMENT '组二 选手一',
  `player_uid4` bigint unsigned NOT NULL COMMENT '组二 选手二',
  `second_partner_score` int unsigned NOT NULL COMMENT '组二 比分',
  `battle_state` int unsigned NOT NULL,
  `referee_uid` bigint unsigned DEFAULT NULL COMMENT '裁判',
  `race_main_type` int NOT NULL DEFAULT '1' COMMENT '比赛类型 这里冗余，方便查询',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_battle`
--

LOCK TABLES `tb_race_battle` WRITE;
/*!40000 ALTER TABLE `tb_race_battle` DISABLE KEYS */;
INSERT INTO `tb_race_battle` VALUES (41,1,1,2,7,25,24,3,23,2,1,1),(42,1,2,4,8,21,5,6,9,2,1,1),(43,1,3,2,6,6,9,24,21,2,1,1),(44,1,4,3,4,21,1,5,19,2,1,1),(45,1,5,8,3,15,7,9,21,2,1,1),(46,1,6,24,5,17,1,4,22,2,NULL,1),(47,1,7,7,4,8,2,1,21,2,1,1),(48,1,8,3,7,13,6,9,21,2,1,1),(49,1,9,24,1,21,8,5,15,2,1,1),(50,1,10,6,3,21,2,9,9,2,1,1),(51,1,11,4,9,21,8,24,17,2,1,1),(52,1,12,6,4,15,2,8,21,2,1,1),(53,1,13,1,24,7,5,2,21,2,1,1),(54,1,14,6,1,12,7,5,21,2,1,1),(55,1,15,2,24,21,3,8,13,2,1,1),(56,1,16,9,1,21,7,3,19,2,1,1),(57,1,17,6,7,21,4,2,18,2,1,1),(58,1,18,3,1,22,5,9,24,2,1,1),(59,1,19,4,5,0,8,6,0,0,NULL,1),(60,1,20,24,7,0,8,9,0,0,NULL,1),(436,12,1,1,0,0,24,0,0,0,NULL,1),(437,12,2,23,0,0,22,0,0,0,NULL,1),(438,12,3,21,0,0,20,0,0,0,NULL,1),(439,12,4,1,0,0,23,0,0,0,NULL,1),(440,12,5,24,0,0,22,0,0,0,NULL,1),(441,12,6,21,0,0,1,0,0,0,NULL,1),(442,12,7,20,0,0,24,0,0,0,NULL,1),(443,12,8,23,0,0,21,0,0,0,NULL,1),(444,12,9,22,0,0,20,0,0,0,NULL,1),(445,12,10,1,0,0,22,0,0,0,NULL,1),(446,12,11,24,0,0,23,0,0,0,NULL,1),(447,12,12,21,0,0,24,0,0,0,NULL,1),(448,12,13,20,0,0,1,0,0,0,NULL,1),(449,12,14,23,0,0,20,0,0,0,NULL,1),(450,12,15,22,0,0,21,0,0,0,NULL,1);
/*!40000 ALTER TABLE `tb_race_battle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race_main`
--

DROP TABLE IF EXISTS `tb_race_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_main` (
  `race_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `race_state` int NOT NULL DEFAULT '1',
  `race_date_time` datetime NOT NULL,
  `race_main_type` int unsigned NOT NULL,
  `scheme_id` bigint unsigned NOT NULL,
  `raceBOX` int NOT NULL,
  `race_score_mode` int unsigned NOT NULL DEFAULT '11',
  `organizer_uid` bigint unsigned NOT NULL,
  `predict_apply_num` int unsigned DEFAULT NULL,
  `every_play_times` int unsigned DEFAULT NULL,
  `gender_limit` int NOT NULL DEFAULT '0',
  `field_num` int unsigned DEFAULT '1',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `add_context` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `battle_generate_algorithm` int unsigned NOT NULL DEFAULT '1',
  `default_referee` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`race_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_main`
--

LOCK TABLES `tb_race_main` WRITE;
/*!40000 ALTER TABLE `tb_race_main` DISABLE KEYS */;
INSERT INTO `tb_race_main` VALUES (1,'混双测试',1,'2024-08-02 18:00:00',1,1,1,21,1,10,NULL,0,2,'萧江羽毛球馆','带球','2024-08-09 13:28:11',1,1),(6,'啊啊啊',1,'2024-08-03 18:00:00',1,1,5,21,24,18,NULL,1,1,'阿巴','','2024-08-09 13:28:11',1,1),(11,'男双局',1,'2024-08-04 18:00:00',1,1,3,21,1,6,NULL,1,1,'周氏宗祠','一山','2024-08-09 13:28:11',1,0),(12,'男单局',3,'2024-08-05 18:00:00',2,4,1,21,1,6,NULL,1,1,'周氏宗祠',NULL,'2024-08-09 13:28:11',1,0),(13,'男单比赛1',1,'2024-08-06 18:00:00',2,4,5,21,1,8,NULL,1,1,'体育馆',NULL,'2024-08-09 13:28:11',1,1),(14,'女单比赛1212',1,'2024-08-07 18:00:00',2,4,1,21,1,8,NULL,2,1,NULL,NULL,'2024-08-09 13:28:47',1,1),(15,'女双女双',4,'2024-08-08 18:00:00',1,1,1,21,1,8,NULL,2,1,'萧江羽毛球馆',NULL,'2024-08-09 13:39:50',1,1);
/*!40000 ALTER TABLE `tb_race_main` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race_rank`
--

DROP TABLE IF EXISTS `tb_race_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_rank` (
  `rid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_id` bigint unsigned NOT NULL,
  `uid` bigint unsigned NOT NULL,
  `victories` int unsigned NOT NULL DEFAULT '0',
  `failures` int unsigned NOT NULL DEFAULT '0',
  `odds` int unsigned NOT NULL DEFAULT '0',
  `odds_difference` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_rank`
--

LOCK TABLES `tb_race_rank` WRITE;
/*!40000 ALTER TABLE `tb_race_rank` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_race_rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race_referee`
--

DROP TABLE IF EXISTS `tb_race_referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_referee` (
  `rid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_id` bigint unsigned NOT NULL,
  `uid` bigint unsigned NOT NULL,
  `master` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_referee`
--

LOCK TABLES `tb_race_referee` WRITE;
/*!40000 ALTER TABLE `tb_race_referee` DISABLE KEYS */;
INSERT INTO `tb_race_referee` VALUES (5,1,1,1),(6,1,24,1),(36,12,1,1);
/*!40000 ALTER TABLE `tb_race_referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race_scheme`
--

DROP TABLE IF EXISTS `tb_race_scheme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race_scheme` (
  `scheme_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `race_scheme` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `simple_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `detail_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `generate_class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `race_main_type` int unsigned NOT NULL DEFAULT '1',
  `field_accommodate_player_num` int unsigned NOT NULL,
  `min_players` int unsigned NOT NULL,
  PRIMARY KEY (`scheme_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_scheme`
--

LOCK TABLES `tb_race_scheme` WRITE;
/*!40000 ALTER TABLE `tb_race_scheme` DISABLE KEYS */;
INSERT INTO `tb_race_scheme` VALUES (1,'八人转','每人跟他人各搭档一次，进行双打比赛','每人跟他人各搭档一次，进行双打比赛','BadmintonDoubleRaceGeneratorTool8',1,8,4),(2,'超八转','每人跟随机8人各搭档1次，进行双打比赛','每人跟随机8人各搭档1次，进行双打比赛','BadmintonDoubleRaceGeneratorTool8',1,8,4),(4,'单循环','每人跟其他选手各比赛1场','每人跟其他选手各比赛1场','BadmintonSingleRaceGeneratorTool',2,4,3);
/*!40000 ALTER TABLE `tb_race_scheme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_wx_user`
--

DROP TABLE IF EXISTS `tb_wx_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_wx_user` (
  `uid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `avatar_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `register_date` datetime NOT NULL,
  `last_login_date` datetime NOT NULL,
  `gender` int DEFAULT '0',
  `level` int unsigned DEFAULT '1',
  PRIMARY KEY (`uid`),
  KEY `tb_wx_user_openid_IDX` (`openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_wx_user`
--

LOCK TABLES `tb_wx_user` WRITE;
/*!40000 ALTER TABLE `tb_wx_user` DISABLE KEYS */;
INSERT INTO `tb_wx_user` VALUES (1,'oumPW5ao1lK4qxjAVsIPcMmD1wv8','啊浒🏸','data:image/png;base64,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','2024-06-23 14:27:18','2024-08-09 15:10:08',1,2),(2,'222','张无忌','http://192.168.2.185:8080/static/images/zwj.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(3,'333','赵敏','http://192.168.2.185:8080/static/images/zm.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(4,'444','小昭','http://192.168.2.185:8080/static/images/xz.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(5,'555','韦一笑','http://192.168.2.185:8080/static/images/wyx.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(6,'666','无相禅师','http://192.168.2.185:8080/static/images/wxcs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(7,'777','灭绝师太','http://192.168.2.185:8080/static/images/mjst.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(8,'888','朱元璋','http://192.168.2.185:8080/static/images/zyz.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(9,'999','陈友谅','http://192.168.2.185:8080/static/images/cyl.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(10,'1010101','宋青书','http://192.168.2.185:8080/static/images/sqs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(11,'111111','张三丰','http://192.168.2.185:8080/static/images/zsf.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(12,'121212','殷野王','http://192.168.2.185:8080/static/images/yyw.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(13,'1313131','谢逊','http://192.168.2.185:8080/static/images/xx.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(14,'14141414','何太冲','http://192.168.2.185:8080/static/images/htc.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(15,'1515151','张翠山','http://192.168.2.185:8080/static/images/zcs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(16,'16161616','鹿杖客','http://192.168.2.185:8080/static/images/lzk.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(17,'171717','水云道人','http://192.168.2.185:8080/static/images/sydr.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(18,'18181818','木桑道长','http://192.168.2.185:8080/static/images/msdr.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(19,'19191991','曹化淳','http://192.168.2.185:8080/static/images/chc.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(20,'202002','马钰','http://192.168.2.185:8080/static/images/my.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(21,'2121212','小龙女','http://192.168.2.185:8080/static/images/xln.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(22,'2222222','尹志平','http://192.168.2.185:8080/static/images/yzp.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(23,'232323232','丘处机','http://192.168.2.185:8080/static/images/qcj.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(24,'oumPW5b7aHI6ToayzTwVS8JzqhFs','啦啦啦','data:image/png;base64,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','2024-07-03 11:55:34','2024-07-21 21:09:14',1,1);
/*!40000 ALTER TABLE `tb_wx_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'data_badminton'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-09 15:51:55
