/*
Navicat MySQL Data Transfer

Source Server         : 114.116.21.8
Source Server Version : 50733
Source Host           : 114.116.21.8:3309
Source Database       : codecopy_shardingsphere

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-12-06 11:07:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `goods_description_mark_down` longtext COMMENT '描述markdown',
  `goods_description` longtext COMMENT '描述',
  `goods_type` longtext COMMENT '商品类型',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `auto_online_time` datetime DEFAULT NULL COMMENT '自动上架时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `sales` int(11) DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号(唯一)',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `product_id` int(11) DEFAULT NULL COMMENT '商品唯一标识(ID)',
  `product_name` varchar(128) DEFAULT NULL COMMENT '产品名称',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '金额,单位元',
  `count` int(11) DEFAULT '1' COMMENT '商品数量',
  `channel` varchar(16) NOT NULL COMMENT '支付渠道：weChat-微信支付，aliPay-支付宝支付,applePay-苹果支付',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：1-未支付 2-支付成功 3-支付失败 -1-订单失效',
  `client_ip` varchar(16) NOT NULL DEFAULT '127.0.0.1' COMMENT '用户支付IP',
  `buy_id` varchar(128) DEFAULT NULL COMMENT '购买账号id',
  `trade_no` varchar(128) NOT NULL COMMENT '支付交易号',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `extra` text COMMENT '附加字段，KV json，例如:{"mobile":13020202,"success_url":123}',
  `goods_order_no` varchar(64) DEFAULT NULL COMMENT '商品订单编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_order_no` (`order_no`) USING BTREE,
  KEY `idx_user_id_order_no` (`user_id`,`order_no`) USING BTREE,
  KEY `idx_created_time` (`created_time`) USING BTREE,
  KEY `idx_user_id_product_id_status` (`user_id`,`product_id`,`status`) USING BTREE COMMENT '查询用户购买信息',
  KEY `idx_goods_order_no` (`goods_order_no`) USING BTREE COMMENT '根据商品订单编号来查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付订单信息表';

-- ----------------------------
-- Table structure for pay_order_record
-- ----------------------------
DROP TABLE IF EXISTS `pay_order_record`;
CREATE TABLE `pay_order_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `user_no` varchar(32) NOT NULL COMMENT '用户id',
  `type` varchar(16) NOT NULL COMMENT '操作类型：CREATE|PAY|REFUND...',
  `from_status` varchar(16) DEFAULT NULL COMMENT '订单状态',
  `paid_amount` int(11) DEFAULT '0' COMMENT '实付金额，单位为分',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(64) NOT NULL COMMENT '支付人',
  `created_at` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付信息表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '用户昵称',
  `portrait` varchar(255) DEFAULT NULL COMMENT '用户头像地址',
  `phone` varchar(255) NOT NULL COMMENT '注册手机',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码（可以为空，支持只用验证码注册、登录）',
  `reg_ip` varchar(255) DEFAULT NULL COMMENT '注册ip',
  `account_non_expired` bit(1) DEFAULT b'1' COMMENT '是否有效用户',
  `credentials_non_expired` bit(1) DEFAULT b'1' COMMENT '账号是否未过期',
  `account_non_locked` bit(1) DEFAULT b'1' COMMENT '是否未锁定',
  `status` varchar(20) NOT NULL DEFAULT 'ENABLE' COMMENT '用户状态：ENABLE能登录，DISABLE不能登录',
  `is_del` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime NOT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_phone_is_del` (`phone`,`is_del`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
