




CREATE TABLE `blog` (
  `id` char(32) NOT NULL COMMENT '主键，UUID作为键值',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `author` varchar(50) NOT NULL COMMENT '作者',
  `summary` text NOT NULL COMMENT '摘要',
  `content` text NOT NULL COMMENT 'markdown格式的内容，用于重新编辑',
  `commentNum` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `heartNum` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `readNum` int(11) NOT NULL DEFAULT '0' COMMENT '阅读数',
  `publishTime` datetime NOT NULL COMMENT '文章创建时间',
  `url` varchar(100) NOT NULL COMMENT '文章相对URL',
  `signature` char(32) NOT NULL COMMENT '文章哈希签名，防止出现重复文章',
  `categoryID` char(32) NOT NULL COMMENT '文章类别ID',
  `lastUpdateTime` datetime NOT NULL COMMENT '文章上次修改时间',
  `path` varchar(100) NOT NULL COMMENT '静态化的文件磁盘路径',
  `coverURL` varchar(255) DEFAULT NULL COMMENT '封面图片',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '博文类型，0表示普通博文，1表示富博文(带封面图片)',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '博文状态，0显示，1不显示，2草稿',
  `statusName` varchar(20) DEFAULT NULL COMMENT '状态名称：显示，不显示，草稿',
  `html` text NOT NULL COMMENT 'html格式的博文，主要用于重新静态化',
  `tags` varchar(256) DEFAULT NULL COMMENT '博文标签',
  `shareNum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `blog_tags` (
  `tagID` char(32) NOT NULL,
  `blogID` char(32) NOT NULL,
  PRIMARY KEY (`tagID`,`blogID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `category` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `cdate` datetime DEFAULT NULL,
  `typeID` int(11) DEFAULT '1',
  `keywords` varchar(50) DEFAULT '',
  `blogNum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `comment` (
  `id` char(32) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `cdate` datetime NOT NULL,
  `likeNum` int(11) NOT NULL DEFAULT '0',
  `hateNum` int(11) NOT NULL DEFAULT '0',
  `parent` char(32) DEFAULT NULL,
  `blogID` char(32) NOT NULL,
  `shareNum` int(11) NOT NULL DEFAULT '0',
  `replyNum` int(11) NOT NULL DEFAULT '0',
  `headURL` varchar(100) DEFAULT NULL,
  `check` int(11) DEFAULT '0' COMMENT '是否审核，0表示未审核，1表示审核',
  `status` int(11) DEFAULT '1' COMMENT '是否合法，0未通过，1通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


CREATE TABLE `notice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0,不可见，1可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tags` (
  `id` char(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `cdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `timeline` (
  `id` varchar(20) NOT NULL COMMENT '主键',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `displayName` varchar(30) NOT NULL COMMENT '显示名称',
  `displayDate` datetime DEFAULT NULL COMMENT '显示日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `cdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nickname` varchar(30) DEFAULT '游客',
  `headurl` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `headurl`)
VALUES
(1,'admin','E10ADC3949BA59ABBE56E057F20F883E','admin','n');
UNLOCK TABLES;


CREATE TABLE `youlian` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `cdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE VIEW `blog_display`
AS SELECT
   `category`.`name` AS `categoryName`,
   `blog`.`id` AS `id`,
   `blog`.`title` AS `title`,
   `blog`.`author` AS `author`,
   `blog`.`summary` AS `summary`,
   `blog`.`commentNum` AS `commentNum`,
   `blog`.`readNum` AS `readNum`,
   `blog`.`url` AS `url`,
   `blog`.`publishTime` AS `publishTime`,
   `blog`.`type` AS `type`,
   `blog`.`status` AS `status`,
   `blog`.`coverURL` AS `coverURL`,
   `blog`.`heartNum` AS `heartNum`,
   `blog`.`tags` AS `tags`,
   `type`.`name` AS `typeName`,
   `type`.`id` AS `typeID`,
   `category`.`id` AS `categoryID`,
   `blog`.`content` AS `content`
FROM ((`category` join `blog`) join `type`) where ((`category`.`id` = `blog`.`categoryID`) and (`category`.`typeID` = `type`.`id`));


CREATE VIEW `blog_back_display`
AS SELECT
   `category`.`name` AS `categoryName`,
   `blog`.`id` AS `id`,
   `blog`.`title` AS `title`,
   `blog`.`author` AS `author`,
   `blog`.`commentNum` AS `commentNum`,
   `blog`.`readNum` AS `readNum`,
   `blog`.`url` AS `url`,
   `blog`.`publishTime` AS `publishTime`,
   `blog`.`type` AS `type`,
   `blog`.`status` AS `status`,
   `blog`.`statusName` AS `statusName`,
   `blog`.`heartNum` AS `heartNum`
FROM (`category` join `blog`) where (`category`.`id` = `blog`.`categoryID`);


CREATE VIEW `blog_display_by_tag`
AS SELECT
   `category`.`name` AS `categoryName`,
   `blog`.`id` AS `id`,
   `blog`.`title` AS `title`,
   `blog`.`author` AS `author`,
   `blog`.`summary` AS `summary`,
   `blog`.`commentNum` AS `commentNum`,
   `blog`.`readNum` AS `readNum`,
   `blog`.`url` AS `url`,
   `blog`.`publishTime` AS `publishTime`,
   `blog`.`type` AS `type`,
   `blog`.`status` AS `status`,
   `blog`.`coverURL` AS `coverURL`,
   `blog`.`heartNum` AS `heartNum`,
   `blog`.`tags` AS `tags`,
   `type`.`name` AS `typeName`,
   `type`.`id` AS `typeID`,
   `category`.`id` AS `categoryID`,
   `tags`.`id` AS `tagID`,
   `tags`.`name` AS `tagName`
FROM ((((`category` join `blog`) join `type`) join `blog_tags`) join `tags`) where ((`category`.`id` = `blog`.`categoryID`) and (`category`.`typeID` = `type`.`id`) and (`blog_tags`.`blogID` = `blog`.`id`) and (`blog_tags`.`tagID` = `tags`.`id`));

