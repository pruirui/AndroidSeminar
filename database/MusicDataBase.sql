/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.50-log : Database - MusicDatabase
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`MusicDatabase` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `MusicDatabase`;

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values (1);

/*Table structure for table `music` */

DROP TABLE IF EXISTS `music`;

CREATE TABLE `music` (
  `music_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `url` varchar(100) NOT NULL,
  `singer` varchar(100) NOT NULL,
  `time` date NOT NULL,
  `status` int(1) NOT NULL,
  `u_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`music_id`),
  KEY `FKh2o342y7ck90fot84r9o5prn2` (`u_phone`),
  CONSTRAINT `FKh2o342y7ck90fot84r9o5prn2` FOREIGN KEY (`u_phone`) REFERENCES `user` (`phone`),
  CONSTRAINT `music_ibfk_1` FOREIGN KEY (`u_phone`) REFERENCES `user` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

/*Data for the table `music` */

insert  into `music`(`music_id`,`name`,`url`,`singer`,`time`,`status`,`u_phone`) values (21,'刘天是个大坏蛋','http://47.94.160.152:8000/uploadMusic/2021-07-22-刘天是个大坏蛋·4A+.mp3','4A+','2021-07-22',2,'13158689651'),(22,'测试用例','http://47.94.160.152:8000/uploadMusic/2021-07-23-测试用例·小锐.mp3','小锐','2021-07-23',2,'19914657629'),(23,'测试用例2','http://47.94.160.152:8000/uploadMusic/2021-07-23-测试用例2·彭小锐.mp3','彭小锐','2021-07-23',2,'19914657629'),(24,'测试用例3','http://47.94.160.152:8000/uploadMusic/2021-07-23-测试用例3·小锐.mp3','小锐','2021-07-23',2,'19914657629'),(25,'测试用例4','http://47.94.160.152:8000/uploadMusic/2021-07-23-测试用例4·小锐.mp3','小锐','2021-07-23',2,'19914657629'),(26,'测试用例5','http://47.94.160.152:8000/uploadMusic/2021-07-23-测试用例5·小锐.mp3','小锐','2021-07-23',2,'19914657629'),(28,'无','http://47.94.160.152:8000/uploadMusic/2021-07-23-无·赵纪淘.mp3','赵纪淘','2021-07-23',2,'15635165302'),(29,'zjt的第一首歌','http://47.94.160.152:8000/uploadMusic/2021-07-23-zjt的第一首歌·淘.mp3','淘','2021-07-23',2,'18888888888'),(30,'我是个好人','http://47.94.160.152:8000/uploadMusic/2021-07-23-我是个好人·一.mp3','一','2021-07-23',2,'15635165302');

/*Table structure for table `music_list` */

DROP TABLE IF EXISTS `music_list`;

CREATE TABLE `music_list` (
  `list_id` int(11) NOT NULL AUTO_INCREMENT,
  `list_name` varchar(100) NOT NULL,
  `time` date NOT NULL,
  `u_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`list_id`),
  KEY `FKjr1ujldug0syktbniqvdbfwel` (`u_phone`),
  CONSTRAINT `FKjr1ujldug0syktbniqvdbfwel` FOREIGN KEY (`u_phone`) REFERENCES `user` (`phone`),
  CONSTRAINT `music_list_ibfk_1` FOREIGN KEY (`u_phone`) REFERENCES `user` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

/*Data for the table `music_list` */

insert  into `music_list`(`list_id`,`list_name`,`time`,`u_phone`) values (9,'我的上传','2021-07-16','13158689651'),(10,'我的喜欢','2021-07-16','13158689651'),(15,'我的上传','2021-07-19','13333333333'),(16,'我的喜欢','2021-07-19','13333333333'),(25,'刘天是沃尔','2021-07-21','13158689651'),(30,'我的上传','2021-07-22','15555555555'),(31,'我的喜欢','2021-07-22','15555555555'),(32,'我的上传','2021-07-22','18888888888'),(33,'我的喜欢','2021-07-22','18888888888'),(34,'我的上传','2021-07-22','19914657629'),(35,'我的喜欢','2021-07-22','19914657629'),(36,'我的上传','2021-07-23','13811111111'),(37,'我的喜欢','2021-07-23','13811111111'),(38,'我的上传','2021-07-23','15635165302'),(39,'我的喜欢','2021-07-23','15635165302');

/*Table structure for table `music_music_list` */

DROP TABLE IF EXISTS `music_music_list`;

CREATE TABLE `music_music_list` (
  `music_id` int(11) DEFAULT NULL,
  `list_id` int(11) DEFAULT NULL,
  KEY `FK2ptep1wqlf11h3qcmp3o7xbqa` (`list_id`),
  KEY `FK2ademqd0heupakpvjncw5bcw5` (`music_id`),
  CONSTRAINT `FK2ademqd0heupakpvjncw5bcw5` FOREIGN KEY (`music_id`) REFERENCES `music` (`music_id`),
  CONSTRAINT `FK2ptep1wqlf11h3qcmp3o7xbqa` FOREIGN KEY (`list_id`) REFERENCES `music_list` (`list_id`),
  CONSTRAINT `music_music_list_ibfk_1` FOREIGN KEY (`music_id`) REFERENCES `music` (`music_id`),
  CONSTRAINT `music_music_list_ibfk_2` FOREIGN KEY (`list_id`) REFERENCES `music_list` (`list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `music_music_list` */

insert  into `music_music_list`(`music_id`,`list_id`) values (21,9),(22,34),(23,34),(24,34),(25,34),(26,34),(28,38),(29,32),(30,38);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `phone` varchar(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `status` int(1) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`phone`,`username`,`password`,`status`,`image`) values ('12345678910','admin','12345678910',1,NULL),('13158689651','张老刘','hH123456789',0,''),('13333333333','彭老三','hH123456789',0,NULL),('13811111111','阿伟','123456',0,NULL),('15555555555','小红','123456',0,NULL),('15635165302','我的','123456',0,'http://47.94.160.152:8000/uploadImage/2021-07-23-15635165302.jpg'),('18888888888','Amangul','123456',0,'http://47.94.160.152:8000/uploadImage/2021-07-23-18888888888.jpg'),('19914657629','彭小锐','123456',0,'http://47.94.160.152:8000/uploadImage/2021-07-23-19914657629.png');

/*Table structure for table `uuid` */

DROP TABLE IF EXISTS `uuid`;

CREATE TABLE `uuid` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `uuid` */

insert  into `uuid`(`next_val`) values (1);

/* Trigger structure for table `music` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `delete_music` */$$

/*!50003 CREATE */  /*!50003 TRIGGER `delete_music` BEFORE DELETE ON `music` FOR EACH ROW BEGIN
    
    DELETE FROM music_music_list
       WHERE music_music_list.`music_id` IN (SELECT OLD.music_id );
	
    END */$$


DELIMITER ;

/* Trigger structure for table `music_list` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `delete_music_list_trigger` */$$

/*!50003 CREATE */  /*!50003 TRIGGER `delete_music_list_trigger` BEFORE DELETE ON `music_list` FOR EACH ROW BEGIN
	DELETE FROM music_music_list WHERE music_music_list.`list_id` IN (SELECT OLD.list_id );
	
    END */$$


DELIMITER ;

/* Trigger structure for table `user` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `insert_user_traggeer` */$$

/*!50003 CREATE */  /*!50003 TRIGGER `insert_user_traggeer` AFTER INSERT ON `user` FOR EACH ROW BEGIN
	insert into music_list(u_phone,list_name,`time`) select NEW.phone,'我的上传',now() ;
	insert into music_list(u_phone,list_name,`time`) SELECT NEW.phone,'我的喜欢',NOW() ;
    END */$$


DELIMITER ;

/* Trigger structure for table `user` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `delete_user_trigger` */$$

/*!50003 CREATE */  /*!50003 TRIGGER `delete_user_trigger` BEFORE DELETE ON `user` FOR EACH ROW BEGIN
	delete from music_list where u_phone in (select OLD.phone);
	DELETE FROM music WHERE music.`status`=0 AND music.`u_phone` IN (SELECT OLD.phone) ;
	update music set u_phone='12345678910' where u_phone in (select OLD.phone);
    END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
