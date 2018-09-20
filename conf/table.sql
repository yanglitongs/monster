-- ���� wxdb �����ݿ�ṹ
DROP DATABASE IF EXISTS `wxdb`;
CREATE DATABASE IF NOT EXISTS `wxdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wxdb`;
 
 
-- ����  �� wxdb.sys_group �ṹ
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE IF NOT EXISTS `sys_group` (
  `UserGroupID` varchar(50) NOT NULL,
  `UserGroupName` varchar(50) NOT NULL,
  `Remark` varchar(500) NOT NULL,
  `IsLock` tinyint(1) NOT NULL,
  PRIMARY KEY (`UserGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ�û���';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_groupmenu �ṹ
DROP TABLE IF EXISTS `sys_groupmenu`;
CREATE TABLE IF NOT EXISTS `sys_groupmenu` (
  `UserGroupID` varchar(50) NOT NULL,
  `MenuID` varchar(50) NOT NULL,
  PRIMARY KEY (`UserGroupID`,`MenuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_grouppurview �ṹ
DROP TABLE IF EXISTS `sys_grouppurview`;
CREATE TABLE IF NOT EXISTS `sys_grouppurview` (
  `GroupID` varchar(50) NOT NULL,
  `PurviewID` varchar(50) NOT NULL,
  PRIMARY KEY (`GroupID`,`PurviewID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ�û���Ȩ��';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_menu �ṹ
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `MenuID` varchar(50) NOT NULL,
  `MenuName` varchar(50) NOT NULL,
  `MenuUrl` varchar(50) NOT NULL,
  `ParentID` varchar(50) NOT NULL,
  `Remark` varchar(500) NOT NULL,
  `OrderNum` int(11) unsigned NOT NULL,
  `IsLock` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`MenuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳĬ�ϲ˵�';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_purview �ṹ
DROP TABLE IF EXISTS `sys_purview`;
CREATE TABLE IF NOT EXISTS `sys_purview` (
  `PurviewID` varchar(50) NOT NULL,
  `PurviewName` varchar(50) NOT NULL,
  `PurviewValue` varchar(50) NOT NULL,
  `Remark` varchar(500) NOT NULL,
  `IsLock` tinyint(1) NOT NULL,
  PRIMARY KEY (`PurviewID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳĬ��Ȩ��';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_user �ṹ
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `UserID` varchar(50) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `LoginPasswd` varchar(50) NOT NULL,
  `IsCustom` tinyint(1) unsigned NOT NULL COMMENT '�Զ���Ȩ�޻�����Ȩ��',
  `UserGroupID` int(11) unsigned NOT NULL,
  `Remark` varchar(500) NOT NULL,
  `IsLock` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ�û���';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_usermenu �ṹ
DROP TABLE IF EXISTS `sys_usermenu`;
CREATE TABLE IF NOT EXISTS `sys_usermenu` (
  `UserID` varchar(50) NOT NULL,
  `MenuID` varchar(50) NOT NULL,
  PRIMARY KEY (`UserID`,`MenuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û��˵���';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.sys_userpurview �ṹ
DROP TABLE IF EXISTS `sys_userpurview`;
CREATE TABLE IF NOT EXISTS `sys_userpurview` (
  `UserID` varchar(50) NOT NULL,
  `PurviewID` varchar(50) NOT NULL,
  PRIMARY KEY (`UserID`,`PurviewID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ�û�Ȩ��';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_data �ṹ
DROP TABLE IF EXISTS `wx_data`;
CREATE TABLE IF NOT EXISTS `wx_data` (
  `DataID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `DealTime` datetime NOT NULL,
  `GetOrPut` tinyint(1) NOT NULL,
  `DataType` tinyint(1) unsigned NOT NULL,
  `DataContent` text NOT NULL,
  `ToUserName` varchar(50) NOT NULL,
  `FromUserName` varchar(50) NOT NULL,
  `CreateTime` int(11) unsigned NOT NULL,
  `MsgType` varchar(50) NOT NULL,
  `Content` text NOT NULL,
  `MsgId` bigint(20) unsigned NOT NULL,
  `MediaId` varchar(500) NOT NULL,
  `ThumbMediaId` varchar(500) NOT NULL,
  `Format` varchar(50) NOT NULL,
  `Location_X` float unsigned NOT NULL,
  `Location_Y` float unsigned NOT NULL,
  `Scale` int(11) unsigned NOT NULL,
  `Label` varchar(500) NOT NULL,
  `Title` varchar(500) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Url` varchar(500) NOT NULL,
  `PicUrl` varchar(500) NOT NULL,
  `MusicURL` varchar(500) NOT NULL,
  `HQMusicUrl` varchar(500) NOT NULL,
  `Event` varchar(50) NOT NULL,
  `EventKey` varchar(500) NOT NULL,
  `Ticket` varchar(500) NOT NULL,
  `Latitude` float unsigned NOT NULL,
  `Longitude` float unsigned NOT NULL,
  `Precision` float unsigned NOT NULL,
  `Recognition` varchar(500) NOT NULL,
  PRIMARY KEY (`DataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ž��ռ���������';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_dataitem �ṹ
DROP TABLE IF EXISTS `wx_dataitem`;
CREATE TABLE IF NOT EXISTS `wx_dataitem` (
  `DataID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Url` varchar(500) NOT NULL,
  `PicUrl` varchar(500) NOT NULL,
  PRIMARY KEY (`DataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����뷢��������ͼ����Ϣ������';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_filelist �ṹ
DROP TABLE IF EXISTS `wx_filelist`;
CREATE TABLE IF NOT EXISTS `wx_filelist` (
  `FileID` varchar(50) NOT NULL,
  `FileName` varchar(500) NOT NULL,
  `FileType` varchar(50) NOT NULL,
  `FileSource` tinyint(1) unsigned NOT NULL COMMENT '0��Ĭ�ϣ�1����ת����',
  `Title` varchar(500) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `PicURL` varchar(500) NOT NULL,
  `MusicURL` varchar(500) NOT NULL,
  `HQMusicURL` varchar(500) NOT NULL,
  `MediaId` varchar(500) NOT NULL,
  `ThumbMediaId` varchar(500) NOT NULL,
  `Content` text NOT NULL,
  `SourceURL` varchar(500) NOT NULL,
  `NoAdv` tinyint(1) unsigned NOT NULL COMMENT '�Ƿ������棬Ԥ��',
  `IsLock` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`FileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�زı�';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_group �ṹ
DROP TABLE IF EXISTS `wx_group`;
CREATE TABLE IF NOT EXISTS `wx_group` (
  `MpID` varchar(50) NOT NULL,
  `GroupId` int(11) unsigned NOT NULL,
  `GroupName` varchar(50) NOT NULL,
  `Count` int(11) unsigned NOT NULL,
  PRIMARY KEY (`MpID`,`GroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢���û�����';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_keywordlist �ṹ
DROP TABLE IF EXISTS `wx_keywordlist`;
CREATE TABLE IF NOT EXISTS `wx_keywordlist` (
  `KeywordID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `RuleID` varchar(50) NOT NULL,
  `Keyword` varchar(50) NOT NULL,
  `MatchMode` tinyint(1) unsigned NOT NULL COMMENT '0��ȫƥ�䣻1������ƥ��',
  PRIMARY KEY (`KeywordID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ؼ���';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_menu �ṹ
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE IF NOT EXISTS `wx_menu` (
  `MpID` varchar(50) NOT NULL,
  `MenuID` varchar(50) NOT NULL,
  `MenuName` varchar(50) NOT NULL,
  `JSONData` text NOT NULL,
  `Remark` varchar(500) NOT NULL,
  `IsUpload` tinyint(1) NOT NULL COMMENT '�Ƿ��Ѿ��ϴ���Ѷ',
  `IsLock` tinyint(1) NOT NULL,
  PRIMARY KEY (`MenuID`,`MpID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ų˵�';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_menubutton �ṹ
DROP TABLE IF EXISTS `wx_menubutton`;
CREATE TABLE IF NOT EXISTS `wx_menubutton` (
  `MenuButtonID` int(11) NOT NULL,
  `MenuID` varchar(50) NOT NULL,
  `ButtonName` varchar(50) NOT NULL,
  `ButtonType` varchar(50) NOT NULL COMMENT '���� click or view',
  `Target` varchar(50) NOT NULL COMMENT '�����ǹؼ��֣�Ҳ������URL',
  `OrderNum` varchar(50) NOT NULL,
  `ParentID` int(11) NOT NULL,
  PRIMARY KEY (`MenuButtonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ų˵���Ŀ';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_mpset �ṹ
DROP TABLE IF EXISTS `wx_mpset`;
CREATE TABLE IF NOT EXISTS `wx_mpset` (
  `MpID` varchar(50) NOT NULL,
  `WechatID` varchar(50) NOT NULL,
  `NickName` varchar(50) NOT NULL,
  `Type` tinyint(1) unsigned NOT NULL COMMENT '���ĺŻ�����',
  `Introduction` varchar(500) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Avatar` varchar(500) NOT NULL,
  `Token` varchar(50) NOT NULL,
  `Url` varchar(500) NOT NULL,
  PRIMARY KEY (`MpID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ź��ںŻ�������';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_rulefile �ṹ
DROP TABLE IF EXISTS `wx_rulefile`;
CREATE TABLE IF NOT EXISTS `wx_rulefile` (
  `RuleID` varchar(50) NOT NULL,
  `FileID` varchar(50) NOT NULL,
  PRIMARY KEY (`RuleID`,`FileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_ruleslist �ṹ
DROP TABLE IF EXISTS `wx_ruleslist`;
CREATE TABLE IF NOT EXISTS `wx_ruleslist` (
  `RuleID` varchar(50) NOT NULL,
  `RuleName` varchar(50) NOT NULL,
  `RuleType` tinyint(1) unsigned NOT NULL COMMENT 'Ĭ��Ϊ0��Ԥ��',
  `Source` tinyint(1) unsigned NOT NULL COMMENT '0���ֹ�������1��ϵͳ����',
  `KeyWordList` varchar(500) NOT NULL,
  `ReplyType` tinyint(1) unsigned NOT NULL COMMENT '0��ȫ���ظ���1������ظ�',
  `EffectiveDate` datetime NOT NULL,
  `ExpiryDate` datetime NOT NULL,
  `DataLabel` varchar(50) NOT NULL COMMENT 'Ϊͳ�Ʊ���Ԥ��',
  `IsLock` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`RuleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Źؼ����Զ��ظ�����';
 
-- ���ݵ�����ȡ��ѡ��
 
 
-- ����  �� wxdb.wx_userlist �ṹ
DROP TABLE IF EXISTS `wx_userlist`;
CREATE TABLE IF NOT EXISTS `wx_userlist` (
  `MpID` varchar(50) NOT NULL,
  `UserID` varchar(50) NOT NULL COMMENT '�û���OPENID',
  `UserName` varchar(50) NOT NULL COMMENT '΢�źţ�Ԥ��',
  `NickName` varchar(50) NOT NULL,
  `RemarkName` varchar(50) NOT NULL COMMENT '��ע����Ԥ��',
  `City` varchar(50) NOT NULL,
  `Province` varchar(50) NOT NULL,
  `Country` varchar(50) NOT NULL,
  `Gender` tinyint(1) unsigned NOT NULL COMMENT '0��δʶ��1���У�2��Ů',
  `GroupId` int(11) unsigned NOT NULL,
  `UserState` tinyint(1) unsigned NOT NULL COMMENT '1�����ģ�0���˳�',
  PRIMARY KEY (`MpID`,`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢���û��б�';
 








-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `cname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cid` int(11) NOT NULL,
  `cperson` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cphone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cemail` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cwork` int(255) DEFAULT NULL,
  `ccid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ccid`),
  KEY `cwork` (`cwork`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('���������޹�˾', '4', '������', '18838181046', 'zhang@qq.com', '1', '1');
INSERT INTO `company` VALUES ('��������', '1', '˧��', '123', 'jaskldjf@qq.com', '2', '2');
INSERT INTO `company` VALUES ('����ú̿����', '4', '����Դ����', '188278', 'asdf@sina.com', '3', '3');
INSERT INTO `company` VALUES ('��ɫ��Ƽ�', '4', '�ź���', '123', 'zhanghanwen@qq.com', '4', '4');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cid` int(11) NOT NULL,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '���Ŀ�');
INSERT INTO `course` VALUES ('2', '��ѧ��');
INSERT INTO `course` VALUES ('3', 'Ӣ���');
INSERT INTO `course` VALUES ('4', '��ѧ��');
INSERT INTO `course` VALUES ('5', '�����');

-- ----------------------------
-- Table structure for course_student
-- ----------------------------
DROP TABLE IF EXISTS `course_student`;
CREATE TABLE `course_student` (
  `cid` int(11) NOT NULL,
  `sid` int(11) DEFAULT NULL,
  KEY `cid` (`cid`),
  KEY `sid` (`sid`),
  CONSTRAINT `course_student_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`),
  CONSTRAINT `course_student_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_student
-- ----------------------------
INSERT INTO `course_student` VALUES ('1', '1');
INSERT INTO `course_student` VALUES ('5', '1');
INSERT INTO `course_student` VALUES ('1', '2');
INSERT INTO `course_student` VALUES ('2', '2');
INSERT INTO `course_student` VALUES ('3', '2');
INSERT INTO `course_student` VALUES ('4', '2');
INSERT INTO `course_student` VALUES ('1', '6');
INSERT INTO `course_student` VALUES ('1', '5');
INSERT INTO `course_student` VALUES ('3', '5');
INSERT INTO `course_student` VALUES ('4', '5');
INSERT INTO `course_student` VALUES ('1', '7');
INSERT INTO `course_student` VALUES ('4', '7');
INSERT INTO `course_student` VALUES ('2', '8');
INSERT INTO `course_student` VALUES ('5', '8');
INSERT INTO `course_student` VALUES ('2', '4');
INSERT INTO `course_student` VALUES ('5', '4');
INSERT INTO `course_student` VALUES ('3', '1');
INSERT INTO `course_student` VALUES ('4', '1');
INSERT INTO `course_student` VALUES ('1', '3');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `did` int(11) NOT NULL,
  `dname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', 'Сhahah����');
INSERT INTO `dept` VALUES ('2', '�Ļ�����');
INSERT INTO `dept` VALUES ('3', 'С����');

-- ----------------------------
-- Table structure for hangye
-- ----------------------------
DROP TABLE IF EXISTS `hangye`;
CREATE TABLE `hangye` (
  `cwork` int(11) DEFAULT NULL,
  `workname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of hangye
-- ----------------------------
INSERT INTO `hangye` VALUES ('1', 'ҽ��');
INSERT INTO `hangye` VALUES ('2', '����');
INSERT INTO `hangye` VALUES ('3', 'ú̿');
INSERT INTO `hangye` VALUES ('4', '����');

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `pname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `psex` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pmanager` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pphone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pemail` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `ccid` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('��ٻ', '0', 'ceo', '999', '555@qq.com', '1', '1');
INSERT INTO `person` VALUES ('������1', '0', 'ceo', '999', '555@qq.com', '2', '1');
INSERT INTO `person` VALUES ('СС��', '0', 'cfo', '999', '122@qq.com', '3', '2');
INSERT INTO `person` VALUES ('˧��', '1', '�ܼ�', '333', 'shuaibi@qq.com', '4', '2');
INSERT INTO `person` VALUES ('�Ա�ɽ��', '1', 'ϲ����Ա', '1883818', 'zhaobenshan@qq.com', '5', '3');
INSERT INTO `person` VALUES ('����', '1', '��ʦ', '991', 'yiyue@qq.com', '6', '3');
INSERT INTO `person` VALUES ('������1', '0', 'ceo', '999', '555@qq.com', '7', '1');
INSERT INTO `person` VALUES ('asdf', '1', 'sadf', '1233', 'asdf', '9', '2');
INSERT INTO `person` VALUES ('���ĵܵ�', '1', 'ceo', '123', '789@qq.com', '10', '4');
INSERT INTO `person` VALUES ('����', '1', 'cfo', '789', '789@q.com', '11', '4');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sid` int(11) NOT NULL,
  `sno` int(11) DEFAULT NULL,
  `sname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '1005', '����Դa');
INSERT INTO `student` VALUES ('2', '1002', '������a');
INSERT INTO `student` VALUES ('3', '1003', '�ź���');
INSERT INTO `student` VALUES ('4', '1004', '������');
INSERT INTO `student` VALUES ('5', '1001', '������a');
INSERT INTO `student` VALUES ('6', '1006', '˧��');
INSERT INTO `student` VALUES ('7', '1007', '����a');
INSERT INTO `student` VALUES ('8', '1008', '��Ǭ');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `did` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '�ŷ�1', '3');
INSERT INTO `users` VALUES ('2', '���ĵܵ�1', '3');
INSERT INTO `users` VALUES ('3', '����1', '2');
INSERT INTO `users` VALUES ('4', '��miu����1', '2');
INSERT INTO `users` VALUES ('6', '����fff1', '1');
INSERT INTO `users` VALUES ('7', 'С����1', '2');
INSERT INTO `users` VALUES ('8', 'С����1', '2');
INSERT INTO `users` VALUES ('9', 'С����fff1', '2');
INSERT INTO `users` VALUES ('11', 'С��lk��1', '2');
INSERT INTO `users` VALUES ('12', '����������������������', '1');
INSERT INTO `users` VALUES ('13', '��������', '1');
INSERT INTO `users` VALUES ('14', 'test�����ɹ�û', '1');
INSERT INTO `users` VALUES ('15', 'С��������222', '3');
INSERT INTO `users` VALUES ('17', '��������', '3');
INSERT INTO `users` VALUES ('23', '��ð�', '1');

-- ----------------------------
-- Table structure for xingzhi
-- ----------------------------
DROP TABLE IF EXISTS `xingzhi`;
CREATE TABLE `xingzhi` (
  `cid` int(11) NOT NULL,
  `xingzhi` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of xingzhi
-- ----------------------------
INSERT INTO `xingzhi` VALUES ('1', '������ҵ');
INSERT INTO `xingzhi` VALUES ('2', '���пع���ҵ');
INSERT INTO `xingzhi` VALUES ('3', '������ҵ');
INSERT INTO `xingzhi` VALUES ('4', '˽Ӫ��ҵ');




-- ���ݵ�����ȡ��ѡ��
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;