/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : luckdraw_db

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-09-11 15:32:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `ID` int(10) NOT NULL,
  `PID` int(10) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  `SORT` int(10) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `PERM_CODE` varchar(50) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `DESCRIPTION` text,
  `FROM_APP_IDENTIFY_ID` varchar(50) DEFAULT NULL,
  KEY `rp_id_index` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` varchar(16) NOT NULL,
  `uname` varchar(64) DEFAULT NULL COMMENT '名称',
  `utype` int(3) DEFAULT NULL COMMENT '用户类型',
  `phone` char(11) DEFAULT NULL COMMENT '电话',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhangsan', '1', '110', '2020-09-01 10:23:14', '2020-09-02 10:23:18');

-- ----------------------------
-- Table structure for tb_activity
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity` (
  `id` varchar(16) NOT NULL COMMENT '主键',
  `activity_name` varchar(128) DEFAULT NULL COMMENT '活动名称',
  `activity_info` varchar(255) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  `is_invalid` tinyint(4) DEFAULT NULL COMMENT '是否失效',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE COMMENT '主键索引',
  KEY `idx_starttime` (`starttime`),
  KEY `idx_endtime` (`endtime`),
  KEY `idx_is_invalid` (`is_invalid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_activity
-- ----------------------------
INSERT INTO `tb_activity` VALUES ('hd_001', '助力炸金蛋', '助力炸金蛋，来把', '2020-09-01 14:00:37', '2020-09-30 14:00:42', '0', '2020-09-01 14:00:57', '2020-09-02 14:01:01');
INSERT INTO `tb_activity` VALUES ('hd_002', '助力炸金蛋-升级版', '助力炸金蛋，来把', '2020-09-01 14:00:37', '2020-09-30 14:00:42', '0', '2020-09-01 14:00:57', '2020-09-02 14:01:01');
INSERT INTO `tb_activity` VALUES ('hd_003', '助力炸金蛋-夏天版', '助力炸金蛋，来把', '2020-09-01 14:00:37', '2020-09-03 14:00:42', '1', '2020-09-01 14:00:57', '2020-09-02 14:01:01');
INSERT INTO `tb_activity` VALUES ('hd_004', '助力炸金蛋-十一版', '助力炸金蛋，来把', '2020-10-01 14:00:37', '2020-10-08 14:00:42', '0', '2020-09-01 14:00:57', '2020-09-02 14:01:01');

-- ----------------------------
-- Table structure for tb_awardee
-- ----------------------------
DROP TABLE IF EXISTS `tb_awardee`;
CREATE TABLE `tb_awardee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` varchar(16) DEFAULT NULL COMMENT '活动id',
  `activity_name` varchar(128) DEFAULT NULL COMMENT '活动名称',
  `prize_id` int(11) DEFAULT NULL COMMENT '奖品id',
  `prize_name` varchar(128) DEFAULT NULL COMMENT '奖品名称',
  `awardee_id` varchar(128) DEFAULT NULL COMMENT '获奖人id',
  `awardee_name` varchar(64) DEFAULT NULL COMMENT '获奖人姓名',
  `awardee_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '获奖时间',
  `prize_num` int(11) DEFAULT NULL COMMENT '获奖个数',
  `is_grant` tinyint(4) DEFAULT NULL COMMENT '奖品是否发放 1 以发放 0 未发放',
  `grant_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发放时间',
  `grant_id` int(11) DEFAULT NULL COMMENT '奖品发放表id',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_awardee
-- ----------------------------
INSERT INTO `tb_awardee` VALUES ('1', 'hd_001', null, '3', '积分8888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('2', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('3', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('4', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('5', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('6', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('7', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('8', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('9', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('10', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('11', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('12', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('13', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('14', 'hd_001', null, '1', '积分88', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('15', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);
INSERT INTO `tb_awardee` VALUES ('16', 'hd_001', null, '2', '积分888', null, 'zhangsan', null, '1', '1', null, null, '2020-09-11 00:00:00', null);

-- ----------------------------
-- Table structure for tb_integral
-- ----------------------------
DROP TABLE IF EXISTS `tb_integral`;
CREATE TABLE `tb_integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(16) DEFAULT NULL COMMENT '用户id',
  `activityid` varchar(10) DEFAULT NULL COMMENT '活动id',
  `uname` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_integral
-- ----------------------------
INSERT INTO `tb_integral` VALUES ('1', '1', 'hd_001', 'zhangsan', '60', '2020-09-11 15:28:40', '2020-09-11 15:28:40');

-- ----------------------------
-- Table structure for tb_prize
-- ----------------------------
DROP TABLE IF EXISTS `tb_prize`;
CREATE TABLE `tb_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prize_name` varchar(128) DEFAULT NULL COMMENT '奖品名称',
  `prize_info` varchar(255) DEFAULT NULL COMMENT '奖品信息',
  `prize_num` int(11) DEFAULT NULL COMMENT '奖品数目',
  `probability` decimal(20,10) DEFAULT NULL COMMENT '概率',
  `activityid` varchar(64) DEFAULT NULL COMMENT '活动id',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_prize
-- ----------------------------
INSERT INTO `tb_prize` VALUES ('1', '积分88', '积分88', '99993', '0.5500000000', 'hd_001', '2020-09-11 15:28:36', '2020-09-11 15:28:36');
INSERT INTO `tb_prize` VALUES ('2', '积分888', '积分888', '9990', '0.3000000000', 'hd_001', '2020-09-11 15:28:40', '2020-09-11 15:28:40');
INSERT INTO `tb_prize` VALUES ('3', '积分8888', '积分8888', '999', '0.1000000000', 'hd_001', '2020-09-11 13:53:23', '2020-09-11 13:53:23');
INSERT INTO `tb_prize` VALUES ('4', '现金100元', '现金100元', '100', '0.0100000000', 'hd_001', '2020-09-01 15:15:07', '2020-09-10 15:15:10');
INSERT INTO `tb_prize` VALUES ('5', '现金500元', '现金500元', '10', '0.0010000000', 'hd_001', '2020-09-01 15:15:07', '2020-09-10 15:15:10');
