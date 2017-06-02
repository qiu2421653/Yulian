/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.1.73 : Database - yulian
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yulian` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `yulian`;

/*Table structure for table `accounting` */

DROP TABLE IF EXISTS `accounting`;

CREATE TABLE `accounting` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `topId` char(36) DEFAULT NULL COMMENT '主帖ID\r\n            ',
  `userId` char(36) DEFAULT NULL COMMENT '用户ID',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(1) DEFAULT NULL COMMENT '类型(1打赏给人 2被打赏 3充值 4积分兑换)',
  `currency` int(12) DEFAULT NULL COMMENT '面值',
  `currencyBefore` int(12) DEFAULT NULL COMMENT '更新前面值',
  `point` int(12) DEFAULT NULL COMMENT '积分',
  `pointBefore` int(12) DEFAULT NULL COMMENT '更新前积分',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='禹币明细';

/*Data for the table `accounting` */

insert  into `accounting`(`uuid`,`topId`,`userId`,`createrId`,`createDate`,`type`,`currency`,`currencyBefore`,`point`,`pointBefore`) values ('0cc334a4-0243-4a48-9476-d691536bd4a5','4797b896-9807-46b9-ab9c-52d1829984b0','9bc73bd6-be69-4334-8391-f1d91ac82745',NULL,'2016-06-30 09:40:13',2,52,122,NULL,NULL),('0f7b8b3e-96f3-4317-8b78-d89f31d2d814','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 15:02:21',1,-1,36,NULL,NULL),('1e47b185-8bf1-4b70-a98f-008e8e9d3e5c','3171db3b-8f34-47e0-b1f6-527582a0c99b','4651b11f-e8d9-4fba-b0cd-a5351eec89af',NULL,'2016-07-01 10:22:13',2,2,123,NULL,NULL),('2788059d-5b8a-46ef-b6f5-159626116a63','a8a2dadf-4168-4c5f-a565-bf7b2583b519',NULL,'619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:07:30',1,-1,77,NULL,NULL),('2bba43a2-1cb2-4285-adb6-092e10136d02','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','4651b11f-e8d9-4fba-b0cd-a5351eec89af',NULL,'2016-06-30 09:10:00',2,2,105,NULL,NULL),('2d08903b-ee7e-4b34-8a32-3b3d89583d62','4797b896-9807-46b9-ab9c-52d1829984b0','619e1e94-693a-42ce-bb77-8b8d31423298',NULL,'2016-07-01 16:59:57',2,5,238,NULL,NULL),('6353ef51-a62c-4670-885a-62cb7687d971','a8a2dadf-4168-4c5f-a565-bf7b2583b519','619e1e94-693a-42ce-bb77-8b8d31423298',NULL,'2016-06-29 18:07:30',2,1,116,NULL,NULL),('6cb624bc-c070-481e-b415-adbe0cd533e0','3171db3b-8f34-47e0-b1f6-527582a0c99b',NULL,'4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-07-01 10:22:13',1,-2,235,NULL,NULL),('88f83548-ff3b-4d5d-a828-22519a68e410','039d84b0-0175-4199-9e46-4eb3b9b48961','5c6e8194-4b48-4237-8ac1-1d8846b6607f',NULL,'2016-07-01 17:13:25',2,1,257,NULL,NULL),('8bc49b1b-93fc-4b61-a5a3-4e02aec75acc','7d4378e2-41b0-4b6f-b903-84cb7e4d58fa','619e1e94-693a-42ce-bb77-8b8d31423298',NULL,'2016-07-01 15:21:16',2,5,233,NULL,NULL),('9abba12b-a38c-47ed-8bd0-f73eed5f061d','4797b896-9807-46b9-ab9c-52d1829984b0',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:40:13',1,-52,152,NULL,NULL),('bce0a38f-a9ed-478b-803c-a4ca0c104a21','039d84b0-0175-4199-9e46-4eb3b9b48961',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01 17:13:20',1,-1,34,NULL,NULL),('c47143c1-0dd8-4c62-901f-edc99f4707c0','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71',NULL,'4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 09:10:00',1,-2,124,NULL,NULL),('c6ad29a7-df16-4cb8-ab8d-9f588dd4fd5b','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','4651b11f-e8d9-4fba-b0cd-a5351eec89af',NULL,'2016-06-30 09:07:20',2,5,99,NULL,NULL),('c965c759-b850-4935-b6be-603fa1ca2378','039d84b0-0175-4199-9e46-4eb3b9b48961',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01 17:13:07',1,-13,47,NULL,NULL),('d0f08610-db14-40b8-8bbe-04716fb2d7e4','039d84b0-0175-4199-9e46-4eb3b9b48961',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01 17:13:25',1,-1,33,NULL,NULL),('d1558be0-9f2d-4e69-a413-f9efeee4f79d','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71',NULL,'4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 09:07:20',1,-5,129,NULL,NULL),('d3d43e70-d77a-4635-991e-f6a2cba7b699','7d4378e2-41b0-4b6f-b903-84cb7e4d58fa',NULL,'619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 15:21:16',1,-5,137,NULL,NULL),('d6a5b0a6-fde4-45d9-b913-b16ee32d0366','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','5c6e8194-4b48-4237-8ac1-1d8846b6607f',NULL,'2016-06-30 15:02:21',2,1,224,NULL,NULL),('e6eb48cf-f9a8-41c5-8f49-b8042fa23903','039d84b0-0175-4199-9e46-4eb3b9b48961','5c6e8194-4b48-4237-8ac1-1d8846b6607f',NULL,'2016-07-01 17:13:20',2,1,256,NULL,NULL),('e8b24984-1759-4c51-9f18-b2d6865e5b61','039d84b0-0175-4199-9e46-4eb3b9b48961','5c6e8194-4b48-4237-8ac1-1d8846b6607f',NULL,'2016-07-01 17:13:07',2,13,243,NULL,NULL),('ec8f45fe-3f41-4a63-9611-3156e692f4da','4797b896-9807-46b9-ab9c-52d1829984b0',NULL,'619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 16:59:57',1,-5,136,NULL,NULL);

/*Table structure for table `accusation` */

DROP TABLE IF EXISTS `accusation`;

CREATE TABLE `accusation` (
  `uuid` char(36) CHARACTER SET utf8 NOT NULL COMMENT '主键\r\n            ',
  `topId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '主帖ID\r\n            ',
  `userId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户ID',
  `createrId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(2) DEFAULT NULL COMMENT '举报类型',
  `remarks` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '举报内容',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='举报';

/*Data for the table `accusation` */

insert  into `accusation`(`uuid`,`topId`,`userId`,`createrId`,`createDate`,`type`,`remarks`) values ('3df54a2d-1c5c-418d-b160-a31c211307ca',NULL,NULL,NULL,'2016-06-29 18:09:03',3,NULL),('a65fc4b8-fd5b-4565-8571-f516aa7670f5',NULL,NULL,NULL,'2016-06-30 11:21:11',2,NULL);

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `addressee` varchar(20) DEFAULT NULL COMMENT '收件人',
  `code` int(6) DEFAULT NULL COMMENT '邮编',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址管理';

/*Data for the table `address` */

/*Table structure for table `advise` */

DROP TABLE IF EXISTS `advise`;

CREATE TABLE `advise` (
  `uuid` char(36) CHARACTER SET utf8 NOT NULL COMMENT '主键\r\n            ',
  `remarks` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '意见内容',
  `createrId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312 COMMENT='意见反馈\r\n';

/*Data for the table `advise` */

/*Table structure for table `collection` */

DROP TABLE IF EXISTS `collection`;

CREATE TABLE `collection` (
  `uuid` char(36) CHARACTER SET utf8 NOT NULL COMMENT '主键\r\n            ',
  `expId` char(36) CHARACTER SET utf8 NOT NULL COMMENT '经历ID\r\n            ',
  `createrId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updaterId` char(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态，10|启用/20|停用/30|删除/',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='收藏表\r\n';

/*Data for the table `collection` */

/*Table structure for table `comenum` */

DROP TABLE IF EXISTS `comenum`;

CREATE TABLE `comenum` (
  `uuid` char(36) NOT NULL COMMENT 'uuid 主键',
  `comClass` varchar(20) NOT NULL COMMENT '分类',
  `code` varchar(50) NOT NULL COMMENT 'code值',
  `name` varchar(100) NOT NULL COMMENT 'code名称',
  `word` varchar(50) DEFAULT NULL COMMENT '预留',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `comDefault` varchar(50) DEFAULT NULL COMMENT '默认',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='枚举表';

/*Data for the table `comenum` */

insert  into `comenum`(`uuid`,`comClass`,`code`,`name`,`word`,`sort`,`remark`,`comDefault`) values ('1','ShareInfo','imageUrl','http://wap.yesky.com/imagelist/09/06/2dv1jv662079.jpg',NULL,1,NULL,NULL),('16','ACCUSATION_PERSONAL','1','个人攻击','',1,NULL,NULL),('17','ACCUSATION_PERSONAL','2','个人骚扰','',2,NULL,NULL),('18','ACCUSATION_PERSONAL','3','其他','',3,NULL,NULL),('19','ACCUSATION_TOPIC','1','广告','',1,NULL,NULL),('2','ShareInfo','siteUrl','http://www.baidu.com',NULL,2,NULL,NULL),('20','ACCUSATION_TOPIC','2','色情/反动','',2,NULL,NULL),('21','ACCUSATION_TOPIC','3','其他','',3,NULL,NULL),('3','ShareInfo','text','禹恋,你的爱情记录专家',NULL,3,NULL,NULL),('4','ShareInfo','title','禹恋',NULL,4,NULL,NULL),('5','ShareInfo','titleUrl','http://www.baidu.com',NULL,5,NULL,NULL);

/*Table structure for table `experience` */

DROP TABLE IF EXISTS `experience`;

CREATE TABLE `experience` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `name` varchar(500) NOT NULL COMMENT '经历名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updaterId` char(36) DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态，10|当前/20|非当前/30|隐藏/',
  `recommend` decimal(2,0) DEFAULT NULL COMMENT '推荐',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经历表\r\n';

/*Data for the table `experience` */

insert  into `experience`(`uuid`,`name`,`remark`,`createrId`,`createDate`,`updaterId`,`updateDate`,`status`,`recommend`) values ('15106715-1490-43dd-8a64-19e2d699ee19','哈哈','','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-29 14:12:43',NULL,NULL,'10','0'),('152f95a9-7355-4d95-bad2-64d7a7620b0b','喜欢你，那双眼动人。','','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:27:14',NULL,NULL,'10',NULL),('1828602b-7666-4ba7-ab49-f58853b7bec2','禹恋','','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 13:56:44',NULL,NULL,'10',NULL),('704e647e-9fe9-490b-9467-4b3f944e4430','那些故事那么真切，那些记忆那么零碎','','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:26:01',NULL,NULL,'10','1'),('974eb6fe-e2f5-4955-932b-52d937a8e315','喔喔奶糖小时候的回忆。。','','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:45:31',NULL,NULL,'20',NULL),('ae7211e4-7e18-4c2e-8396-83a53e7dae4c','我的爱情','','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:49:47',NULL,NULL,'20','1'),('bcbc9602-0707-4d6e-9f98-543b3c3df847','爱情悄悄的到来','','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:04:16',NULL,NULL,'10','1'),('d6b1290b-643d-4271-807e-22049ad98d40','我的爱情','','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 14:07:53',NULL,NULL,'20','1');

/*Table structure for table `follow` */

DROP TABLE IF EXISTS `follow`;

CREATE TABLE `follow` (
  `uuId` char(36) NOT NULL COMMENT '(主键)',
  `userId` char(36) NOT NULL COMMENT '用户ID',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `status` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '状态，1|关注/0|取消关注',
  PRIMARY KEY (`uuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注表\r\n';

/*Data for the table `follow` */

insert  into `follow`(`uuId`,`userId`,`createrId`,`createDate`,`status`) values ('02ff34a8-d18a-4438-9df5-95fa83b9c4a5','619e1e94-693a-42ce-bb77-8b8d31423298','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 15:16:59','1'),('289237a9-0baf-4e0a-a0ee-0e7db58830ed','9bc73bd6-be69-4334-8391-f1d91ac82745','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:43:12','1'),('2f929a4d-f2bb-4b8e-b88c-6b868cfa9929','619e1e94-693a-42ce-bb77-8b8d31423298','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:53:57','1'),('3c7528ba-294f-437a-a70c-ccbfaba2410b','619e1e94-693a-42ce-bb77-8b8d31423298','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 15:28:25','1'),('41a6a471-f3dc-4427-9786-577ecba80888','9390b2d9-01f6-4809-9cd1-c4f812855001','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 15:18:11','1'),('4d9cf4b6-1ee7-4941-a215-096308e1d6eb','9bc73bd6-be69-4334-8391-f1d91ac82745','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 15:16:54','1'),('5aa88b10-ef54-40f4-81a0-bf951f811610','619e1e94-693a-42ce-bb77-8b8d31423298','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 15:08:55','0'),('63336cc8-033f-4ce7-8a20-2053cb5a9d1b','9390b2d9-01f6-4809-9cd1-c4f812855001','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:27:34','1'),('83935d1b-2451-4212-8642-1e0b25fa0a65','9bc73bd6-be69-4334-8391-f1d91ac82745','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 15:19:50','1'),('8d7669b4-4ea1-498a-bd20-0d3ca75215bf','9bc73bd6-be69-4334-8391-f1d91ac82745','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 14:29:33','1'),('904edb48-d284-4ae5-85db-2151264546f0','4651b11f-e8d9-4fba-b0cd-a5351eec89af','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:58:59','1'),('9942b7ce-79ff-47c3-b254-0e7a2d981e84','9390b2d9-01f6-4809-9cd1-c4f812855001','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:04:42','1'),('ae7608ad-e811-42e3-af4d-8f681af77d85','4651b11f-e8d9-4fba-b0cd-a5351eec89af','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:04:30','1'),('b40f058b-9484-4d3f-b4ec-90aebb827cc2','619e1e94-693a-42ce-bb77-8b8d31423298','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:43:08','1'),('b8811e09-e7c8-4bb1-a0b9-4ad39bacbe19','4651b11f-e8d9-4fba-b0cd-a5351eec89af','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:43:10','1'),('ed86d43c-0148-4844-92a2-8bc8b6aa7065','9390b2d9-01f6-4809-9cd1-c4f812855001','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 14:09:32','1'),('f1c00985-4d3d-467b-abcf-c6ef12211c06','4651b11f-e8d9-4fba-b0cd-a5351eec89af','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:48:16','1');

/*Table structure for table `levelname` */

DROP TABLE IF EXISTS `levelname`;

CREATE TABLE `levelname` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `start` int(12) NOT NULL COMMENT '起始积分',
  `end` int(12) DEFAULT NULL COMMENT '终止积分',
  `levelName` varchar(50) DEFAULT NULL COMMENT '积分称谓',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分称谓\r\n';

/*Data for the table `levelname` */

insert  into `levelname`(`uuid`,`start`,`end`,`levelName`) values ('1',0,100,'LV-1'),('10',5501,7000,'LV-10'),('11',7001,8500,'LV-11'),('12',8501,10000,'LV-12'),('13',10000,999999,NULL),('2',101,300,'LV-2'),('3',301,600,'LV-3'),('4',601,1000,'LV-4'),('5',1001,1500,'LV-5'),('6',1501,2400,'LV-6'),('7',2401,3000,'LV-7'),('8',3001,4000,'LV-8'),('9',4001,5500,'LV-9');

/*Table structure for table `media` */

DROP TABLE IF EXISTS `media`;

CREATE TABLE `media` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `fkId` char(36) NOT NULL COMMENT '关联ID\r\n            ',
  `path` varchar(500) NOT NULL COMMENT '路径',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `height` varchar(20) DEFAULT NULL COMMENT '图片高度',
  `width` varchar(20) DEFAULT NULL COMMENT '宽度',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='媒体索引表\r\n';

/*Data for the table `media` */

insert  into `media`(`uuid`,`fkId`,`path`,`content`,`height`,`width`) values ('0594a4e6-2c23-464a-9f7c-06b26d22bf57','dd71374e-f2ac-41f4-bf98-468ce769b02c','http://139.129.109.204:8080/upload/topic/20160701/373dc047-033f-42f2-b5eb-2bac554f74e7.jpg','','720','521'),('0e3fdbf1-8ccc-4c8d-a0b4-c6bf051cadba','576ee36c-6067-48f8-a6dd-ef6bed42e7bf','http://139.129.109.204:8080/upload/topic/20160701/540143f7-754a-4239-82f5-e4fd02b44d00.jpg','','505','808'),('128b63c6-8f84-4a2f-bc71-bad1910b9ae7','9601150e-18dd-44b6-b14c-7b052f82dd45','http://139.129.109.204:8080/upload/topic/20160629/b852d2ef-9133-4851-a420-7fe9f139c1ea.jpg','','1280','852'),('14c0702d-f437-4ac3-b1ef-581e0a246adc','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160630/905ab6fb-a496-4df3-8c21-8ee0ea946d03.jpg','禹恋','424','954'),('19a7b894-0c1a-45a0-b8d1-260623b630f9','345fc9ee-613d-4274-9e09-a57c0ce35fb2','http://139.129.109.204:8080/upload/topic/20160701/879d45a1-1148-45c2-8aa5-6a1d59550231.jpg','','2080','1560'),('1a191a48-e09b-4cf5-b621-88910ecd27cc','869918bd-0804-4dc4-9da4-d0a60ec47565','http://139.129.109.204:8080/upload/topic/20160629/cd5f4acd-65d1-4344-b9dd-7aee074105f9.jpg','','440','750'),('1bcd717b-5ca1-4581-80a8-0b5ddece20d0','e10f7b3e-1788-4a9f-80d4-4656299502ba','http://139.129.109.204:8080/upload/topic/20160630/36b2461d-a9c8-4ef5-9212-e2a1a0ca5ce8.jpg','','1042','1080'),('1e011f5b-93a1-4565-a9b1-6e515d1def01','15106715-1490-43dd-8a64-19e2d699ee19','http://139.129.109.204:8080/upload/topic/20160629/7ba32967-573c-409d-9fac-9ca99e525804.jpg','哈哈','416','935'),('209239db-9a61-42f8-a716-5d4903c6c6df','f060671a-b3b3-428f-a427-06450870075d','http://139.129.109.204:8080/upload/topic/20160629/75ebc497-f568-4ece-974e-6bbd816f383f.jpg','','454','700'),('22754170-9fab-4047-86d0-d22ce7b63621','4797b896-9807-46b9-ab9c-52d1829984b0','http://139.129.109.204:8080/upload/topic/20160629/c7773b4e-6a96-4653-9140-96701529e879.jpg','','483','700'),('293b8e6a-89c1-4df0-be80-535d08e82663','039d84b0-0175-4199-9e46-4eb3b9b48961','http://139.129.109.204:8080/upload/topic/20160629/da696542-8c77-418b-a31f-c295d5fff55c.jpg','','336','600'),('2dc147bb-e91b-4bf8-ab0c-f4ffb9f75125','039d84b0-0175-4199-9e46-4eb3b9b48961','http://139.129.109.204:8080/upload/topic/20160629/3297b063-4897-4b7b-975a-cedbb17f1667.jpg','','1019','870'),('312427f3-3e30-4adc-ad2b-0bcd98ed6961','3baceca8-b50c-48c9-af03-3aeecd6146af','http://139.129.109.204:8080/upload/topic/20160630/e55ca9d6-911b-4a3e-ba92-672c62e507ac.jpg','','2048','1365'),('317f1d79-dd23-45e0-9a30-3203998cbfbb','9bc73bd6-be69-4334-8391-f1d91ac82745','http://139.129.109.204:8080/upload/topic/20160701/911d08d5-9a7d-4e3c-88ef-ff728f7c468b.JPEG',NULL,'480','480'),('365203af-2c6b-4f8e-ba36-db1347b1aabd','2d23dac1-5ec6-49a8-880b-fa7833c2e56f','http://139.129.109.204:8080/upload/topic/20160630/2751d74f-513e-4e10-b7fd-15fd86ab8461.jpg','','600','471'),('3eb2b867-faba-4d6c-9526-3f06b438340e','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160629/6be409ba-5b01-42a9-b5e6-65bf16801f44.jpg','我的爱情','380','852'),('44259d0b-2cdf-4194-bd86-ade7059e5068','d9d22367-c002-4414-a684-e42a746cafbb','http://139.129.109.204:8080/upload/topic/20160701/7812487d-5f4d-4cf5-b883-39ab173d943d.jpg','','1296','728'),('4ad2ec6a-9d1c-4712-92cb-398e1b8ed647','619e1e94-693a-42ce-bb77-8b8d31423298','http://139.129.109.204:8080/upload/topic/20160629/f1ed13d3-4f0f-465c-9382-ebf631af2406.JPEG',NULL,'480','480'),('4d354d18-a285-4d77-9b47-74be3dcd6b0e','901d5f05-c660-410c-a6b3-5028751a2031','http://139.129.109.204:8080/upload/topic/20160630/ca95dc42-b5ac-41a0-94b3-2cd4eec18718.jpg','','540','960'),('538acae8-0b03-4521-8353-3e14ae8c3ced','e889986b-87b8-4096-aaff-35d443d4ab55','http://139.129.109.204:8080/upload/topic/20160629/99d90a29-53a7-484a-a425-9156ff4af2c0.jpg','','720','1280'),('577f3ea2-0694-406d-ad5b-4a162db9396b','bcbc9602-0707-4d6e-9f98-543b3c3df847','http://139.129.109.204:8080/upload/topic/20160629/778bb064-9508-4aef-af14-ee96e8f21a5c.jpg','爱情悄悄的到来','567','1276'),('5be0d680-ef20-460d-aa28-a5756db79fac','745cc58c-ee99-4371-a2ba-c9a14758613d','http://139.129.109.204:8080/upload/topic/20160630/87f210d1-5473-4853-b823-020672ce0845.jpg','','700','700'),('5d0d89b2-a519-4459-9e02-e3632ca08bf7','9390b2d9-01f6-4809-9cd1-c4f812855001','http://139.129.109.204:8080/upload/topic/20160630/1f9c7e9c-b31f-47e5-a492-8bc4fdfe49c6.JPEG',NULL,'480','480'),('5f1b096c-fc78-4fda-9041-b9289f7a994b','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/0977fdb1-22e9-4122-9fa9-c7dd88c8789c.jpg','','1920','1080'),('5fbc09f8-fae3-435e-a3b6-b5eba8566688','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/34c2ea62-a741-410b-a960-a790d2b97089.jpg','那些故事那么真切，那些记忆那么零碎','266','598'),('61001235-782d-4032-ac33-e983893f7be3','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/9fef02c1-6988-4fef-9139-d08c94f90f3f.jpg','','1920','1080'),('68c9a8b5-4381-4fa5-b797-a4912de7a47d','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/391e46b9-2105-49d2-981d-40e9fc6a5e24.jpg','','1920','1080'),('71738d01-4bfb-48a0-82a6-007f2669f79b','93f226f1-c043-4190-82a9-036a6f65af8c','http://139.129.109.204:8080/upload/topic/20160701/e5b79129-4c7e-4e30-9b84-a75ac7fc0541.jpg','','1200','800'),('753a7fa8-54b1-4e6e-8d6c-fd587f108a55','925a4d34-e177-4c1d-9e93-4eb34acee9a0','http://139.129.109.204:8080/upload/topic/20160630/d20e7905-f2df-4f3b-bdf7-453147383ade.jpg','','383','383'),('76a7b342-b6b8-40cf-849b-cc28cdd15bd6','a8a2dadf-4168-4c5f-a565-bf7b2583b519','http://139.129.109.204:8080/upload/topic/20160629/c38fbedb-ea8d-4b35-ad20-87c024173c60.jpg','','600','960'),('79fbeb3f-4c1b-44df-85a7-9e7e284bc2a4','1574af5d-34dc-42a6-93ff-2e7d935c685f','http://139.129.109.204:8080/upload/topic/20160701/dea94e7a-49b2-4509-959e-5bcb9c8d48ea.jpg','','1042','1080'),('80e9c67b-e53b-49a9-9e49-fa9cb5d8916d','974eb6fe-e2f5-4955-932b-52d937a8e315','http://139.129.109.204:8080/upload/topic/20160630/ab8b4d5c-20fe-4f60-994d-1cdb6d303735.jpg','喔喔奶糖小时候的回忆。。','499','1122'),('818e9df6-130a-45a8-816f-0c4d3b36aee7','2a6b154e-5975-4415-9ef9-435a9b382457','http://139.129.109.204:8080/upload/topic/20160630/61597447-f6d1-49d2-be31-ddf819594f89.jpg','','960','1280'),('85c8089d-1b75-4104-a115-4df0a9f5fb79','e10f7b3e-1788-4a9f-80d4-4656299502ba','http://139.129.109.204:8080/upload/topic/20160630/ab5e7662-a01f-4d9a-a8f0-2a14cd0be72d.jpg','','1042','1080'),('8cbf9b57-9ad3-4855-979c-573fe974e3e7','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/99763b60-4aba-4619-9f1c-6991eb08fb9c.jpg','','1921','1080'),('8ef921bd-0831-4cdc-8359-784af2d3fe91','8c8e9f72-35b3-41e4-8b3b-41ed8215e622','http://139.129.109.204:8080/upload/topic/20160630/d8761d4d-7d36-442b-a029-d2b09ffdfa5c.jpg','','223','270'),('947595ed-d473-4bfd-9ef6-1eab9f00deab','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','http://139.129.109.204:8080/upload/topic/20160630/4bfcc9ac-09c2-4672-b1e8-15b5cb9b5aa7.jpg','','366','527'),('967072d6-01ca-458e-8080-5cf9e8d39c66','5c6e8194-4b48-4237-8ac1-1d8846b6607f','http://139.129.109.204:8080/upload/topic/20160701/a0398094-acf4-40bd-9cd3-c422bcbfda20.JPEG',NULL,'480','480'),('9ccb5de3-481c-40d9-8d22-d5f4dc6169d7','4845d351-36e0-4cb9-b7c6-b5132237bdf3','http://139.129.109.204:8080/upload/topic/20160701/d21bb6db-44db-482a-8096-3b7209727362.jpg','','375','500'),('9d03d1f5-7e0e-4421-81ab-29806ff99c4c','24273ace-0b12-4253-94b9-a900110e54c7','http://139.129.109.204:8080/upload/topic/20160630/54cd3c0d-c014-4f9d-9286-4286ac321758.jpg','','960','1280'),('9eb554d1-2bef-4b9d-be21-e3ca4ff2fee2','22bdfd89-c086-4b9c-bf8c-51a6d109d631','http://139.129.109.204:8080/upload/topic/20160630/12e1b4b1-8252-4a80-b1e3-eb4c34f6eb48.jpg','','480','720'),('9f41bf92-2715-4947-a128-ea8674bf6928','1fb65db8-f323-4946-9d00-f5286bef158a','http://139.129.109.204:8080/upload/topic/20160630/fe7c8ebb-3fd8-470b-816a-66f664343bc8.jpg','','960','1280'),('9f51d3cc-0c07-4b2a-b954-34a20aaaa05f','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/3a682471-2e63-4a05-bca0-10709ef5975b.jpg','','1920','1080'),('a05b9d7e-69ae-4e83-8190-1ed99177c667','93f226f1-c043-4190-82a9-036a6f65af8c','http://139.129.109.204:8080/upload/topic/20160701/baf569a5-8d25-4a25-bbd6-36f6788564db.jpg','','1200','800'),('a3f86e1d-3954-4398-832a-68b08401b7ce','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','http://139.129.109.204:8080/upload/topic/20160629/997ed46c-2441-4512-9038-16937e3610ed.jpg','','780','1040'),('a4ca3fca-a766-42eb-ad1f-36a0058b3806','e799c923-d9ae-460b-9e64-504259a63540','http://139.129.109.204:8080/upload/topic/20160630/870f03fb-686b-40c1-98a9-883315b077c6.jpg','','1920','1080'),('a60d9cca-d661-4eac-a3d9-d393b27d6635','bed53bac-9ed5-4a8b-bf96-24d9255e03fa','http://139.129.109.204:8080/upload/topic/20160630/0de5bfb8-a329-4f6a-8ea6-efa04abb439c.jpg','','525','840'),('b31f459b-a07f-4188-8645-8017989cc93d','f2064b89-8e7b-4ffb-9417-cb9a89ecc71a','http://139.129.109.204:8080/upload/topic/20160630/de31149c-023d-4aca-9883-75a387320d37.jpg','','1200','800'),('bb0866f5-9901-4bac-bcf3-9beaeb1c3850','4651b11f-e8d9-4fba-b0cd-a5351eec89af','http://139.129.109.204:8080/upload/topic/20160629/131b3fdf-5088-4da9-9c9f-864e6a16305f.JPEG',NULL,'109','110'),('beca48b1-a898-4706-b9f1-b17f47d2d750','d0f784f9-8f63-4239-b98a-606abafe5451','http://139.129.109.204:8080/upload/topic/20160701/6453ca8a-f329-4438-83f9-b8d22ab0dfff.jpg','','896','600'),('c1209d6c-fae2-44a4-9e42-466215d8a28f','17a606e7-5d4d-4a05-82f9-bfd8e76fca24','http://139.129.109.204:8080/upload/topic/20160629/71332455-12d2-4e2f-857e-9f545b71d96e.jpg','我的小猫咪','450','1013'),('c1d59a67-e39f-4e8a-bbfd-dd49223f2bdf','5c0df389-6341-4d13-aa24-fda0aede1ac6','http://139.129.109.204:8080/upload/topic/20160630/b15a7a19-8dc5-4b52-92eb-23705d87e860.jpg','','2048','1365'),('c2539495-d1bd-4c8c-a65e-b93fa3f42d53','b9373510-6ef4-4a56-a091-ab55cb22c1e5','http://139.129.109.204:8080/upload/topic/20160630/96f71338-9940-47f6-936c-33c827a4f3d6.jpg','','1550','863'),('c5880802-cd04-4034-bc4d-3d24f6089e0f','f26e99be-0ded-44b6-915b-5300ab82d908','http://139.129.109.204:8080/upload/topic/20160630/681bb891-257a-4938-b5fd-2e7e0d2ed153.jpg','','1921','1080'),('c773250b-7ef1-43d1-be8f-4ddd82acafad','26aa208e-d850-4b8a-b3f7-f75a5894b88e','http://139.129.109.204:8080/upload/topic/20160629/641a95f5-5bd8-467e-bbc5-19cf2a8d5ee6.jpg','么么哒哈哈','447','1004'),('cb5c6e08-1568-4af9-9113-9d753418e47a','8efe838c-eba1-45d8-ac0c-1fdc6f8dbf37','http://139.129.109.204:8080/upload/topic/20160630/bcd46e59-07d9-4cac-9455-77d371395bb5.jpg','','1042','1080'),('cf062eae-ea83-48e6-91ca-8ce08f6d7c4f','98a24ad9-74b7-404b-8e0b-aeaf8dad8a46','http://139.129.109.204:8080/upload/topic/20160701/8df16c9e-46db-4414-aa2c-02c78415f078.jpg','','1392','960'),('d0a9e9bb-b053-4b12-a00f-73e85695e706','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/3adf6f86-858c-4d93-b9d2-7d6edc17d538.jpg','喜欢你，那双眼动人。','286','640'),('d45285ec-9224-4a60-a504-f45069618207','66c3f687-78dc-49a9-94a3-e1253f6f6d89','http://139.129.109.204:8080/upload/topic/20160701/9a1a2640-fd3f-4e79-a1d0-8a915bf5ba67.jpg','今天和我亲爱的～～～去吃大餐。。。','451','1015'),('d7bb5bf8-a55b-4079-b6bb-7ca2a89a1941','3e3feb8c-b57c-44de-a48e-7eed1b63cdb9','http://139.129.109.204:8080/upload/topic/20160629/ac40f381-a9e7-41dc-adcf-89ebbae8c9ef.jpg','','1280','1024'),('deea56e3-6eb3-4e2c-9e15-8d220ce8f5a7','37298065-56af-48da-a3c7-8746f3959f3b','http://139.129.109.204:8080/upload/topic/20160630/91a04833-9892-4e6f-89b9-498036dc9725.jpg','','722','1080'),('e1b7b614-7306-48a2-86ac-a4c623fdeddb','bb583764-f53b-4da0-a9b3-8d8ad923258d','http://139.129.109.204:8080/upload/topic/20160629/13a033be-afd0-49e3-a390-aea797c0899a.jpg','','768','1024'),('e387e1f9-8b56-4e77-ac9b-7d95c2de1e28','76438854-5fbf-404a-846d-a8a16d9e7888','http://139.129.109.204:8080/upload/topic/20160630/b9e6d458-79db-4ab4-9151-e6d495d58646.jpg','','1500','1000'),('e4659c05-8f37-4459-a9be-7d5e3988d544','d6b1290b-643d-4271-807e-22049ad98d40','http://139.129.109.204:8080/upload/topic/20160629/a3a8f285-67cc-452a-9cfc-f16c342afc81.jpg','我的爱情','220','493'),('e4e7fcd1-73d3-4e1c-864b-2ff7ec5c736d','951054de-707d-457c-afe7-b8e52b9f9481','http://139.129.109.204:8080/upload/topic/20160629/2af92905-df27-42ba-91b7-6e2ccfdcbd0b.jpg','','450','720'),('ebd44955-00ef-4d76-be0b-c4b05cbdae75','76db3d13-e0f5-4b58-81b8-c6e3ee63573c','http://139.129.109.204:8080/upload/topic/20160630/c97a3c42-aecc-4cfc-a9b7-28441affeec3.jpg','','2048','1365'),('ef6fb756-07dd-464c-92d1-6886f696ff73','f302ea8c-9baf-4e90-ac6f-c5f8678ce178','http://139.129.109.204:8080/upload/topic/20160629/85fbcf88-343a-457d-bf60-f7e69322168e.jpg','','1800','1200'),('f04eebc9-d6c4-45ad-9183-68d528fa0088','db77eb24-f331-4c08-bbff-32535c045896','http://139.129.109.204:8080/upload/topic/20160630/4d615516-b5ab-48bb-85fb-661328b461fc.jpg','','1200','800'),('f2ac6461-a9a3-4396-96f4-c95ae960e771','22bdfd89-c086-4b9c-bf8c-51a6d109d631','http://139.129.109.204:8080/upload/topic/20160630/d7c17825-6305-41de-bb6d-1acbc472cff5.jpg','','480','720'),('f2bb4ed2-8a6a-4a0b-929b-f71847775fdb','4ac89693-08a0-43f7-8f85-488ed9d0e966','http://139.129.109.204:8080/upload/topic/20160629/d6522b4f-322e-4b4c-9f4d-c1e742237e15.jpg','','720','1280'),('f34a719d-b272-4057-b4e6-462867c196ef','22bdfd89-c086-4b9c-bf8c-51a6d109d631','http://139.129.109.204:8080/upload/topic/20160630/217221b1-c685-4f26-b764-6b49498f7bca.jpg','','447','984'),('f36d991e-d3f5-4a14-9ff7-439702f78bfb','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','http://139.129.109.204:8080/upload/topic/20160629/58328cb4-c854-445c-8d96-81f1258e5678.jpg','','1500','1000'),('f8a37fea-fcb6-4d85-a2c2-0aa4a04815b1','f3367190-8e15-44b7-920b-50cedc70c2d3','http://139.129.109.204:8080/upload/topic/20160629/fbc96f6a-6cc5-48fe-a466-86b860bd551b.jpg','','768','1366'),('f8c40a0a-4e88-4c5f-a7fd-3a5e7c48eeca','611d5541-b33f-473d-a931-7738c6b2e6e0','http://139.129.109.204:8080/upload/topic/20160630/293b0698-7927-455f-abc8-5984f39f2de7.jpg','','140','140'),('fc705f1b-2585-4c6d-84f5-a066b06fc025','e3737a8b-f761-4bbc-a569-72285ec8095c','http://139.129.109.204:8080/upload/topic/20160630/392df19f-802b-4517-a3cc-0e2b44f7cca2.jpg','','480','720');

/*Table structure for table `mobilevalidate` */

DROP TABLE IF EXISTS `mobilevalidate`;

CREATE TABLE `mobilevalidate` (
  `mobile` varchar(12) NOT NULL COMMENT '手机号',
  `validateId` varchar(20) NOT NULL COMMENT '验证ID',
  PRIMARY KEY (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证\r\n';

/*Data for the table `mobilevalidate` */

insert  into `mobilevalidate`(`mobile`,`validateId`) values ('13500780439','3729'),('18842878603','8230');

/*Table structure for table `praise` */

DROP TABLE IF EXISTS `praise`;

CREATE TABLE `praise` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `topId` char(36) NOT NULL COMMENT '主帖ID\r\n            ',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updaterId` char(36) DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态，10|启用/20|停用/30|删除/',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表\r\n';

/*Data for the table `praise` */

insert  into `praise`(`uuid`,`topId`,`createrId`,`createDate`,`updaterId`,`updateDate`,`status`) values ('0220a87f-9888-4a87-bef6-e0a534a01f0b','f3367190-8e15-44b7-920b-50cedc70c2d3','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:11',NULL,NULL,'0'),('02c00b87-ce64-43ca-9a26-55837d570946','37298065-56af-48da-a3c7-8746f3959f3b','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:19:27',NULL,NULL,'0'),('09bef152-b01b-456e-9538-d07eb90d97b8','7d4378e2-41b0-4b6f-b903-84cb7e4d58fa','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 15:21:21',NULL,NULL,'0'),('1421df04-b9ae-4e14-9c0d-4d40b809ebf7','9601150e-18dd-44b6-b14c-7b052f82dd45','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:10:45',NULL,NULL,'0'),('1730e258-0ac1-43fd-8c22-0351cbf7ff18','e889986b-87b8-4096-aaff-35d443d4ab55','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 17:41:42',NULL,NULL,'0'),('1805887d-bcd4-48b1-87f7-aacd2ba80ed7','039d84b0-0175-4199-9e46-4eb3b9b48961','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:42:47',NULL,NULL,'0'),('183b7e00-6e88-4453-8da2-eeea93e45405','e889986b-87b8-4096-aaff-35d443d4ab55','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 17:41:42',NULL,NULL,'0'),('288f62f2-219e-4f88-91b1-815ae2ceb621','611d5541-b33f-473d-a931-7738c6b2e6e0','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:16:34',NULL,NULL,'0'),('28ab4ef5-cf82-4756-819d-ca1194366c88','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:09:16',NULL,NULL,'0'),('2c8fbbb5-a85f-4ee7-a836-3f8c947713a2','039d84b0-0175-4199-9e46-4eb3b9b48961','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:41',NULL,NULL,'0'),('2d582ae7-a685-4230-b41e-a8bd63176e91','8c8e9f72-35b3-41e4-8b3b-41ed8215e622','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 11:23:48',NULL,NULL,'0'),('31903897-af56-4e77-a487-a547118e759f','869918bd-0804-4dc4-9da4-d0a60ec47565','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:09:30',NULL,NULL,'0'),('3493b6f0-9236-4cdc-9cb8-82592155b5a6','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:08:50',NULL,NULL,'0'),('37678893-dc64-4a79-8585-bc33f1301253','901d5f05-c660-410c-a6b3-5028751a2031','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 16:56:39',NULL,NULL,'0'),('3a13a000-995b-4151-9ad7-f5640bf09509','f3367190-8e15-44b7-920b-50cedc70c2d3','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:24:36',NULL,NULL,'0'),('4157604d-6079-4e28-8402-3417595c126e','869918bd-0804-4dc4-9da4-d0a60ec47565','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:35:27',NULL,NULL,'0'),('46141b16-32e6-44ba-bea1-4fa70c680dc6','98a24ad9-74b7-404b-8e0b-aeaf8dad8a46','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:21:25',NULL,NULL,'0'),('465cadd7-d8ae-4e18-b33e-b33041475fa1','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 09:06:02',NULL,NULL,'0'),('48820bd0-ff0f-4179-a653-9aa20461fffb','bb583764-f53b-4da0-a9b3-8d8ad923258d','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:25:16',NULL,NULL,'0'),('49ffef37-6cc6-43d5-aaf9-99a1a1110b72','d9d22367-c002-4414-a684-e42a746cafbb','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 13:43:09',NULL,NULL,'0'),('4b33158f-5fee-4a4c-9f7e-783d81ee76d3','925a4d34-e177-4c1d-9e93-4eb34acee9a0','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:13:43',NULL,NULL,'0'),('4bfdf3a8-2cbd-4969-87b3-ac451ca86e56','76438854-5fbf-404a-846d-a8a16d9e7888','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:19:59',NULL,NULL,'0'),('4fb5b464-0264-4575-9c93-e4472b69c8ea','951054de-707d-457c-afe7-b8e52b9f9481','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:35:45',NULL,NULL,'0'),('51c8592e-7b20-4639-a399-1371700c3f04','f060671a-b3b3-428f-a427-06450870075d','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:19',NULL,NULL,'0'),('5571d207-22ee-4d0b-b861-9bbcab1ba989','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 09:06:42',NULL,NULL,'0'),('57de7c2c-93b0-4882-9a82-72c1d99d8bd7','4ac89693-08a0-43f7-8f85-488ed9d0e966','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 11:06:06',NULL,NULL,'0'),('5b72a54d-5a1d-4e93-b954-65b7eee48bfb','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 15:14:58',NULL,NULL,'0'),('5d0e04b4-5888-4379-9286-06e555364b08','f302ea8c-9baf-4e90-ac6f-c5f8678ce178','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 11:05:59',NULL,NULL,'0'),('5dd32a81-e14e-4fea-a571-c10da88e77cb','bed53bac-9ed5-4a8b-bf96-24d9255e03fa','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:18:01',NULL,NULL,'0'),('5e39c7af-f809-40c6-914d-19ca083d74f5','56e458bf-08a1-419c-b218-363d7791fbd0','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:16:43',NULL,NULL,'0'),('608818f7-402e-437b-9a93-ea71f2773668','76438854-5fbf-404a-846d-a8a16d9e7888','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 10:19:24',NULL,NULL,'0'),('60ac8652-33e6-44bd-9135-94a13d2326b9','8c8e9f72-35b3-41e4-8b3b-41ed8215e622','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:18',NULL,NULL,'0'),('71579cf0-c476-42d6-bbd8-6ff1b1d5aeb5','f060671a-b3b3-428f-a427-06450870075d','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:10:05',NULL,NULL,'0'),('733d15bb-78ac-422f-a628-0e100c756171','4797b896-9807-46b9-ab9c-52d1829984b0','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 16:59:53',NULL,NULL,'0'),('7472b7bb-c38d-4d8c-8e17-e6774c77a7be','bb583764-f53b-4da0-a9b3-8d8ad923258d','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:22:05',NULL,NULL,'0'),('7a98107e-9d96-44a8-a44d-1430c4bf016f','3e3feb8c-b57c-44de-a48e-7eed1b63cdb9','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 10:19:29',NULL,NULL,'0'),('7bb79147-b26c-4f15-a8b5-809ba2a6320f','7d4378e2-41b0-4b6f-b903-84cb7e4d58fa','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:50:55',NULL,NULL,'0'),('7d262e06-5a30-48ea-b8b7-d9122e909c28','b9373510-6ef4-4a56-a091-ab55cb22c1e5','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:19:29',NULL,NULL,'0'),('86f46361-afe9-41b1-b7ef-1a3f4be3c2ad','f060671a-b3b3-428f-a427-06450870075d','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:25:03',NULL,NULL,'0'),('8851f378-7441-479e-bb6c-dd0202e6ad1c','56e458bf-08a1-419c-b218-363d7791fbd0','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:21:50',NULL,NULL,'0'),('890c4b26-565b-40af-9f48-05a79df44ff0','e889986b-87b8-4096-aaff-35d443d4ab55','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:06',NULL,NULL,'0'),('89a2b178-678e-478e-9502-9fe423c06ff5','a8a2dadf-4168-4c5f-a565-bf7b2583b519','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:15',NULL,NULL,'0'),('950c0155-2ad1-4906-882f-89c753505492','925a4d34-e177-4c1d-9e93-4eb34acee9a0','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 10:19:19',NULL,NULL,'0'),('a7af455d-e3b5-4e72-8b6f-1a0abf29a4f8','76438854-5fbf-404a-846d-a8a16d9e7888','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:23:42',NULL,NULL,'0'),('a8668294-570f-49f7-987e-f2273251c74a','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:07:11',NULL,NULL,'0'),('ad287b0b-2276-4690-92d9-c954c6b2d997','925a4d34-e177-4c1d-9e93-4eb34acee9a0','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:13:43',NULL,NULL,'0'),('b1a883fe-d810-4730-a63c-a71d7b1d5462','4797b896-9807-46b9-ab9c-52d1829984b0','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:35',NULL,NULL,'0'),('b4fd0b9c-4281-47f2-8d61-5539fe52c221','a8a2dadf-4168-4c5f-a565-bf7b2583b519','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:07:24',NULL,NULL,'0'),('bc448d0c-299e-4e5d-9787-dbffb466cefa','869918bd-0804-4dc4-9da4-d0a60ec47565','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:29',NULL,NULL,'0'),('bcefb9f2-a8a8-4a52-ac43-276176cb3d39','76438854-5fbf-404a-846d-a8a16d9e7888','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:23:42',NULL,NULL,'0'),('bf9a8c17-ec32-4815-89e5-f3886e78709d','d9d22367-c002-4414-a684-e42a746cafbb','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 13:43:59',NULL,NULL,'0'),('c25cd2c7-aaf3-42bf-8d5f-08b92fc8451b','9601150e-18dd-44b6-b14c-7b052f82dd45','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:46:25',NULL,NULL,'0'),('c3b4bffb-6599-45e1-a79e-460ba9779b9f','3171db3b-8f34-47e0-b1f6-527582a0c99b','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:16:20',NULL,NULL,'0'),('c52d4b51-437b-4766-8465-ad1801485d6c','4797b896-9807-46b9-ab9c-52d1829984b0','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:35:35',NULL,NULL,'0'),('c6aa1ac0-215c-4ab1-b6c5-fd5513ffc05a','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:45:55',NULL,NULL,'0'),('c8130287-2f94-4df9-a631-0d7233eb323a','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:49',NULL,NULL,'0'),('ca5b3731-6539-4d49-b0ab-c35de5442bd3','8d6693d2-d953-4894-98aa-c6fc40da29dc','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 10:19:58',NULL,NULL,'1'),('cce9e958-33a3-4c71-b286-43dafbfdbe7b','925a4d34-e177-4c1d-9e93-4eb34acee9a0','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:13:44',NULL,NULL,'0'),('d4c6ea77-4305-4cda-b1e7-9334cffb82bb','901d5f05-c660-410c-a6b3-5028751a2031','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 14:19:00',NULL,NULL,'0'),('ddc8e423-616b-45a1-b7db-099dcf1f61a3','bb583764-f53b-4da0-a9b3-8d8ad923258d','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:51:23',NULL,NULL,'0'),('e509565f-9590-4e3b-89e6-25b8e8a6a520','901d5f05-c660-410c-a6b3-5028751a2031','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:18:15',NULL,NULL,'0'),('e699b0a1-9804-4cd8-8fca-0cff19791253','0f7295ec-5e7e-45aa-aa5f-77ef0b0249e9','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 10:19:33',NULL,NULL,'1'),('e7ffe982-6a79-4f68-9511-f6545697c678','dd71374e-f2ac-41f4-bf98-468ce769b02c','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:21:33',NULL,NULL,'0'),('ebeedbb3-6935-40e7-acda-483e2691b958','e889986b-87b8-4096-aaff-35d443d4ab55','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 17:41:43',NULL,NULL,'0'),('f3315b43-74c4-49fd-a1f3-89b147ec52c3','951054de-707d-457c-afe7-b8e52b9f9481','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:50:59',NULL,NULL,'0'),('f9e5326c-63c0-4836-b44d-8bd76098bc2b','869918bd-0804-4dc4-9da4-d0a60ec47565','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:35:28',NULL,NULL,'0');

/*Table structure for table `recommen` */

DROP TABLE IF EXISTS `recommen`;

CREATE TABLE `recommen` (
  `uuid` char(36) NOT NULL COMMENT '主键Id\r\n',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面',
  `content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `title` varchar(1000) DEFAULT NULL COMMENT '标题',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主帖表\r\n';

/*Data for the table `recommen` */

insert  into `recommen`(`uuid`,`cover`,`content`,`title`,`createDate`) values ('0f7295ec-5e7e-45aa-aa5f-77ef0b0249e9','http://139.129.109.204:8080/upload/recommen/second.jpg','		健身达人认为,其实锻炼随时随地都可以进行,办公室里也能健身。黄海平建议,上班一族可按如下方法进行办公室健身,下面一起跟着健身达人学习一下如何在办公室健身吧。|1、逆腹式呼吸法。取站、坐姿均可。慢慢用鼻腔吸气,同时腹部内收,胸部上提,横隔膜下移,肺部吸满了空气之后再徐缓吐气,呼气时腹部慢慢鼓起,渐渐恢复到原来的状态。每吸呼一次约需7-9秒,每次练习5-8分钟。|2、举臂伸展。两腿自然开立,两臂伸直向上举起后摆,挺胸塌腰,深吸一口气,使肩、背、腰部肌肉拉长,静止状态坚持3-5秒,然后上体慢慢直立再呼气。身体感到疲劳时,离开座椅,伸展几次,顿觉身体轻松、舒展。|3、利用办公桌为锻炼设施。双手撑扶于桌边,两腿并拢伸直,整个身体与桌面形成一个斜角(根据自己的力量来掌握身体与桌面的倾斜角度)；然后,双臂屈肘使身体下降,全身的重量压在双臂上,再用两臂的力量把身体撑起,连续撑15-20次。|4、推墙。距墙壁40-50厘米站立,两腿开立与肩同宽,身体向前倾倒,两臂屈肘双手贴扶于墙上,然后双手用力推墙,将身体直立撑起恢复原位,推15-20次。','身体力行','2016-06-08 16:07:01'),('8d6693d2-d953-4894-98aa-c6fc40da29dc','http://139.129.109.204:8080/upload/recommen/first.jpg','一、拥有自信和风度男人到了二十几岁后,就要开始学着用心去经营自己了,它体现在自己的思想与涵养上。自信是一个男人最重要的品质,自信的男人就你像一只在暴风雨中战斗的海鸥。海鸥所要说的只有一句话“让暴风雨来的再猛烈些吧”,只因为它无所畏惧。一个自信的男人,总是能够感染别人,无论这些人是朋友还是敌人。要使别人对你有信心,就必须要先对自己充满信心。自信的男人可以战胜一切困难。一个有风度的男人就像一片大海,不拒点滴,又包容江河。有风度使男人得到更多的青睐,不争眼前才能够放眼世界,给予别人才能够受益无穷。正所谓“宰相肚里能撑船”,一个心如大海的男人,肚中不知能撑多少船呀！风度偏偏让男人看上去潇洒万千。|二、养成看书和写作的习惯男人到了二十几岁后,就开始要走入社会了,在与别人交往的过程中,谈吐与修养是最能征服别人的。一个有知识的男人一定是常看书的,一个有智慧的男人一定是常写作的。无论自己多忙,都要抽出时间来看看书,写写文章。因为这样做能够改变一个男人的思想与行为。一个男人要改变自己思想首先要做的就是读一本好书,读一本书就像交了一个好朋友,他能够帮助你走好自己的路。读书的生活是最丰富多彩的,写作的时光是最能启迪智慧的。喜欢看书和写作的男人,一定能够培养出一个好的心态。因为知识与智慧的海洋是无边无际的,但喜欢看书和写作的男人却能做到执著追求。追求是一个男人的思想,也是一个男人的行动,永不放弃地追求,无时不刻的在激励的男人去战斗。在这种战斗中,使一个男人能够经历风雨的洗礼,成长为一棵参天大树。读书使男人变得的冷静,写作使男人变得成熟。|三、要试着发现生活里的真、善、美男人到了二十几岁后,就要学会如何去面对生活。什么是“真”,现在的男人越来越不懂,那是因为现在的男人都很浮躁,他们不懂什么才是“真心”地去生活。“真”,就是对自己实事求是,不要骗自己,也不要骗别人。“真”,就是诚实做人,诚实做事,诚实的男人最可爱。“善”,自然是善良的意思了。善待别人,就是在善待自己的生活。“善”其实就在我们每一个人的身边,不要为难别人,不要挖苦别人,不要侮辱别人,就是善良的行为。有时你的一点点善意就能结出一个善果,使你的生活因此而变得幸福。','男神的修养','2016-06-08 15:05:55');

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `topId` char(36) NOT NULL COMMENT '主帖ID\r\n            ',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `toUserId` char(36) DEFAULT NULL COMMENT '对谁回复',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updaterId` char(36) DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态，10|启用/20|停用/30|删除/',
  `partentId` char(36) DEFAULT NULL COMMENT '回复的评论id',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回帖表\r\n';

/*Data for the table `reply` */

insert  into `reply`(`uuid`,`topId`,`content`,`toUserId`,`createrId`,`createDate`,`updaterId`,`updateDate`,`status`,`partentId`) values ('033b7cfa-82bb-4770-bcac-688a73597ad9','76438854-5fbf-404a-846d-a8a16d9e7888','这是谁',NULL,'4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 13:31:54',NULL,NULL,'10',NULL),('04d55bdd-a2ae-422d-9705-d6f5f8f8f7e3','9601150e-18dd-44b6-b14c-7b052f82dd45','哈哈哈哈哈哈',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:46:17',NULL,NULL,'10',NULL),('050b358f-581a-4f4c-8f82-99e1a9255a39','d9d22367-c002-4414-a684-e42a746cafbb','聊聊','15140374302','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:14:35',NULL,NULL,'10','e1e7933d-abff-4979-a830-ab7fc1bfb0d6'),('0eb5c049-6c33-490e-a51f-932edef95ff7','bb583764-f53b-4da0-a9b3-8d8ad923258d','(.ω.)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:18:38',NULL,NULL,'10',NULL),('1232b9a7-343e-40ba-8c72-5e94f4b5e7d8','9601150e-18dd-44b6-b14c-7b052f82dd45','哈哈哈哈哈哈',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:46:20',NULL,NULL,'10',NULL),('1614e915-5576-4b57-b893-0a49e41d35c0','951054de-707d-457c-afe7-b8e52b9f9481','(๑`灬´๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:02',NULL,NULL,'10',NULL),('16de354e-5244-43e2-b2c6-2ab7cc73e97b','e10f7b3e-1788-4a9f-80d4-4656299502ba','小编色',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:09:18',NULL,NULL,'10',NULL),('1dc499bc-9e8c-431b-ab9f-f70a4a2d3f33','611d5541-b33f-473d-a931-7738c6b2e6e0','๛ก(ｰ̀ωｰ́ก)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:16:38',NULL,NULL,'10',NULL),('2d3536fa-2bd5-4545-b0d5-e22372d86887','4ac89693-08a0-43f7-8f85-488ed9d0e966','(๑`灬´๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:19:22',NULL,NULL,'10',NULL),('4166671d-9ca4-4772-927f-57d63682ef5d','a8a2dadf-4168-4c5f-a565-bf7b2583b519','三三ᕕ( ⌓̈ )ᕗ',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:55:00',NULL,NULL,'10',NULL),('4474ce2c-08f1-4490-badc-7d0e2748f4fc','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','诶呦喂',NULL,'9390b2d9-01f6-4809-9cd1-c4f812855001','2016-06-30 13:45:59',NULL,NULL,'10',NULL),('4531413f-2750-4544-8692-2548a5d36bf2','d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','(๑•̀ㅁ•́ฅ)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:53',NULL,NULL,'10',NULL),('4dd975b1-7df0-4c73-b6a5-88b5ffdb5f3b','9601150e-18dd-44b6-b14c-7b052f82dd45','哈哈哈哈哈哈',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:46:20',NULL,NULL,'10',NULL),('4e2127d3-1bbe-452e-8a37-c5529f5b5099','e889986b-87b8-4096-aaff-35d443d4ab55','(⇀ㅁ↼‶)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:54:40',NULL,NULL,'10',NULL),('4ef5d820-939f-4296-9530-c6eb7233cf1f','d9d22367-c002-4414-a684-e42a746cafbb','可能会吧','15941137503','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 17:15:31',NULL,NULL,'10','e1e7933d-abff-4979-a830-ab7fc1bfb0d6'),('603ad787-6b6e-47ba-a22d-f36508f0f404','039d84b0-0175-4199-9e46-4eb3b9b48961','哈哈','15140374302','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 11:20:56',NULL,NULL,'10','c18c3c66-ed81-4521-837d-dd77131f9a3b'),('79565b63-156f-4c37-b11c-5560d369cc5d','f3367190-8e15-44b7-920b-50cedc70c2d3','(⇀ㅁ↼‶)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:54:46',NULL,NULL,'10',NULL),('80de5d5f-bb27-4480-886b-2690364de3dc','039d84b0-0175-4199-9e46-4eb3b9b48961','你的文字不错',NULL,'5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01 17:13:51',NULL,NULL,'10',NULL),('81250afa-af43-43ee-b8fa-cc9c35c72896','869918bd-0804-4dc4-9da4-d0a60ec47565','ε=٩(●❛ö❛)۶',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:55:25',NULL,NULL,'10',NULL),('879abf04-10fd-4d7d-91ce-8f8d7110e4aa','4797b896-9807-46b9-ab9c-52d1829984b0','(〃\'▽\'〃) (´ ❥ `)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:55:57',NULL,NULL,'10',NULL),('9db672d4-c3d2-4a29-84a0-fe1d6be8df03','d9d22367-c002-4414-a684-e42a746cafbb','哈哈哈',NULL,'619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 17:13:38',NULL,NULL,'10',NULL),('9e88caab-24df-4929-951d-4f3b50466499','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','(●⁰౪⁰●)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:53:37',NULL,NULL,'10',NULL),('a4aa2dd7-85ce-49f7-8cf2-53cae037909b','76438854-5fbf-404a-846d-a8a16d9e7888','我男票๛ก(ｰ̀ωｰ́ก)','13500780439','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:20:09',NULL,NULL,'10','033b7cfa-82bb-4770-bcac-688a73597ad9'),('a50b277c-1486-4e2a-9a59-c7fb5144fa41','e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','不喜欢这个图片',NULL,'619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:09:17',NULL,NULL,'10',NULL),('a7d4b0c1-fba0-410e-971f-3b69eee10b85','56e458bf-08a1-419c-b218-363d7791fbd0','(¬_¬ )',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:52:52',NULL,NULL,'10',NULL),('aaf1f4d9-f97a-46cf-8420-b4cc7191f3c3','f3367190-8e15-44b7-920b-50cedc70c2d3','(⇀ㅁ↼‶)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:54:49',NULL,NULL,'10',NULL),('af20ba28-1f09-46ba-98e6-5f2e21cc5f83','bed53bac-9ed5-4a8b-bf96-24d9255e03fa','(๑•́ ₃ •̀๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:18:05',NULL,NULL,'10',NULL),('b6e0f07e-3149-40f2-9692-d33b49b95858','901d5f05-c660-410c-a6b3-5028751a2031','( 。ớ ₃ờ)ھ',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:18:21',NULL,NULL,'10',NULL),('bba5c069-991a-4796-bcab-ab5bc2fe80f3','3171db3b-8f34-47e0-b1f6-527582a0c99b','(๑′°︿°๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:16:30',NULL,NULL,'10',NULL),('c18c3c66-ed81-4521-837d-dd77131f9a3b','039d84b0-0175-4199-9e46-4eb3b9b48961','( •̩̩̩̩д•̩̩̩̩ ) “汪”的一声哭了',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:56:23',NULL,NULL,'10',NULL),('d11ff3c3-e609-4d86-b292-1701d36531e1','df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','(●⁰౪⁰●)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:53:40',NULL,NULL,'10',NULL),('d6f46987-d20f-47f1-bd55-251be3f1d093','f060671a-b3b3-428f-a427-06450870075d','٩(๑`н´๑)۶',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:55:08',NULL,NULL,'10',NULL),('e167da6d-0d2f-4a73-8d9f-92b3aff4d764','8c8e9f72-35b3-41e4-8b3b-41ed8215e622','(๑•̀ω•́๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:46',NULL,NULL,'10',NULL),('e1e7933d-abff-4979-a830-ab7fc1bfb0d6','d9d22367-c002-4414-a684-e42a746cafbb','美女(๑•́ ₃ •̀๑)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 13:43:30',NULL,NULL,'10',NULL),('e9facea3-b32a-4e46-b331-60139958de96','d9d22367-c002-4414-a684-e42a746cafbb','你今天怎么了',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 17:15:00',NULL,NULL,'10',NULL),('f072f004-150a-47c1-a423-77b5bfbd61d6','4797b896-9807-46b9-ab9c-52d1829984b0','(〃\'▽\'〃) (´ ❥ `)',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:55:59',NULL,NULL,'10',NULL),('f417506f-ec0e-4e84-8ba6-781622f345c7','7d4378e2-41b0-4b6f-b903-84cb7e4d58fa','( • ̀ω•́ )✧',NULL,'9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:17:07',NULL,NULL,'10',NULL);

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `uuid` char(36) NOT NULL COMMENT 'uuid 主键',
  `fkId` char(36) NOT NULL COMMENT 'user外键',
  `updateDate` date DEFAULT NULL COMMENT '更新时间',
  `post` int(1) DEFAULT '0' COMMENT '发帖次数',
  `comment` int(5) DEFAULT '0' COMMENT '评论次数',
  `fork` int(36) DEFAULT '0' COMMENT '点赞次数',
  `tag` int(10) DEFAULT '0' COMMENT '加标签次数',
  `store` int(36) DEFAULT '0' COMMENT '商品好评次数',
  `firstFollow` int(36) DEFAULT '0' COMMENT '首次关注次数',
  `report` int(10) DEFAULT '0' COMMENT '举报采纳次数',
  `isAll` int(1) DEFAULT '0' COMMENT '完善资料次数',
  `myScore` int(36) DEFAULT '0' COMMENT '用户总积分',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分表';

/*Data for the table `score` */

insert  into `score`(`uuid`,`fkId`,`updateDate`,`post`,`comment`,`fork`,`tag`,`store`,`firstFollow`,`report`,`isAll`,`myScore`) values ('2d2e9243-97d0-4b27-afeb-673322a319c6','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01',1,2,10,0,0,1,0,1,305),('451682d0-758a-471d-beb6-3d209f6ca5bd','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01',3,10,10,3,0,1,0,1,580),('934d7d92-8adb-4cef-80ed-fd87d65fc4b4','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30',3,2,10,2,0,1,0,1,395),('d2ef0c73-438b-4c53-b57d-15f4c0d89f17','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-07-01',1,0,0,0,0,1,0,1,140),('ec624227-b13d-4c57-9c95-d8457476e915','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01',1,1,0,0,0,0,0,1,160);

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `uuid` char(36) NOT NULL COMMENT 'uuid 主键',
  `comClass` varchar(20) NOT NULL COMMENT '分类',
  `code` varchar(50) NOT NULL COMMENT 'code值',
  `name` varchar(50) NOT NULL COMMENT 'code名称',
  `createrId` char(36) DEFAULT NULL COMMENT 'createrId 创建人Id',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

/*Data for the table `tag` */

insert  into `tag`(`uuid`,`comClass`,`code`,`name`,`createrId`,`remark`) values ('0','A_TAG','A_0','第一次说','title','http://img3.imgtn.bdimg.com/it/u=184570246,4277853927&fm=21&gp=0.jpg'),('1','A_TAG','A_1','第一次见面','1','\"http://img5.imgtn.bdimg.com/it/u=2921477736,3540642309&fm=11&gp=0.jpg'),('100','B_TAG','B_0','二人世界','title','http://img2.imgtn.bdimg.com/it/u=1751979636,135706838&fm=11&gp=0.jpg'),('101','B_TAG','B_1','浪漫电影','1','http://img2.imgtn.bdimg.com/it/u=2442262559,1475150055&fm=11&gp=0.jpg'),('102','B_TAG','B_2','吃饭圣地','1','http://img0.imgtn.bdimg.com/it/u=2473342780,1498457558&fm=11&gp=0.jpg'),('103','B_TAG','B_3','情趣酒店','1','http://img5.imgtn.bdimg.com/it/u=1402896672,2794493783&fm=11&gp=0.jpg'),('104','B_TAG','B_4','美丽沙滩','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('105','B_TAG','B_5','爱情漫步','1','http://img5.imgtn.bdimg.com/it/u=1402896672,2794493783&fm=11&gp=0.jpg'),('106','B_TAG','B_6','撩妹撩汉','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('107','B_TAG','B_7','有你相伴','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('2','A_TAG','A_2','第一次牵手','1','http://img4.imgtn.bdimg.com/it/u=4025356997,3888770775&fm=21&gp=0.jpg'),('200','C_TAG','C_0','爱之墨迹','title','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('201','C_TAG','C_1','一封情书','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('202','C_TAG','C_2','爱情语录','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('203','C_TAG','C_3','我对你说','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('204','C_TAG','C_4','分手理由','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=1.jpg'),('3','A_TAG','A_3','第一次吃饭','1','http://img1.imgtn.bdimg.com/it/u=834652240,1265758209&fm=21&gp=0.jpg'),('300','D_TAG','D_0','我们故事','title','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('301','D_TAG','D_1','暗恋的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('302','D_TAG','D_2','表白的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('303','D_TAG','D_3','初恋的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('304','D_TAG','D_4','情人节故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('305','D_TAG','D_5','象牙塔故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('306','D_TAG','D_6','求婚的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('307','D_TAG','D_7','虐心的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('308','D_TAG','D_8','分手的故事','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('4','A_TAG','A_4','第一次kiss','1','http://img3.imgtn.bdimg.com/it/u=184570246,4277853927&fm=21&gp=0.jpg'),('400','E_TAG','E_0','自恋秀场','title','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('401','E_TAG','E_1','女神秀','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('402','E_TAG','E_2','软妹子','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('403','E_TAG','E_3','嗲女生','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('404','E_TAG','E_4','单身狗','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('405','E_TAG','E_5','马甲线','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('406','E_TAG','E_6','一字马','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('407','E_TAG','E_7','纹身控','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('408','E_TAG','E_8','健身控','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('409','E_TAG','E_9','情侣秀','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('410','E_TAG','E_10','姐有沟','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=1.jpg'),('411','E_TAG','E_11','大长腿','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=2.jpg'),('412','E_TAG','E_12','二次元','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=3.jpg'),('413','E_TAG','E_13','COSPLAY','1','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=4.jpg'),('5','A_TAG','A_5','第一次啪啪','1','http://img5.imgtn.bdimg.com/it/u=268752361,4057597784&fm=21&gp=0.jpg'),('500','F_TAG','F_0','恋爱神器','title','http://img1.imgtn.bdimg.com/it/u=3542411806,1491800083&fm=21&gp=0.jpg'),('8','Q_TAG','Q_3','',NULL,NULL);

/*Table structure for table `topic` */

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `expId` char(36) NOT NULL COMMENT '经历ID\r\n            ',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面',
  `name` varchar(50) NOT NULL COMMENT '主帖名称',
  `content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `createrId` char(36) DEFAULT NULL COMMENT '创建人ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updaterId` char(36) DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `gps` varchar(50) DEFAULT NULL COMMENT '坐标',
  `tag` varchar(50) DEFAULT NULL COMMENT '标签',
  `recommend` decimal(2,0) DEFAULT NULL COMMENT '推荐',
  `type` decimal(2,0) DEFAULT NULL COMMENT '类型(1纯文字 2图文混合 3视频)',
  `access` decimal(10,0) DEFAULT '0' COMMENT '访问量',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主帖表\r\n';

/*Data for the table `topic` */

insert  into `topic`(`uuid`,`expId`,`cover`,`name`,`content`,`createrId`,`createDate`,`updaterId`,`updateDate`,`status`,`gps`,`tag`,`recommend`,`type`,`access`) values ('039d84b0-0175-4199-9e46-4eb3b9b48961','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/3297b063-4897-4b7b-975a-cedbb17f1667.jpg','','单纯的情意、朦胧的爱。','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:28:11',NULL,NULL,'10',NULL,'A_2',NULL,'2','32'),('1574af5d-34dc-42a6-93ff-2e7d935c685f','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160701/dea94e7a-49b2-4509-959e-5bcb9c8d48ea.jpg','','的地方下的','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-07-01 11:10:43',NULL,NULL,'10',NULL,'',NULL,'2','5'),('22bdfd89-c086-4b9c-bf8c-51a6d109d631','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/12e1b4b1-8252-4a80-b1e3-eb4c34f6eb48.jpg','','萌萌萌▼(´ᴥ`)▼','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 14:19:12',NULL,NULL,'10',NULL,'',NULL,'2','5'),('2a6b154e-5975-4415-9ef9-435a9b382457','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/61597447-f6d1-49d2-be31-ddf819594f89.jpg','','可以不在乎，才能对别人在乎','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 13:16:02',NULL,NULL,'10',NULL,'B_6',NULL,'2','1'),('2d23dac1-5ec6-49a8-880b-fa7833c2e56f','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/2751d74f-513e-4e10-b7fd-15fd86ab8461.jpg','','盾冬大法好','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 14:23:39',NULL,NULL,'10',NULL,'',NULL,'2','3'),('3171db3b-8f34-47e0-b1f6-527582a0c99b','bcbc9602-0707-4d6e-9f98-543b3c3df847',NULL,'','爱情嘎然而止的时候\n\n就像喝完一杯红酒\n\n甜蜜的晕眩散去\n\n好的爱情留有回甘\n\n痛的爱情留下苦涩\n\n爱情怦然来袭的时候\n\n就像靠近太阳\n\n理智和虚荣抛弃\n\n炙热的灵魂颤抖\n\n滚烫的心脏跳舞','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 13:45:58',NULL,NULL,'10',NULL,'',NULL,'1','5'),('345fc9ee-613d-4274-9e09-a57c0ce35fb2','bcbc9602-0707-4d6e-9f98-543b3c3df847','http://139.129.109.204:8080/upload/topic/20160701/879d45a1-1148-45c2-8aa5-6a1d59550231.jpg','','一起观看博物馆','619e1e94-693a-42ce-bb77-8b8d31423298','2016-07-01 11:55:18',NULL,NULL,'10',NULL,'',NULL,'2','2'),('37298065-56af-48da-a3c7-8746f3959f3b','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/91a04833-9892-4e6f-89b9-498036dc9725.jpg','','看着微笑的你，突然发现我是世界上最幸福的人。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:13:46',NULL,NULL,'10',NULL,'B_1',NULL,'2','9'),('3baceca8-b50c-48c9-af03-3aeecd6146af','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/e55ca9d6-911b-4a3e-ba92-672c62e507ac.jpg','','如果活着，是上帝赋予我最大的使命，那么活着有你，将会是上帝赋予我使命中最大的恩赐','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 11:26:48',NULL,NULL,'10',NULL,'C_3',NULL,'2','3'),('3e3feb8c-b57c-44de-a48e-7eed1b63cdb9','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160629/ac40f381-a9e7-41dc-adcf-89ebbae8c9ef.jpg','','哈哈哈哈哈哈哈哈哈','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:54:46',NULL,NULL,'10',NULL,'B_1',NULL,'2','15'),('4797b896-9807-46b9-ab9c-52d1829984b0','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/c7773b4e-6a96-4653-9140-96701529e879.jpg','','　天气渐冷，屋子里带有着一丝丝的凉意，我躺在床上随手翻阅着案头上那些个被岁月尘封了许久的故事。它承载了我年少时期所有的梦，包括那如梦幻般羞涩的青春年华、以及发生在了流年里朦胧的爱恨纠缠。可是如歌的岁月却将我拉回了无情的现实，往事一幕幕，回想起曾经发生在身边的故事依旧是那么真切、那么唯美，仔细回忆时却又变得那么碎杂、那么凌乱……','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:30:00',NULL,NULL,'10',NULL,'C_1',NULL,'2','22'),('4845d351-36e0-4cb9-b7c6-b5132237bdf3','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/d21bb6db-44db-482a-8096-3b7209727362.jpg','','情人节快乐[(|)]','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 16:58:15',NULL,NULL,'10',NULL,'D_4',NULL,'2','1'),('4ac89693-08a0-43f7-8f85-488ed9d0e966','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160629/d6522b4f-322e-4b4c-9f4d-c1e742237e15.jpg','','好吃好吃好吃','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:52:25',NULL,NULL,'10',NULL,'A_3',NULL,'2','13'),('56e458bf-08a1-419c-b218-363d7791fbd0','bcbc9602-0707-4d6e-9f98-543b3c3df847',NULL,'','爱情总是悄悄的降临，让我不知所措','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:07:04',NULL,NULL,'10',NULL,'',NULL,'1','11'),('576ee36c-6067-48f8-a6dd-ef6bed42e7bf','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/540143f7-754a-4239-82f5-e4fd02b44d00.jpg','','[;)][;)][;)]','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 16:59:36',NULL,NULL,'10',NULL,'E_9',NULL,'2','1'),('611d5541-b33f-473d-a931-7738c6b2e6e0','bcbc9602-0707-4d6e-9f98-543b3c3df847','http://139.129.109.204:8080/upload/topic/20160630/293b0698-7927-455f-abc8-5984f39f2de7.jpg','','每当孤单的时候，总是默默的想你','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-30 10:18:59',NULL,NULL,'10',NULL,'',NULL,'2','4'),('745cc58c-ee99-4371-a2ba-c9a14758613d','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/87f210d1-5473-4853-b823-020672ce0845.jpg','','[:\'(][:\'(][:\'(][:\'(]','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 13:45:16',NULL,NULL,'10',NULL,'C_4',NULL,'2','2'),('76438854-5fbf-404a-846d-a8a16d9e7888','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/b9e6d458-79db-4ab4-9151-e6d495d58646.jpg','','不愿意醒来时，台灯投射在墙上只有我孤独的身影。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:17:11',NULL,NULL,'10',NULL,'D_8',NULL,'2','22'),('76db3d13-e0f5-4b58-81b8-c6e3ee63573c','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/c97a3c42-aecc-4cfc-a9b7-28441affeec3.jpg','','我放下了尊严，放下了个性，放下了固执，都只因为放不下你。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 11:28:45',NULL,NULL,'10',NULL,'C_3',NULL,'2','4'),('7d4378e2-41b0-4b6f-b903-84cb7e4d58fa','d6b1290b-643d-4271-807e-22049ad98d40',NULL,'','每个人都有段记忆、深埋心底不曾诉说于他人的记忆和回忆、 你能会想自己三年前刚走出社会是什么样子吗？ 是幼稚、没有大脑、没有自己的做事方式没有可用的思维、还是傻事做了一大堆、可能那个时候的记忆、包括爱情、友情、亲情对你来说是是依靠还是快乐还是...','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:15:43',NULL,NULL,'10',NULL,'',NULL,'1','13'),('869918bd-0804-4dc4-9da4-d0a60ec47565','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/cd5f4acd-65d1-4344-b9dd-7aee074105f9.jpg','','　“时间是懂事的孩子，盼望你的拥抱，在沉默的时候让我看到你一次一次温柔的笑，岁月是成熟的叶子，在这一刻将我围绕，再盛开的时候让我看到，这一切来的刚刚好...”我不会忘了，这是你为我唱的第一首歌情歌，很清楚的记得，你再唱歌的时候那深情的表情，我估计连你自己都被陶醉了吧，那也是我第一次很认真的关注着我面前的这个女生，不对！在此应该改正一下是冷冰冰的视频前的这个女生。','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:33:43',NULL,NULL,'10',NULL,'A_1',NULL,'2','18'),('8c8e9f72-35b3-41e4-8b3b-41ed8215e622','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160630/d8761d4d-7d36-442b-a029-d2b09ffdfa5c.jpg','','如果一开始就知道这样的结局，我不知道自己是不是会那样的奋不顾身','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 15:32:42',NULL,NULL,'10',NULL,'',NULL,'2','7'),('8efe838c-eba1-45d8-ac0c-1fdc6f8dbf37','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160630/bcd46e59-07d9-4cac-9455-77d371395bb5.jpg','','泡妞圣地','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:28:03',NULL,NULL,'10',NULL,'B_5',NULL,'2','9'),('901d5f05-c660-410c-a6b3-5028751a2031','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160630/ca95dc42-b5ac-41a0-94b3-2cd4eec18718.jpg','','爱情不是你买 想买就能买 现在裤子脱下来','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 14:18:47',NULL,NULL,'10',NULL,'C_2',NULL,'2','10'),('925a4d34-e177-4c1d-9e93-4eb34acee9a0','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/d20e7905-f2df-4f3b-bdf7-453147383ade.jpg','','[):][):][):]','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:18:10',NULL,NULL,'10',NULL,'E_4',NULL,'2','18'),('93f226f1-c043-4190-82a9-036a6f65af8c','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/e5b79129-4c7e-4e30-9b84-a75ac7fc0541.jpg','','[({)][({)][({)]','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 17:02:23',NULL,NULL,'10',NULL,'E_8',NULL,'2','2'),('951054de-707d-457c-afe7-b8e52b9f9481','d6b1290b-643d-4271-807e-22049ad98d40','http://139.129.109.204:8080/upload/topic/20160629/2af92905-df27-42ba-91b7-6e2ccfdcbd0b.jpg','','佛曾经说过只因前生五百次的轮回，才能换回今生的一点缘，然后相忘于尘世！ 如果真的是这样，我宁愿没有开始，这样就不会有遗憾','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:19:47',NULL,NULL,'10',NULL,'B_7',NULL,'2','13'),('9601150e-18dd-44b6-b14c-7b052f82dd45','d6b1290b-643d-4271-807e-22049ad98d40','http://139.129.109.204:8080/upload/topic/20160629/b852d2ef-9133-4851-a420-7fe9f139c1ea.jpg','','今天去吃了杨国福麻辣烫，看到了一个超级丑的日本艺妓...','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 14:19:39',NULL,NULL,'10',NULL,'',NULL,'2','18'),('98a24ad9-74b7-404b-8e0b-aeaf8dad8a46','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/8df16c9e-46db-4414-aa2c-02c78415f078.jpg','','爱那么简单，在一起却那么难。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:43:03',NULL,NULL,'10',NULL,'D_1',NULL,'2','5'),('a8a2dadf-4168-4c5f-a565-bf7b2583b519','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/c38fbedb-ea8d-4b35-ad20-87c024173c60.jpg','','除了你，别人的爱我不稀罕','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:20:14',NULL,NULL,'10',NULL,'A_3',NULL,'2','8'),('b9373510-6ef4-4a56-a091-ab55cb22c1e5','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/96f71338-9940-47f6-936c-33c827a4f3d6.jpg','','想你的心情实在没有办法用一句话代替。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 09:15:26',NULL,NULL,'10',NULL,'C_2',NULL,'2','6'),('bb583764-f53b-4da0-a9b3-8d8ad923258d','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/13a033be-afd0-49e3-a390-aea797c0899a.jpg','','知不知道，你是我的整个青春','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 15:40:04',NULL,NULL,'10',NULL,'B_7',NULL,'2','12'),('bed53bac-9ed5-4a8b-bf96-24d9255e03fa','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160630/0de5bfb8-a329-4f6a-8ea6-efa04abb439c.jpg','','我还在原地等你，你却已经忘记曾来过这里[(S)]','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 14:23:17',NULL,NULL,'10',NULL,'D_7',NULL,'2','5'),('d0f784f9-8f63-4239-b98a-606abafe5451','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/6453ca8a-f329-4438-83f9-b8d22ab0dfff.jpg','','我曾路过你的心，不是我不想停留，而是你不肯收留。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:46:15',NULL,NULL,'10',NULL,'D_1',NULL,'2','2'),('d18f6e5b-80c0-402b-8c05-dcfcb5cddd7e','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160630/4bfcc9ac-09c2-4672-b1e8-15b5cb9b5aa7.jpg','','我还是相信，星星会说话，石头会开花，穿过夏天的木栅栏和冬天的风雪之后，你终会抵达！','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-30 14:38:55',NULL,NULL,'10',NULL,'',NULL,'2','8'),('d9d22367-c002-4414-a684-e42a746cafbb','15106715-1490-43dd-8a64-19e2d699ee19','http://139.129.109.204:8080/upload/topic/20160701/7812487d-5f4d-4cf5-b883-39ab173d943d.jpg','','[;)]美女？','9390b2d9-01f6-4809-9cd1-c4f812855001','2016-07-01 13:39:54',NULL,NULL,'10',NULL,'',NULL,'2','13'),('db77eb24-f331-4c08-bbff-32535c045896','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/4d615516-b5ab-48bb-85fb-661328b461fc.jpg','','给我一个承诺，我哪里都不会去，就站在这里等你。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 11:34:33',NULL,NULL,'10',NULL,'D_5',NULL,'2','2'),('dd71374e-f2ac-41f4-bf98-468ce769b02c','152f95a9-7355-4d95-bad2-64d7a7620b0b','http://139.129.109.204:8080/upload/topic/20160701/373dc047-033f-42f2-b5eb-2bac554f74e7.jpg','','你得从我的记忆中离开，我才能自由','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-07-01 10:54:05',NULL,NULL,'10',NULL,'B_1',NULL,'2','2'),('df1ff5b8-8c2e-4e2d-9c46-d71c38984fbb','bcbc9602-0707-4d6e-9f98-543b3c3df847','http://139.129.109.204:8080/upload/topic/20160629/997ed46c-2441-4512-9038-16937e3610ed.jpg','','为亲爱的做的，么么哒','619e1e94-693a-42ce-bb77-8b8d31423298','2016-06-29 18:06:04',NULL,NULL,'10',NULL,'B_2',NULL,'2','10'),('e10f7b3e-1788-4a9f-80d4-4656299502ba','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160630/ab5e7662-a01f-4d9a-a8f0-2a14cd0be72d.jpg','','红唇女孩偶遇','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:08:29',NULL,NULL,'10',NULL,'',NULL,'2','5'),('e3737a8b-f761-4bbc-a569-72285ec8095c','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160630/392df19f-802b-4517-a3cc-0e2b44f7cca2.jpg','','٩(๑`н´๑)۶','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-30 14:25:57',NULL,NULL,'10',NULL,'',NULL,'2','3'),('e66cf6f9-1ee7-4d0b-b3b5-c8cf9f4e4d71','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160629/58328cb4-c854-445c-8d96-81f1258e5678.jpg','','难以忘记初次见你，一双迷人的眼睛。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:50:38',NULL,NULL,'10',NULL,'A_1',NULL,'2','11'),('e799c923-d9ae-460b-9e64-504259a63540','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160630/9fef02c1-6988-4fef-9139-d08c94f90f3f.jpg','','今日模特6人','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:14:36',NULL,NULL,'10',NULL,'',NULL,'2','3'),('e889986b-87b8-4096-aaff-35d443d4ab55','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/99d90a29-53a7-484a-a425-9156ff4af2c0.jpg','','　明媚的阳光，柔美的风光，迷人的海边有一群可爱的孩纸，光着脚丫，牵着小手，尽情狂奔，呐喊，尽情挥洒属于他们自己的天地和梦想。','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 17:41:19',NULL,NULL,'10',NULL,'B_5',NULL,'2','9'),('f060671a-b3b3-428f-a427-06450870075d','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/75ebc497-f568-4ece-974e-6bbd816f383f.jpg','','春光是一条河，你在岸的这边，流连于风姿万种的烟花三月，看一只忙碌燕子衔泥筑梦，看一瓣清淡的云悠闲飘逸，或是喝一杯山茶花的清露，在阳光下打个盹儿，就已经错过彼岸的船。站在春天的渡口，手中拽紧一张过期的船票，又该登上哪一艘收留你的客船？','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:18:50',NULL,NULL,'10',NULL,'D_7',NULL,'2','18'),('f26e99be-0ded-44b6-915b-5300ab82d908','1828602b-7666-4ba7-ab49-f58853b7bec2','http://139.129.109.204:8080/upload/topic/20160630/681bb891-257a-4938-b5fd-2e7e0d2ed153.jpg','','遇见女神[(H)]','5c6e8194-4b48-4237-8ac1-1d8846b6607f','2016-06-30 14:06:07',NULL,NULL,'10',NULL,'',NULL,'2','4'),('f302ea8c-9baf-4e90-ac6f-c5f8678ce178','ae7211e4-7e18-4c2e-8396-83a53e7dae4c','http://139.129.109.204:8080/upload/topic/20160629/85fbcf88-343a-457d-bf60-f7e69322168e.jpg','','喜欢你，那双眼动人，笑声更迷人。','9bc73bd6-be69-4334-8391-f1d91ac82745','2016-06-29 17:53:27',NULL,NULL,'10',NULL,'A_2',NULL,'2','15'),('f3367190-8e15-44b7-920b-50cedc70c2d3','704e647e-9fe9-490b-9467-4b3f944e4430','http://139.129.109.204:8080/upload/topic/20160629/fbc96f6a-6cc5-48fe-a466-86b860bd551b.jpg','','偏偏痴心喜欢你','4651b11f-e8d9-4fba-b0cd-a5351eec89af','2016-06-29 16:24:18',NULL,NULL,'10',NULL,'A_4',NULL,'2','11');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `userId` varchar(20) NOT NULL COMMENT '用户ID(登录用)',
  `passWrod` char(40) NOT NULL COMMENT '用户登录密码\r\n            ',
  `name` varchar(50) DEFAULT NULL COMMENT '用户昵称（表示用）',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '性别 (1男 2女)',
  `job` varchar(50) DEFAULT NULL COMMENT '职业',
  `clas` decimal(2,0) DEFAULT NULL COMMENT '用户等级（权限）',
  `point` int(12) DEFAULT NULL COMMENT '积分',
  `currency` int(12) NOT NULL DEFAULT '0' COMMENT '虚拟货币',
  `latitude` varchar(50) DEFAULT '38.919345',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注（个人签名）',
  `updaterId` char(36) DEFAULT NULL COMMENT '更新人ID',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `vx` varchar(50) DEFAULT NULL COMMENT '微信',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq',
  `face` varchar(50) DEFAULT NULL COMMENT '头像码',
  `longitude` varchar(50) DEFAULT '121.621391' COMMENT '纬度坐标',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`uuid`,`userId`,`passWrod`,`name`,`birthday`,`sex`,`job`,`clas`,`point`,`currency`,`latitude`,`remark`,`updaterId`,`updateDate`,`status`,`vx`,`qq`,`face`,`longitude`) values ('4651b11f-e8d9-4fba-b0cd-a5351eec89af','13500780439','81dc9bdb52d04dc2036dbd8313ed055','邱_',NULL,'1',NULL,NULL,NULL,258,'38.8594',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'121.526623'),('5c6e8194-4b48-4237-8ac1-1d8846b6607f','13898686805','9e4022ab53c1d8733260fb118d7e2b4b','1389xxxx805',NULL,'1',NULL,NULL,NULL,83,'38.865384',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'121.532877'),('619e1e94-693a-42ce-bb77-8b8d31423298','15941137503','e4f1afbb1b9ae3dd6747ced5bca532','我还活着',NULL,'1',NULL,NULL,NULL,137,'38.859457',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'121.526367'),('9390b2d9-01f6-4809-9cd1-c4f812855001','18642801420','b524a5c1c8332193f6725a62b66a99','小羊毛茸茸',NULL,'2',NULL,NULL,NULL,92,'38.859383',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'121.526589'),('9bc73bd6-be69-4334-8391-f1d91ac82745','15140374302','e1adc3949ba59abbe56e057f2f883e','卤蛋',NULL,'2',NULL,NULL,NULL,204,'38.865392',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'121.532975');

/*Table structure for table `version` */

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `uuid` char(36) NOT NULL COMMENT '主键\r\n            ',
  `versionNo` varchar(20) NOT NULL COMMENT '版本号',
  `remarks` varchar(500) DEFAULT NULL COMMENT '版本信息',
  `forced` int(1) DEFAULT NULL COMMENT '强制更新',
  `system` int(1) DEFAULT NULL COMMENT '手机系统',
  `path` varchar(500) DEFAULT NULL COMMENT '更新路径',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版本信息\r\n';

/*Data for the table `version` */

insert  into `version`(`uuid`,`versionNo`,`remarks`,`forced`,`system`,`path`,`updateDate`) values ('f320dd23-dc85-4a66-a6fc-45381743956d','1.0','更新',0,0,'http://192.168.1.100:8080/upload/relase/YuLian.apk','2016-05-17 15:33:15');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
