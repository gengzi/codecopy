/*
Navicat MySQL Data Transfer

Source Server         : 114.116.21.8
Source Server Version : 50733
Source Host           : 114.116.21.8:3309
Source Database       : codecopy_shardingsphere_goods

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-12-22 16:24:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_0
-- ----------------------------
DROP TABLE IF EXISTS `goods_0`;
CREATE TABLE `goods_0` (
  `id` int(11) unsigned NOT NULL COMMENT 'id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `goods_description_mark_down` longtext COMMENT '描述markdown',
  `goods_description` longtext COMMENT '描述',
  `goods_type` longtext COMMENT '商品类型',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '课程分享图片url',
  `auto_online_time` datetime DEFAULT NULL COMMENT '自动上架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `sales` int(11) DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_1`;
CREATE TABLE `goods_1` (
  `id` int(11) unsigned NOT NULL COMMENT 'id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `goods_description_mark_down` longtext COMMENT '描述markdown',
  `goods_description` longtext COMMENT '描述',
  `goods_type` longtext COMMENT '商品类型',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '课程分享图片url',
  `auto_online_time` datetime DEFAULT NULL COMMENT '自动上架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `sales` int(11) DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_2
-- ----------------------------
DROP TABLE IF EXISTS `goods_2`;
CREATE TABLE `goods_2` (
  `id` int(11) unsigned NOT NULL COMMENT 'id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `goods_description_mark_down` longtext COMMENT '描述markdown',
  `goods_description` longtext COMMENT '描述',
  `goods_type` longtext COMMENT '商品类型',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '课程分享图片url',
  `auto_online_time` datetime DEFAULT NULL COMMENT '自动上架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `sales` int(11) DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_3
-- ----------------------------
DROP TABLE IF EXISTS `goods_3`;
CREATE TABLE `goods_3` (
  `id` int(11) unsigned NOT NULL COMMENT 'id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `goods_description_mark_down` longtext COMMENT '描述markdown',
  `goods_description` longtext COMMENT '描述',
  `goods_type` longtext COMMENT '商品类型',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '课程分享图片url',
  `auto_online_time` datetime DEFAULT NULL COMMENT '自动上架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `sales` int(11) DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
