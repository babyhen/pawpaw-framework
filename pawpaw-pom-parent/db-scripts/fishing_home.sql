/*
Navicat MySQL Data Transfer

Source Server         : 开发mysql
Source Server Version : 80012
Source Host           : 192.168.179.128:3306
Source Database       : fishing_home

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-09-14 23:22:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `source` int(2) NOT NULL COMMENT '用户来源 1 自有，2微信',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `weixin_open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户标识，如果是',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `gender` int(1) DEFAULT NULL COMMENT '性别  1男 ，2女',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态， 1 正常，2注销',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '用户类型，1 正常 2虚拟',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `inx_mobile` (`mobile`),
  UNIQUE KEY `inx_weixin_open_id` (`weixin_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
