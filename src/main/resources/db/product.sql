/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : sqltest

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-06-18 18:46:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `pname` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(16) DEFAULT NULL COMMENT '类型',
  `pmoney` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `num` int(11) DEFAULT '0' COMMENT '数目',
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'string', 'string', '0', '1', '2020-06-17 00:00:00', '2020-06-17 00:00:00');
INSERT INTO `product` VALUES ('2', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('3', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('4', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('5', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('6', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('7', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('8', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('9', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('10', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('11', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('12', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('13', 'pname1', 'type1', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('14', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('15', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('16', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('17', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('18', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('19', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('20', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('21', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('22', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('23', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('24', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('25', 'pname1', 'type2', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('26', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('27', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('28', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('29', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('30', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('31', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('32', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('33', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('34', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('35', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('36', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('37', 'pname1', 'type3', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('38', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('39', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('40', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('41', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('42', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('43', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('44', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('45', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('46', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('47', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('48', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('49', 'pname1', 'type4', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('50', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('51', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('52', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('53', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('54', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('55', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('56', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('57', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('58', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('59', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('60', 'pname1', 'type5', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('61', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('62', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('63', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('64', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('65', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('66', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('67', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('68', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('69', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('70', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('71', 'pname1', 'type6', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('72', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('73', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('74', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('75', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('76', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('77', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('78', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('79', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('80', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('81', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('82', 'pname1', 'type7', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('83', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('84', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('85', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('86', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('87', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('88', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('89', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('90', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('91', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('92', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('93', 'pname1', 'type8', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('94', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('95', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('96', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('97', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('98', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('99', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('100', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('101', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('102', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('103', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
INSERT INTO `product` VALUES ('104', 'pname1', 'type9', '100', '2', '2020-06-16 13:42:58', '2020-06-16 13:42:58');
