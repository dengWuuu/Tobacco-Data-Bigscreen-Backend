/*
 Navicat Premium Data Transfer

 Source Server         : 烟草SQL
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 30/03/2024 19:58:48
*/

PRAGMA
    foreign_keys = false;

-- ----------------------------
-- Table structure for t_goview_project
-- ----------------------------
DROP TABLE IF EXISTS "t_goview_project";
CREATE TABLE "t_goview_project"
(
    "id"             text(50) NOT NULL,
    "project_name"   text(255),
    "state"          integer(1),
    "create_time"    text,
    "create_user_id" text(50),
    "is_delete"      integer(1),
    "index_image"    text(255),
    "remarks"        text(255),
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for t_goview_project_data
-- ----------------------------
DROP TABLE IF EXISTS "t_goview_project_data";
CREATE TABLE "t_goview_project_data"
(
    "id"             text(50) NOT NULL,
    "project_id"     text(50),
    "create_time"    text,
    "create_user_id" text(50),
    "content"        blob,
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS "t_sys_file";
CREATE TABLE "t_sys_file"
(
    "id"            text NOT NULL,
    "file_name"     text,
    "file_size"     integer,
    "file_suffix"   text,
    "create_time"   text DEFAULT '',
    "md5"           text,
    "virtual_key"   text,
    "relative_path" text,
    "absolute_path" text,
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "t_sys_user";
CREATE TABLE "t_sys_user"
(
    "id"       text(255) NOT NULL,
    "username" text(255),
    "password" text(255),
    "nickname" text(255),
    "dep_id"   integer(11),
    "pos_id"   text(255),
    PRIMARY KEY ("id")
);

PRAGMA
    foreign_keys = true;

-- ----------------------------
-- Table structure for t_sku
-- 烟草店sku表
-- ----------------------------
DROP TABLE IF EXISTS "t_spu";
CREATE TABLE "t_spu"
(
    "id"          text(255) NOT NULL,
    "sku_name"    text(255),
    "price"       text(255),
    "image"       text(255),
    "status"      text(255),
    "num"         integer,
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),

    PRIMARY KEY ("id")
);

PRAGMA
    foreign_keys = true;


-- ----------------------------
-- Table structure for t_sell_record
-- 用户消费记录表
-- ----------------------------
DROP TABLE IF EXISTS "t_sell_record";
CREATE TABLE "t_sell_record"
(
    "id"          text(255) NOT NULL,
    "consumer_id" text(255),
    "spu_id"      text(255),
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),
    PRIMARY KEY ("id")
);

PRAGMA
    foreign_keys = true;

-- ----------------------------
-- Table structure for t_consumer
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS "t_consumer";
CREATE TABLE "t_consumer"
(
    "id"          text(255) NOT NULL,
    "name"        text(255),
    "phone"       text(255),
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),
    PRIMARY KEY ("id")
);

PRAGMA
    foreign_keys = true;


-- ----------------------------
-- Table structure for t_shop
-- 店铺表
-- ----------------------------
DROP TABLE IF EXISTS "t_shop";
CREATE TABLE "t_shop"
(
    "id"          text(255) NOT NULL,
    "base"        text(255),
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),
    PRIMARY KEY ("id")
);

PRAGMA
    foreign_keys = true;