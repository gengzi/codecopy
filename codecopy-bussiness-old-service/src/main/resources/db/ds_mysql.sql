/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : ds_mysql

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-07-15 16:24:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mysql0
-- ----------------------------
DROP TABLE IF EXISTS `mysql0`;
CREATE TABLE `mysql0` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `ip` char(15) DEFAULT NULL COMMENT 'ip',
  `port` char(8) DEFAULT NULL COMMENT '端口',
  `username` varchar(16) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `createdate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mysql0
-- ----------------------------
INSERT INTO `mysql0` VALUES ('489844989485363200', '123.206.13.30', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845021848612864', '123.206.13.71', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845026609147904', '123.206.13.83', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845034305695744', '123.206.13.94', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845054107004928', '123.206.13.120', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845065758781440', '123.206.13.139', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845074323550208', '123.206.13.153', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845101808824320', '123.206.13.196', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845140027322368', '123.206.13.245', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845178266791936', '123.206.13.240', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845204900622336', '123.206.14.49', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845214459441152', '123.206.14.72', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845247523139584', '123.206.14.108', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845299452817408', '123.206.13.222', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845308801921024', '123.206.14.183', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845319224766464', '123.206.14.170', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845366888837120', '123.206.15.3', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845379375280128', '123.206.15.24', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845443829149696', '123.206.15.93', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845468709761024', '123.206.15.146', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845514675138560', '123.206.15.182', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845532643536896', '123.206.15.209', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845539178262528', '123.206.15.218', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845565312970752', '123.206.15.193', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845620572925952', '123.206.16.33', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845670132822016', '123.206.16.63', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845721148141568', '123.206.16.150', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845764668239872', '123.206.16.198', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845788936482816', '123.206.16.226', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845811036270592', '123.206.16.251', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845821765300224', '123.206.16.213', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845845798662144', '123.206.17.25', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845857383329792', '123.206.17.44', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845884327538688', '123.206.17.18', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845893735362560', '123.206.17.77', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845922084663296', '123.206.17.108', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845946990440448', '123.206.17.127', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489845965252440064', '123.206.17.150', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846003378663424', '123.206.17.190', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846019363155968', '123.206.17.208', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846036362670080', '123.206.17.226', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846079408812032', '123.206.18.17', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846093455536128', '123.206.18.19', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846117933494272', '123.206.18.69', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846181267484672', '123.206.18.140', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846192814403584', '123.206.18.150', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846212032704512', '123.206.18.176', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846266160197632', '123.206.18.217', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846277312851968', '123.206.18.200', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846320870699008', '123.206.19.23', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846394233270272', '123.206.19.71', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846421605298176', '123.206.19.114', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846440844570624', '123.206.19.119', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846495659929600', '123.206.19.194', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489846520553123840', '123.206.19.213', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('489875668243820544', '123.206.15.147', null, null, null, null, null);
INSERT INTO `mysql0` VALUES ('490189300596977664', '123.206.13.245', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:16', '2020-07-15 15:57:16');
INSERT INTO `mysql0` VALUES ('490189319144189952', '123.206.14.14', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:21', '2020-07-15 15:57:21');
INSERT INTO `mysql0` VALUES ('490189343517290496', '123.206.14.51', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:27', '2020-07-15 15:57:27');
INSERT INTO `mysql0` VALUES ('490189357333327872', '123.206.14.72', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:30', '2020-07-15 15:57:30');
INSERT INTO `mysql0` VALUES ('490189380494274560', '123.206.14.108', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:35', '2020-07-15 15:57:35');
INSERT INTO `mysql0` VALUES ('490189422697361408', '123.206.14.170', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:46', '2020-07-15 15:57:46');
INSERT INTO `mysql0` VALUES ('490189430742036480', '123.206.14.183', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:47', '2020-07-15 15:57:47');
INSERT INTO `mysql0` VALUES ('490189440116305920', '123.206.14.197', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:50', '2020-07-15 15:57:50');
INSERT INTO `mysql0` VALUES ('490189490028523520', '123.206.15.13', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:01', '2020-07-15 15:58:01');
INSERT INTO `mysql0` VALUES ('490189528234438656', '123.206.15.63', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:10', '2020-07-15 15:58:10');
INSERT INTO `mysql0` VALUES ('490189565253365760', '123.206.15.123', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:19', '2020-07-15 15:58:19');
INSERT INTO `mysql0` VALUES ('490189579769851904', '123.206.15.146', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:23', '2020-07-15 15:58:23');
INSERT INTO `mysql0` VALUES ('490189630499958784', '123.206.15.204', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:35', '2020-07-15 15:58:35');
INSERT INTO `mysql0` VALUES ('490189643783319552', '123.206.15.218', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:38', '2020-07-15 15:58:38');
INSERT INTO `mysql0` VALUES ('490189658484355072', '123.206.15.216', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:38', '2020-07-15 15:58:38');
INSERT INTO `mysql0` VALUES ('490189713387794432', '123.206.16.33', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:55', '2020-07-15 15:58:55');
INSERT INTO `mysql0` VALUES ('490189738641698816', '123.206.16.63', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:01', '2020-07-15 15:59:01');
INSERT INTO `mysql0` VALUES ('490189784988758016', '123.206.16.120', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:12', '2020-07-15 15:59:12');
INSERT INTO `mysql0` VALUES ('490189831163850752', '123.206.16.177', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:23', '2020-07-15 15:59:23');
INSERT INTO `mysql0` VALUES ('490189864118497280', '123.206.16.213', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:30', '2020-07-15 15:59:30');
INSERT INTO `mysql0` VALUES ('490189874520371200', '123.206.16.228', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:33', '2020-07-15 15:59:33');
INSERT INTO `mysql0` VALUES ('490189894523006976', '123.206.16.251', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:38', '2020-07-15 15:59:38');
INSERT INTO `mysql0` VALUES ('490189912709509120', '123.206.17.18', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:42', '2020-07-15 15:59:42');
INSERT INTO `mysql0` VALUES ('490189936176640000', '123.206.17.25', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:43', '2020-07-15 15:59:43');
INSERT INTO `mysql0` VALUES ('490189944795934720', '123.206.17.42', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:48', '2020-07-15 15:59:48');
INSERT INTO `mysql0` VALUES ('490189958192541696', '123.206.17.69', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:53', '2020-07-15 15:59:53');
INSERT INTO `mysql0` VALUES ('490189978711076864', '123.206.17.96', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:58', '2020-07-15 15:59:58');
INSERT INTO `mysql0` VALUES ('490190005827252224', '123.206.17.125', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:05', '2020-07-15 16:00:05');
INSERT INTO `mysql0` VALUES ('490190034168164352', '123.206.17.150', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:11', '2020-07-15 16:00:11');
INSERT INTO `mysql0` VALUES ('490190071468109824', '123.206.17.141', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:08', '2020-07-15 16:00:08');
INSERT INTO `mysql0` VALUES ('490190081962258432', '123.206.17.185', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:20', '2020-07-15 16:00:20');
INSERT INTO `mysql0` VALUES ('490190104754106368', '123.206.17.226', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:28', '2020-07-15 16:00:28');
INSERT INTO `mysql0` VALUES ('490190113637642240', '123.206.17.236', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:30', '2020-07-15 16:00:30');
INSERT INTO `mysql0` VALUES ('490190152401399808', '123.206.18.25', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:39', '2020-07-15 16:00:39');
INSERT INTO `mysql0` VALUES ('490190169488994304', '123.206.18.46', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:44', '2020-07-15 16:00:44');
INSERT INTO `mysql0` VALUES ('490190219308937216', '123.206.18.106', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:55', '2020-07-15 16:00:55');
INSERT INTO `mysql0` VALUES ('490190245414285312', '123.206.18.140', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:02', '2020-07-15 16:01:02');
INSERT INTO `mysql0` VALUES ('490190279472033792', '123.206.18.150', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:04', '2020-07-15 16:01:04');
INSERT INTO `mysql0` VALUES ('490190309415170048', '123.206.18.208', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:17', '2020-07-15 16:01:17');
INSERT INTO `mysql0` VALUES ('490190317141078016', '123.206.18.217', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:19', '2020-07-15 16:01:19');
INSERT INTO `mysql0` VALUES ('490190347692388352', '123.206.18.200', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:15', '2020-07-15 16:01:15');
INSERT INTO `mysql0` VALUES ('490190373025984512', '123.206.19.23', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:32', '2020-07-15 16:01:32');
INSERT INTO `mysql0` VALUES ('490190411219316736', '123.206.19.71', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:41', '2020-07-15 16:01:41');
INSERT INTO `mysql0` VALUES ('490190455452446720', '123.206.19.91', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:45', '2020-07-15 16:01:45');
INSERT INTO `mysql0` VALUES ('490190460603052032', '123.206.19.119', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:52', '2020-07-15 16:01:52');
INSERT INTO `mysql0` VALUES ('490190516546678784', '123.206.19.194', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:02:06', '2020-07-15 16:02:06');
INSERT INTO `mysql0` VALUES ('490190545676120064', '123.206.19.217', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:02:11', '2020-07-15 16:02:11');

-- ----------------------------
-- Table structure for mysql1
-- ----------------------------
DROP TABLE IF EXISTS `mysql1`;
CREATE TABLE `mysql1` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `ip` char(15) DEFAULT NULL COMMENT 'ip',
  `port` char(8) DEFAULT NULL COMMENT '端口',
  `username` varchar(16) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `createdate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mysql1
-- ----------------------------
INSERT INTO `mysql1` VALUES ('489845021626314753', '123.206.13.73', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845021953470465', '123.206.13.26', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845030618902529', '123.206.13.89', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845036021166081', '123.206.13.88', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845062407532545', '123.206.13.129', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845071316234241', '123.206.13.149', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845076160655361', '123.206.13.158', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845114672754689', '123.206.13.207', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845165730017281', '123.206.14.14', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845197090828289', '123.206.14.51', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845210080587777', '123.206.14.66', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845217340928001', '123.206.14.76', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845289004806145', '123.206.14.147', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845306331475969', '123.206.14.176', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845308957110273', '123.206.14.186', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845337100890113', '123.206.14.197', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845375327776769', '123.206.15.13', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845414016036865', '123.206.15.63', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845461730439169', '123.206.15.123', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845512766730241', '123.206.15.139', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845527551651841', '123.206.15.204', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845538662363137', '123.206.15.216', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845554093207553', '123.206.15.224', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845596686364673', '123.206.16.23', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845642647547905', '123.206.16.73', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845714357563393', '123.206.16.120', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845764534022145', '123.206.16.197', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845766643757057', '123.206.16.177', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845789850841089', '123.206.16.228', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845811787051009', '123.206.16.252', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845843131084801', '123.206.17.29', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845856137621505', '123.206.17.42', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845883564175361', '123.206.17.69', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845892091195393', '123.206.17.65', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845906775453697', '123.206.17.96', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845938870267905', '123.206.17.125', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845963432112129', '123.206.17.97', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489845986936991745', '123.206.17.141', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846014892027905', '123.206.17.185', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846031644078081', '123.206.17.223', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846041479720961', '123.206.17.236', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846079761133569', '123.206.18.25', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846099960901633', '123.206.18.46', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846168277725185', '123.206.18.106', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846192663408641', '123.206.18.131', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846203174334465', '123.206.18.161', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846249437507585', '123.206.18.208', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846269977014273', '123.206.18.218', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846286745841665', '123.206.18.213', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846393864171521', '123.206.19.57', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846395428646913', '123.206.19.12', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846438856470529', '123.206.19.121', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846492463869953', '123.206.19.180', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846513502498817', '123.206.19.217', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('489846540132134913', '123.206.19.246', null, null, null, null, null);
INSERT INTO `mysql1` VALUES ('490189310898188289', '123.206.13.240', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:15', '2020-07-15 15:57:15');
INSERT INTO `mysql1` VALUES ('490189343123025921', '123.206.14.49', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:26', '2020-07-15 15:57:26');
INSERT INTO `mysql1` VALUES ('490189353109663745', '123.206.14.66', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:29', '2020-07-15 15:57:29');
INSERT INTO `mysql1` VALUES ('490189358990077953', '123.206.14.76', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:30', '2020-07-15 15:57:30');
INSERT INTO `mysql1` VALUES ('490189408751300609', '123.206.14.147', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:42', '2020-07-15 15:57:42');
INSERT INTO `mysql1` VALUES ('490189426157662209', '123.206.14.176', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:46', '2020-07-15 15:57:46');
INSERT INTO `mysql1` VALUES ('490189432000327681', '123.206.14.186', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:57:48', '2020-07-15 15:57:48');
INSERT INTO `mysql1` VALUES ('490189486555639809', '123.206.15.3', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:00', '2020-07-15 15:58:00');
INSERT INTO `mysql1` VALUES ('490189496559054849', '123.206.15.24', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:03', '2020-07-15 15:58:03');
INSERT INTO `mysql1` VALUES ('490189545045209089', '123.206.15.93', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:15', '2020-07-15 15:58:15');
INSERT INTO `mysql1` VALUES ('490189575055454209', '123.206.15.139', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:22', '2020-07-15 15:58:22');
INSERT INTO `mysql1` VALUES ('490189620072919041', '123.206.15.182', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:30', '2020-07-15 15:58:30');
INSERT INTO `mysql1` VALUES ('490189634878812161', '123.206.15.209', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:36', '2020-07-15 15:58:36');
INSERT INTO `mysql1` VALUES ('490189650334822401', '123.206.15.224', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:40', '2020-07-15 15:58:40');
INSERT INTO `mysql1` VALUES ('490189679405543425', '123.206.15.193', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:33', '2020-07-15 15:58:33');
INSERT INTO `mysql1` VALUES ('490189719280791553', '123.206.16.23', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:58:52', '2020-07-15 15:58:52');
INSERT INTO `mysql1` VALUES ('490189744811520001', '123.206.16.73', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:02', '2020-07-15 15:59:02');
INSERT INTO `mysql1` VALUES ('490189807952572417', '123.206.16.150', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:17', '2020-07-15 15:59:17');
INSERT INTO `mysql1` VALUES ('490189846494031873', '123.206.16.197', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:27', '2020-07-15 15:59:27');
INSERT INTO `mysql1` VALUES ('490189865875910657', '123.206.16.198', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:27', '2020-07-15 15:59:27');
INSERT INTO `mysql1` VALUES ('490189878391713793', '123.206.16.226', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:33', '2020-07-15 15:59:33');
INSERT INTO `mysql1` VALUES ('490189898528567297', '123.206.16.252', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:38', '2020-07-15 15:59:38');
INSERT INTO `mysql1` VALUES ('490189921177808897', '123.206.17.29', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:44', '2020-07-15 15:59:44');
INSERT INTO `mysql1` VALUES ('490189936386355201', '123.206.17.44', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:48', '2020-07-15 15:59:48');
INSERT INTO `mysql1` VALUES ('490189954711269377', '123.206.17.65', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:52', '2020-07-15 15:59:52');
INSERT INTO `mysql1` VALUES ('490189963015991297', '123.206.17.77', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:54', '2020-07-15 15:59:54');
INSERT INTO `mysql1` VALUES ('490189988655771649', '123.206.17.108', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:01', '2020-07-15 16:00:01');
INSERT INTO `mysql1` VALUES ('490190007546916865', '123.206.17.127', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:05', '2020-07-15 16:00:05');
INSERT INTO `mysql1` VALUES ('490190036445671425', '123.206.17.97', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 15:59:58', '2020-07-15 15:59:58');
INSERT INTO `mysql1` VALUES ('490190074597060609', '123.206.17.190', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:21', '2020-07-15 16:00:21');
INSERT INTO `mysql1` VALUES ('490190090422169601', '123.206.17.208', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:25', '2020-07-15 16:00:25');
INSERT INTO `mysql1` VALUES ('490190107182608385', '123.206.17.223', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:28', '2020-07-15 16:00:28');
INSERT INTO `mysql1` VALUES ('490190147988992001', '123.206.18.17', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:37', '2020-07-15 16:00:37');
INSERT INTO `mysql1` VALUES ('490190160160862209', '123.206.18.19', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:38', '2020-07-15 16:00:38');
INSERT INTO `mysql1` VALUES ('490190187503529985', '123.206.18.69', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:00:48', '2020-07-15 16:00:48');
INSERT INTO `mysql1` VALUES ('490190238288162817', '123.206.18.131', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:00', '2020-07-15 16:01:00');
INSERT INTO `mysql1` VALUES ('490190279409119233', '123.206.18.161', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:06', '2020-07-15 16:01:06');
INSERT INTO `mysql1` VALUES ('490190282068307969', '123.206.18.176', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:11', '2020-07-15 16:01:11');
INSERT INTO `mysql1` VALUES ('490190313873715201', '123.206.18.213', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:18', '2020-07-15 16:01:18');
INSERT INTO `mysql1` VALUES ('490190317589868545', '123.206.18.218', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:19', '2020-07-15 16:01:19');
INSERT INTO `mysql1` VALUES ('490190364528324609', '123.206.19.12', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:30', '2020-07-15 16:01:30');
INSERT INTO `mysql1` VALUES ('490190399794032641', '123.206.19.57', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:39', '2020-07-15 16:01:39');
INSERT INTO `mysql1` VALUES ('490190451161673729', '123.206.19.114', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:51', '2020-07-15 16:01:51');
INSERT INTO `mysql1` VALUES ('490190456790429697', '123.206.19.121', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:01:52', '2020-07-15 16:01:52');
INSERT INTO `mysql1` VALUES ('490190510007758849', '123.206.19.180', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:02:04', '2020-07-15 16:02:04');
INSERT INTO `mysql1` VALUES ('490190535953723393', '123.206.19.213', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:02:10', '2020-07-15 16:02:10');
INSERT INTO `mysql1` VALUES ('490190562050682881', '123.206.19.246', '3306', 'root', 'QJhz1zp9x4+VVpznMYjz4A==', '2020-07-15 16:02:17', '2020-07-15 16:02:17');

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
INSERT INTO `shorturl` VALUES ('101', 'http://localhost:8089/u/1D', 'fdafdafda', '2020-06-29 00:00:00', '1', '2020-06-29 00:00:00', '2020-06-29 00:00:00');
INSERT INTO `shorturl` VALUES ('102', 'http://localhost:8089/u/1E', '32432', '2020-06-29 00:00:00', '1', '2020-06-29 00:00:00', '2020-06-29 00:00:00');
INSERT INTO `shorturl` VALUES ('241', 'http://localhost:8089/u/3T', '55', '2020-06-19 00:00:00', '1', '2020-06-19 00:00:00', '2020-06-19 00:00:00');
INSERT INTO `shorturl` VALUES ('301', 'http://localhost:8089/u/4R', '58', '2020-06-19 00:00:00', '1', '2020-06-19 00:00:00', '2020-06-19 00:00:00');
INSERT INTO `shorturl` VALUES ('321', 'http://localhost:8089/u/5b', '59', '2020-06-19 00:00:00', '1', '2020-06-19 00:00:00', '2020-06-19 00:00:00');
INSERT INTO `shorturl` VALUES ('341', 'http://localhost:8089/u/5v', '60', '2020-06-19 00:00:00', '1', '2020-06-19 00:00:00', '2020-06-19 00:00:00');

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
INSERT INTO `sys_permission` VALUES ('310512', null, '业务系统', 'F', null, 'business', '', 'icon-hamburg-folder', '', '', '');
INSERT INTO `sys_permission` VALUES ('310521', '310516', '修改', 'O', null, '', 'business:iigPerson:update', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('310522', '310516', '删除', 'O', null, '', 'business:iigPerson:delete', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('310523', '310516', '导入', 'O', null, '', 'business:iigPerson:import', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('310524', '310516', '导出', 'O', null, '', 'business:iigPerson:export', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('6050', '40', '测试', 'O', null, '', 'system:dict:area', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('1', '29075', '系统管理', 'F', '1', 'rms', ' ', 'icon-standard-cog', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('2', '1', '角色管理', 'F', '3', 'system/role', ' ', 'icon-hamburg-my-account', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('3', '1', '用户管理', 'F', '2', 'system/user', ' ', 'icon-hamburg-user', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('4', '2', '添加', 'O', null, ' ', 'sys:role:add', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('5', '2', '删除', 'O', null, ' ', 'sys:role:delete', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('6', '2', '修改', 'O', null, ' ', 'sys:role:update', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('7', '3', '添加', 'O', null, ' ', 'sys:user:add', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('8', '3', '删除', 'O', null, ' ', 'sys:user:delete', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('12', '1', '权限管理', 'F', '5', 'system/permission', ' ', 'icon-hamburg-login', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('14', '15', '数据源监控', 'F', '6', 'druid', ' ', 'icon-hamburg-database', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('15', '29075', '系统监控', 'F', '999', 'rms', ' ', 'icon-hamburg-graphic', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('16', '3', '修改', 'O', null, ' ', 'sys:user:update', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('20', '15', '日志管理', 'F', '7', 'system/log', ' ', 'icon-hamburg-archives', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('25', '12', '添加', 'O', null, ' ', 'sys:perm:add', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('26', '12', '修改', 'O', null, ' ', 'sys:perm:update', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('27', '12', '删除', 'O', null, ' ', 'sys:perm:delete', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('28', '2', '查看', 'O', null, ' ', 'sys:role:view', ' ', ' ', '', '1050');
INSERT INTO `sys_permission` VALUES ('29', '3', '查看', 'O', null, ' ', 'sys:user:view', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('30', '12', '查看', 'O', null, ' ', 'sys:perm:view', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('31', '20', '删除', 'O', null, ' ', 'sys:log:delete', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('32', '20', '导出excel', 'O', null, ' ', 'sys:log:exportExcel', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('33', '3', '查看用户角色', 'O', null, ' ', 'sys:user:roleView', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('34', '2', '保存授权', 'O', null, ' ', 'sys:role:permUpd', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('35', '3', '修改用户角色', 'O', null, ' ', 'sys:user:roleUpd', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('36', '2', '查看角色权限', 'O', null, ' ', 'sys:role:permView', ' ', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('37', '15', '定时任务管理', 'F', null, 'system/scheduleJob', ' ', 'icon-hamburg-full-time', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('38', '15', 'cron表达式生成', 'F', null, 'system/scheduleJob/quartzCron', ' ', 'icon-hamburg-future', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('39', '1', '菜单管理', 'F', '4', 'system/permission/menu', ' ', 'icon-hamburg-old-versions', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('40', '1', '字典管理', 'F', '6', 'system/dic/dicList', '', 'icon-hamburg-address', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('45', '39', '修改', 'O', null, ' ', 'sys:perm:update', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('58', '39', '添加', 'O', null, ' ', 'sys:perm:add', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('59', '39', '删除', 'O', null, ' ', 'sys:perm:delte', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('61', '40', '添加', 'O', null, ' ', 'sys:dict:add', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('62', '40', '删除', 'O', null, ' ', 'sys:dict:delete', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('63', '40', '修改', 'O', null, ' ', 'sys:dict:update', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('68', '20', '查看', 'O', null, ' ', 'sys:log:view', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('69', '40', '查看', 'O', null, ' ', 'sys:dict:view', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('70', '39', '查看', 'O', null, ' ', 'sys:perm:menu:view', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('9000', '3', '修改密码', 'O', null, '', 'sys:user:updatePassword', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('74', '1', '区域信息', 'F', '7', 'system/area', '', 'icon-hamburg-world', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('75', '1', '单位信息管理', 'F', '8', 'system/organization', '', 'icon-cologne-home', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('76', '3', '查看用户机构', 'O', null, ' ', 'sys:user:orgView', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('77', '3', '修改用户机构', 'O', null, ' ', 'sys:user:orgUpd', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('80', '74', '删除', 'O', null, '', 'system:hh:delete', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('81', '40', 'cc', 'O', null, ' ', 'system:dict:cc', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('5400', '29075', '查询管理', 'F', '14', 'rms', '', 'icon-hamburg-current-work', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('5450', '5400', '静态模版维护', 'F', null, 'comquery/query', '', 'icon-hamburg-config', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('6000', '40', '测试字典', 'O', null, '', 'business:dict:view', '', '', '', '1050');
INSERT INTO `sys_permission` VALUES ('22051', '29075', '公告资讯', 'F', null, '', '', 'icon-hamburg-bug', '', '', '');
INSERT INTO `sys_permission` VALUES ('22052', '22051', '公告管理', 'F', null, 'business/bbs/bbs', '', 'icon-hamburg-business', '', '', '');
INSERT INTO `sys_permission` VALUES ('26000', '3', '重置密码', 'O', null, '', 'sys:user:reset', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('29075', null, '后台管理', 'F', '1', 'a', '', 'icon-hamburg-folder', '', '', '');
INSERT INTO `sys_permission` VALUES ('310520', '310516', '添加', 'O', null, '', 'business:iigPerson:add', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('310511', '1', '用户导入', 'F', '9', 'system/excelImport/userEntry', '', 'icon-hamburg-attibutes', '', '', '');
INSERT INTO `sys_permission` VALUES ('310531', '310530', '添加', 'O', null, '', 'awb:add', '', '', '', '');
INSERT INTO `sys_permission` VALUES ('310540', '310544', '添加', 'O', null, '', 'business:personunit:add', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310541', '310544', '删除', 'O', null, '', 'business:personunit:delete', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310542', '310544', '审核', 'O', null, '', 'business:personunit:examine', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310543', '310544', '修改', 'O', null, '', 'business:personunit:update', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310544', '310545', '用人单位信息', 'F', null, 'business/group/OhEnterpriseInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310545', '310512', '用人单位信息', 'F', '0', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310546', '310547', '农药中毒报告', 'F', null, 'business/group/ohDiseaseReportInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310547', '310512', '农药中毒报告', 'F', '2', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310548', '310512', '职业病监测和管理', 'F', '3', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310549', '310548', '疑似职业病报告', 'F', '1', 'business/group/suspectedZybController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310550', '310548', '职业病病例', 'F', '2', 'business/group/occupationCaseController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310552', '310512', '健康指标管理', 'F', '1', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310553', '310552', '健康指标管理', 'F', '0', 'business/group/ohOrganExamItemRelController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310554', '310548', '职业病鉴定管理', 'F', '3', 'business/group/OhOccuDiseAppraisalInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310555', '310553', '修改', 'O', null, '', 'business:OhOrganExamItemRel:update', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310556', '310553', '查看', 'O', null, '', 'business:OhOrganExamItemRel:view', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310557', '310546', '添加', 'O', null, '', 'business:OhDiseaseReportInfo:add', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310558', '310546', '修改', 'O', null, '', 'business:OhDiseaseReportInfo:update', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310559', '310546', '删除', 'O', null, '', 'business:OhDiseaseReportInfo:delete', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310560', '310546', '审核', 'O', null, '', 'business:OhDiseaseReportInfo:examine', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310561', '310548', '职业健康档案', 'F', '0', 'business/group/ohHealthExamRecordInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310562', '310548', '职业病随访管理', 'F', '5', 'business/group/ohFollowupInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310563', '310512', '健康危害因素检测和管理', 'F', '4', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310564', '310563', '放射工作人员个人剂量监测', 'F', '1', 'business/group/ohRadiationWorkerDoseInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310565', '310563', '放射诊疗监测', 'F', '2', 'business/group/ohRadiationMedicalInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310566', '310563', '职业性有害因素监测管理', 'F', '0', 'business/group/ohFactorDetectionInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310567', '310512', '统计分析', 'F', '5', '', null, 'icon-hamburg-archives', null, '', null);
INSERT INTO `sys_permission` VALUES ('310568', '310567', '职业病报告统计', 'F', '0', 'occupational/statistics/goQuery', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310569', '310567', '农药中毒死亡率', 'F', '2', 'business/statistics/geturl/nyzdswl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310570', '310567', '职业性有害因素检测合格率', 'F', '3', 'business/statistics/geturl/jyxyhysjc', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310571', '310567', '放射工作人员个人剂量监测率', 'F', '4', 'business/statistics/geturl/fsgzrygrjljc', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310572', '310548', '档案浏览', 'F', '6', 'business/group/compositivePreview/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310573', '310572', '查看', 'O', null, '', 'business:preview:see', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310574', '310572', '下载', 'O', null, '', 'business:preview:download', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310575', '310567', '职业病诊断与鉴定符合率', 'F', '5', 'business/statistics/geturl/zybZdyjd', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310576', '310567', '疑似职业病检出数', 'F', '6', 'business/statistics/geturl/yszybjcs', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310577', '310567', '职业健康检查关键指标异常数', 'F', '7', 'business/statistics/geturl/zyjkjcgjzb', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310578', '310563', '医疗照射水平监测', 'F', '4', 'business/group/ohMedicalExposureInfoController/geturl', null, 'icon-hamburg-attibutes', null, '', null);
INSERT INTO `sys_permission` VALUES ('310579', '310544', '导出', 'O', null, '', 'business:personunit:export', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310580', '310546', '导出', 'O', null, '', 'business:personunit:export', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310581', '310544', '查重', 'O', null, '', 'business:personunit:cnki', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310582', '310544', '用人单位信息修改', 'O', null, '', 'business:personunit:yrdwupdate', null, null, '', null);
INSERT INTO `sys_permission` VALUES ('310583', '310567', '疑似职业病汇总统计', 'F', '8', 'business/statistics/occupationbute/toStatistics', null, '', null, '', null);
