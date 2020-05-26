/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : sqltest

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-05-26 16:47:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shorturl
-- ----------------------------
DROP TABLE IF EXISTS `shorturl`;
CREATE TABLE `shorturl` (
  `id` bigint(11) NOT NULL COMMENT '主键',
  `shorturl` varchar(64) DEFAULT NULL COMMENT '短链接',
  `longurl` varchar(512) DEFAULT NULL COMMENT '普通的链接',
  `termtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '期限',
  `isoverdue` tinyint(4) DEFAULT NULL COMMENT '是否过期',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shorturl
-- ----------------------------
INSERT INTO `shorturl` VALUES ('5', 'localhost:8089/5', 'https://blog.csdn.net/qq_36635434/article/details/103992863', '2020-05-26 00:00:00', '1', '2020-05-26 00:00:00', '2020-05-26 00:00:00');
INSERT INTO `shorturl` VALUES ('7', 'http://localhost:8089/7', 'http://www.iocoder.cn/Ribbon/good-collection/', '2020-05-26 00:00:00', '1', '2020-05-26 00:00:00', '2020-05-26 00:00:00');
INSERT INTO `shorturl` VALUES ('101', 'http://localhost:8089/1D', 'https://juejin.im/post/5de2553e5188256e885f4fa3#comment', '2020-05-26 00:00:00', '1', '2020-05-26 00:00:00', '2020-05-26 00:00:00');
INSERT INTO `shorturl` VALUES ('102', 'http://localhost:8089/1E', 'https://juejin.im/post/5de2553e5188256e885f4fa3#comment', '2020-05-26 00:00:00', '1', '2020-05-26 00:00:00', '2020-05-26 00:00:00');
