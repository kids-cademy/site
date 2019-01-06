-- MySQL dump 10.13  Distrib 5.5.60, for Win64 (AMD64)
--
-- Host: localhost    Database: kids_cademy
-- ------------------------------------------------------
-- Server version	5.5.60

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
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lifespan_minimum` int(11) NOT NULL,
  `lifespan_maximum` int(11) NOT NULL,
  `lifespan_captivity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_animal_object_id` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `app_load`
--

DROP TABLE IF EXISTS `app_load`;
/*!50001 DROP VIEW IF EXISTS `app_load`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `app_load` (
  `id` tinyint NOT NULL,
  `appId` tinyint NOT NULL,
  `timestamp` tinyint NOT NULL,
  `value` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `atlasobject`
--

DROP TABLE IF EXISTS `atlasobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `dtype` varchar(45) NOT NULL,
  `state` enum('DEVELOPMENT','CREATED','PUBLISHED') NOT NULL,
  `rank` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `display` varchar(45) DEFAULT NULL,
  `description` text,
  `iconPath` varchar(45) DEFAULT NULL,
  `thumbnailPath` varchar(45) DEFAULT NULL,
  `picturePath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_atlasobject_name` (`name`,`dtype`),
  KEY `fk_atlasobject_user1_idx` (`user_id`),
  CONSTRAINT `fk_atlasobject_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=408 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atlasobject_aliases`
--

DROP TABLE IF EXISTS `atlasobject_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_aliases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `aliases` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_alias_atlas_object1_idx` (`atlasobject_id`),
  CONSTRAINT `fk_alias_objec_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=442 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atlasobject_facts`
--

DROP TABLE IF EXISTS `atlasobject_facts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_facts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `facts_key` varchar(45) NOT NULL,
  `facts` tinytext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_atlasobject_facts_key` (`atlasobject_id`,`facts_key`),
  KEY `id_atlasobject_facts_object_id` (`atlasobject_id`),
  CONSTRAINT `fk_fact_object1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atlasobject_links`
--

DROP TABLE IF EXISTS `atlasobject_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `url` varchar(128) NOT NULL,
  `name` varchar(45) NOT NULL,
  `iconPath` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_link_url` (`url`,`atlasobject_id`),
  KEY `idx_link_atlasobject_id` (`atlasobject_id`),
  CONSTRAINT `fk_link_atlasobject_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=817 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atlasobject_related`
--

DROP TABLE IF EXISTS `atlasobject_related`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_related` (
  `atlasobject_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `relevance` float NOT NULL,
  PRIMARY KEY (`atlasobject_id`,`name`),
  KEY `ix_atlasobject_related_id` (`atlasobject_id`),
  KEY `ix_atlastobject_related_name` (`name`),
  CONSTRAINT `fk_atlastobject_related_name` FOREIGN KEY (`name`) REFERENCES `atlasobject` (`name`) ON DELETE CASCADE,
  CONSTRAINT `fk_atlasobject_related_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atlasobject_spreading`
--

DROP TABLE IF EXISTS `atlasobject_spreading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atlasobject_spreading` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `atlasobject_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `area` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_region_area` (`atlasobject_id`,`name`,`area`),
  KEY `idx_region_atlasobject_id` (`atlasobject_id`),
  CONSTRAINT `fk_region_atlasobject_id` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `deviceId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `event` varchar(45) NOT NULL,
  `parameter1` varchar(80) DEFAULT NULL,
  `parameter2` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`appId`,`deviceId`),
  KEY `fk_audit_device1_idx` (`deviceId`),
  KEY `fk_audit_app1_idx` (`appId`),
  CONSTRAINT `fk_audit_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_audit_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bird`
--

DROP TABLE IF EXISTS `bird`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bird` (
  `id` int(11) NOT NULL,
  `wingspan_minimum` int(11) NOT NULL,
  `wingspan_maximum` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bird_object_id` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `display` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `iconPath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collection_atlasobject`
--

DROP TABLE IF EXISTS `collection_atlasobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_atlasobject` (
  `collection_id` int(11) NOT NULL,
  `atlasobject_id` int(11) NOT NULL,
  PRIMARY KEY (`collection_id`,`atlasobject_id`),
  KEY `fk_collection_has_atlasobject_atlasobject1_idx` (`atlasobject_id`),
  KEY `fk_collection_has_atlasobject_collection1_idx` (`collection_id`),
  CONSTRAINT `fk_collection_has_atlasobject_atlasobject1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_collection_has_atlasobject_collection1` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crash_report`
--

DROP TABLE IF EXISTS `crash_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crash_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `deviceId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `stackTrace` text NOT NULL,
  PRIMARY KEY (`id`,`appId`,`deviceId`),
  KEY `fk_crash_report_device1_idx` (`deviceId`),
  KEY `fk_crash_report_app1_idx` (`appId`),
  CONSTRAINT `fk_crash_report_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_crash_report_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `developer_message`
--

DROP TABLE IF EXISTS `developer_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `developer_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `text` text NOT NULL,
  `developerName` varchar(45) DEFAULT NULL,
  `senderEmail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`appId`),
  KEY `fk_message_app1_idx` (`appId`),
  CONSTRAINT `fk_message_app1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelId` int(11) NOT NULL,
  `serial` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`modelId`),
  KEY `fk_device_model1_idx` (`modelId`),
  CONSTRAINT `fk_device_model1` FOREIGN KEY (`modelId`) REFERENCES `model` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `i18ntext`
--

DROP TABLE IF EXISTS `i18ntext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `i18ntext` (
  `text_hash` int(11) NOT NULL,
  `language` char(2) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`text_hash`,`language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `instrument`
--

DROP TABLE IF EXISTS `instrument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instrument` (
  `id` int(11) NOT NULL,
  `category` tinyint(4) NOT NULL,
  `sampleTitle` varchar(80) DEFAULT NULL,
  `samplePath` varchar(45) DEFAULT NULL,
  `waveformPath` varchar(45) DEFAULT NULL,
  `date_value` bigint(20) DEFAULT NULL,
  `date_mask` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_instrument_atlas_object1` FOREIGN KEY (`id`) REFERENCES `atlasobject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword` (
  `name` char(20) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `likecounter`
--

DROP TABLE IF EXISTS `likecounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likecounter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `likecounter_reasons`
--

DROP TABLE IF EXISTS `likecounter_reasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likecounter_reasons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `likecounter_id` int(11) NOT NULL,
  `reasons` enum('NOT_INTUITIVE','BAD_DESIGN','BAD_CONTENT','SLOW_LOADING','OTHER') NOT NULL,
  PRIMARY KEY (`id`,`likecounter_id`),
  KEY `fk_dislike_reason_like_counter_idx` (`likecounter_id`),
  CONSTRAINT `fk_dislike_reason_like_counter` FOREIGN KEY (`likecounter_id`) REFERENCES `likecounter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `version` varchar(45) NOT NULL,
  `apiLevel` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `no_ads_survey`
--

DROP TABLE IF EXISTS `no_ads_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `no_ads_survey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceId` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `agree` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_no_ads_survey_device1_idx` (`deviceId`),
  CONSTRAINT `fk_no_ads_survey_device1` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `search_index`
--

DROP TABLE IF EXISTS `search_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_index` (
  `keyword_name` char(20) NOT NULL,
  `atlasobject_id` int(11) NOT NULL,
  `relevance` int(11) NOT NULL,
  PRIMARY KEY (`keyword_name`,`atlasobject_id`),
  KEY `fk_search_index_atlas_object1_idx` (`atlasobject_id`),
  CONSTRAINT `fk_search_index_atlas_object1` FOREIGN KEY (`atlasobject_id`) REFERENCES `atlasobject` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_search_index_keyword1` FOREIGN KEY (`keyword_name`) REFERENCES `keyword` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emailAddress` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emailAddress_UNIQUE` (`emailAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `app_load`
--

/*!50001 DROP TABLE IF EXISTS `app_load`*/;
/*!50001 DROP VIEW IF EXISTS `app_load`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `app_load` AS select `audit`.`id` AS `id`,`audit`.`appId` AS `appId`,`audit`.`timestamp` AS `timestamp`,count(0) AS `value` from `audit` where (`audit`.`event` = 'APP_LOAD') group by year(`audit`.`timestamp`),month(`audit`.`timestamp`),dayofmonth(`audit`.`timestamp`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-05 20:01:14
