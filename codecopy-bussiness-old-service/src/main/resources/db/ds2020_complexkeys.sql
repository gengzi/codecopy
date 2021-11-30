/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : ds2020

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-07-03 17:44:54
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

-- ----------------------------
-- Table structure for t_bussiness_date1
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_date1`;
CREATE TABLE `t_bussiness_date1` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_date1
-- ----------------------------
INSERT INTO `t_bussiness_date1` VALUES ('485147064930906112', null, null, null, null, '2020-01-01 00:00:00', '2020-01-01 00:00:00', '0', null, null);
INSERT INTO `t_bussiness_date1` VALUES ('485462422405492736', 'xx', 'xx', 'xx', 'xx', '2020-01-23 00:00:00', '2020-01-23 00:00:00', '0', 'xx', '1');

-- ----------------------------
-- Table structure for t_bussiness_date2
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_date2`;
CREATE TABLE `t_bussiness_date2` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_date2
-- ----------------------------

-- ----------------------------
-- Table structure for t_bussiness_region_est1
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_region_est1`;
CREATE TABLE `t_bussiness_region_est1` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_region_est1
-- ----------------------------
INSERT INTO `t_bussiness_region_est1` VALUES ('485147064930906112', null, null, null, null, '2020-01-01 00:00:00', '2020-01-01 00:00:00', '0', null, null);
INSERT INTO `t_bussiness_region_est1` VALUES ('485462422405492736', 'xx', 'xx', 'xx', 'xx', '2020-01-23 00:00:00', '2020-01-23 00:00:00', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_est1` VALUES ('485847877701578752', 'xx', 'xx', 'xx', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_est1` VALUES ('485847956541911041', 'xx', 'xx', 'xx', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');

-- ----------------------------
-- Table structure for t_bussiness_region_est2
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_region_est2`;
CREATE TABLE `t_bussiness_region_est2` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_region_est2
-- ----------------------------
INSERT INTO `t_bussiness_region_est2` VALUES ('485147064930906112', null, null, null, null, '2020-01-01 00:00:00', '2020-01-01 00:00:00', '0', null, null);
INSERT INTO `t_bussiness_region_est2` VALUES ('485462422405492736', 'xx', 'xx', 'xx', 'xx', '2020-01-23 00:00:00', '2020-01-23 00:00:00', '0', 'xx', '1');

-- ----------------------------
-- Table structure for t_bussiness_region_west1
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_region_west1`;
CREATE TABLE `t_bussiness_region_west1` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_region_west1
-- ----------------------------
INSERT INTO `t_bussiness_region_west1` VALUES ('485147064930906112', null, null, null, null, '2020-01-01 00:00:00', '2020-01-01 00:00:00', '0', null, null);
INSERT INTO `t_bussiness_region_west1` VALUES ('485462422405492736', 'xx', 'xx', 'xx', 'xx', '2020-01-23 00:00:00', '2020-01-23 00:00:00', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_west1` VALUES ('485847877701578752', 'xx', 'xx', 'xx', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_west1` VALUES ('485847956541911041', 'xx', 'xx', 'xx', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_west1` VALUES ('485850717765480448', 'xx', 'xx', 'west', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');
INSERT INTO `t_bussiness_region_west1` VALUES ('485850931536572417', 'xx', 'xx', 'west', 'xx', '2020-01-23 16:10:26', '2020-06-23 16:10:26', '0', 'xx', '1');

-- ----------------------------
-- Table structure for t_bussiness_region_west2
-- ----------------------------
DROP TABLE IF EXISTS `t_bussiness_region_west2`;
CREATE TABLE `t_bussiness_region_west2` (
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_id` (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bussiness_region_west2
-- ----------------------------
INSERT INTO `t_bussiness_region_west2` VALUES ('485147064930906112', null, null, null, null, '2020-01-01 00:00:00', '2020-01-01 00:00:00', '0', null, null);
INSERT INTO `t_bussiness_region_west2` VALUES ('485462422405492736', 'xx', 'xx', 'xx', 'xx', '2020-01-23 00:00:00', '2020-01-23 00:00:00', '0', 'xx', '1');
