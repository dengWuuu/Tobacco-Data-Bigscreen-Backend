/*
 Navicat Premium Data Transfer

 Source Server         : 烟草SQL
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 01/04/2024 20:45:21
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for t_consumer
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
-- Table structure for t_sell_record
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

-- ----------------------------
-- Table structure for t_shop
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

-- ----------------------------
-- Table structure for t_spu
-- ----------------------------
DROP TABLE IF EXISTS "t_spu";
CREATE TABLE "t_spu"
(
    "type"           integer,
    "id"             text(255) NOT NULL,
    "sku_name"       text(255),
    "price"          integer(255),
    "image"          text,
    "status"         text(255),
    "num"            integer,
    "create_time"    text(255),
    "update_time"    text(255),
    "create_user"    text(255),
    "update_user"    text(255),
    "purchase_price" integer,
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

PRAGMA foreign_keys = true;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "t_type";
CREATE TABLE "t_type"
(
    "id"          text(255) NOT NULL,
    "name"        text(255),
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),
    PRIMARY KEY ("id")
);

PRAGMA foreign_keys = true;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "t_spu_type_union";
CREATE TABLE "t_spu_type_union"
(
    "id"          text(255) NOT NULL,
    "type_id"     text(255),
    "spu_id"      text(255),
    "create_time" text(255),
    "update_time" text(255),
    "create_user" text(255),
    "update_user" text(255),
    PRIMARY KEY ("id")
);

PRAGMA foreign_keys = true;

