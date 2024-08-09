-- MySQL dump 10.13  Distrib 8.4.0, for Linux (x86_64)
--
-- Host: localhost    Database: data_badminton
-- ------------------------------------------------------
-- Server version	8.4.0

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='比赛报名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_applicant`
--

LOCK TABLES `tb_race_applicant` WRITE;
/*!40000 ALTER TABLE `tb_race_applicant` DISABLE KEYS */;
INSERT INTO `tb_race_applicant` VALUES (8,1,2),(14,1,24),(15,1,3),(16,1,4),(17,1,5),(18,1,6),(19,1,7),(20,1,8),(21,1,9),(22,1,1),(23,6,24),(24,6,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_battle`
--

LOCK TABLES `tb_race_battle` WRITE;
/*!40000 ALTER TABLE `tb_race_battle` DISABLE KEYS */;
INSERT INTO `tb_race_battle` VALUES (41,1,1,2,7,25,24,3,23,2,1,1),(42,1,2,4,8,21,5,6,9,2,1,1),(43,1,3,2,6,6,9,24,21,2,1,1),(44,1,4,3,4,21,1,5,19,2,1,1),(45,1,5,8,3,15,7,9,21,2,1,1),(46,1,6,24,5,17,1,4,22,2,1,1),(47,1,7,7,4,0,2,1,0,0,NULL,1),(48,1,8,3,7,0,6,9,0,0,NULL,1),(49,1,9,24,1,0,8,5,0,0,NULL,1),(50,1,10,6,3,0,2,9,0,0,NULL,1),(51,1,11,4,9,0,8,24,0,0,NULL,1),(52,1,12,6,4,0,2,8,0,0,NULL,1),(53,1,13,1,24,0,5,2,0,0,NULL,1),(54,1,14,6,1,0,7,5,0,0,NULL,1),(55,1,15,2,24,0,3,8,0,0,NULL,1),(56,1,16,9,1,0,7,3,0,0,NULL,1),(57,1,17,6,7,0,4,2,0,0,NULL,1),(58,1,18,3,1,0,5,9,0,0,NULL,1),(59,1,19,4,5,0,8,6,0,0,NULL,1),(60,1,20,24,7,0,8,9,0,0,NULL,1);
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
  `field_num` varchar(100) COLLATE utf8mb4_bin DEFAULT '1',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `add_context` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `battle_generate_algorithm` int unsigned NOT NULL DEFAULT '1',
  `default_referee` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`race_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_main`
--

LOCK TABLES `tb_race_main` WRITE;
/*!40000 ALTER TABLE `tb_race_main` DISABLE KEYS */;
INSERT INTO `tb_race_main` VALUES (1,'43fdfs',3,'2024-06-30 14:00:00',1,1,1,11,1,7,NULL,0,'1','34234','rwr23','2024-06-30 20:58:13',1,1),(6,'啊啊啊',1,'2024-07-12 18:00:00',1,1,5,21,24,18,NULL,1,'1','阿巴','','2024-07-11 20:05:13',1,1),(7,'测试比赛1',1,'2024-07-19 14:00:00',1,1,1,21,1,8,NULL,0,'1','周氏宗祠','','2024-07-18 12:07:34',1,0);
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
INSERT INTO `tb_race_rank` VALUES (61,1,2,0,0,0,0),(62,1,24,0,0,0,0),(63,1,3,0,0,0,0),(64,1,4,0,0,0,0),(65,1,5,0,0,0,0),(66,1,6,0,0,0,0),(67,1,7,0,0,0,0),(68,1,8,0,0,0,0),(69,1,9,0,0,0,0),(70,1,1,0,0,0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_referee`
--

LOCK TABLES `tb_race_referee` WRITE;
/*!40000 ALTER TABLE `tb_race_referee` DISABLE KEYS */;
INSERT INTO `tb_race_referee` VALUES (5,1,1,1),(10,1,24,1);
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
  `generate_class_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `race_main_type` int unsigned NOT NULL DEFAULT '1',
  `per_session_num` int unsigned NOT NULL,
  `min_players` int unsigned NOT NULL,
  PRIMARY KEY (`scheme_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race_scheme`
--

LOCK TABLES `tb_race_scheme` WRITE;
/*!40000 ALTER TABLE `tb_race_scheme` DISABLE KEYS */;
INSERT INTO `tb_race_scheme` VALUES (1,'八人转','每人跟他人各搭档一次，进行双打比赛','每人跟他人各搭档一次，进行双打比赛','BadmintonDoubleRaceGeneratorTool8',1,8,4),(2,'超八转','每人跟随机8人各搭档1次，进行双打比赛','每人跟随机8人各搭档1次，进行双打比赛','BadmintonDoubleRaceGeneratorTool8',1,8,4),(3,'单打转','每人比赛N次，多种场次可选','每人比赛N次，多种场次可选',NULL,2,4,3);
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
INSERT INTO `tb_wx_user` VALUES (1,'oumPW5ao1lK4qxjAVsIPcMmD1wv8','啊浒🏸','data:image/png;base64,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','2024-06-23 14:27:18','2024-07-13 22:29:11',1,2),(2,'222','张无忌','http://192.168.2.185:8080/static/images/zwj.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(3,'333','赵敏','http://192.168.2.185:8080/static/images/zm.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(4,'444','小昭','http://192.168.2.185:8080/static/images/xz.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(5,'555','韦一笑','http://192.168.2.185:8080/static/images/wyx.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(6,'666','无相禅师','http://192.168.2.185:8080/static/images/wxcs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(7,'777','灭绝师太','http://192.168.2.185:8080/static/images/mjst.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(8,'888','朱元璋','http://192.168.2.185:8080/static/images/zyz.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(9,'999','陈友谅','http://192.168.2.185:8080/static/images/cyl.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(10,'1010101','宋青书','http://192.168.2.185:8080/static/images/sqs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(11,'111111','张三丰','http://192.168.2.185:8080/static/images/zsf.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(12,'121212','殷野王','http://192.168.2.185:8080/static/images/yyw.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(13,'1313131','谢逊','http://192.168.2.185:8080/static/images/xx.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(14,'14141414','何太冲','http://192.168.2.185:8080/static/images/htc.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(15,'1515151','张翠山','http://192.168.2.185:8080/static/images/zcs.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(16,'16161616','鹿杖客','http://192.168.2.185:8080/static/images/lzk.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(17,'171717','水云道人','http://192.168.2.185:8080/static/images/sydr.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(18,'18181818','木桑道长','http://192.168.2.185:8080/static/images/msdr.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(19,'19191991','曹化淳','http://192.168.2.185:8080/static/images/chc.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(20,'202002','马钰','http://192.168.2.185:8080/static/images/my.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(21,'2121212','小龙女','http://192.168.2.185:8080/static/images/xln.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',2,1),(22,'2222222','尹志平','http://192.168.2.185:8080/static/images/yzp.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(23,'232323232','丘处机','http://192.168.2.185:8080/static/images/qcj.jpg','2024-06-23 14:27:18','2024-06-28 21:27:23',1,1),(24,'oumPW5b7aHI6ToayzTwVS8JzqhFs','啦啦啦','data:image/png;base64,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','2024-07-03 11:55:34','2024-07-17 13:00:47',1,1);
/*!40000 ALTER TABLE `tb_wx_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-19  4:19:54
