/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : ds1

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-06-23 10:28:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_bussiness
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness`;
CREATE TABLE `t_bussiness` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '字段1',
  `code` varchar(64) DEFAULT NULL COMMENT '字段2',
  `addresscode` varchar(12) DEFAULT NULL COMMENT '地区编码',
  `diccode` varchar(16) DEFAULT NULL COMMENT '数据字典对应的code',
  `createdate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `guid` varchar(64) DEFAULT NULL,
  `data_version` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness
-- ----------------------------
