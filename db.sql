

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` char(32) NOT NULL COMMENT 'uuid\r\n            ',
  `login_act` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `login_pwd` varchar(255) DEFAULT NULL COMMENT '密码不能采用明文存储，采用密文，MD5加密之后的数据',
  `email` varchar(255) DEFAULT NULL,
  `expire_time` char(19) DEFAULT NULL COMMENT '失效时间为空的时候表示永不失效，失效时间为2018-10-10 10:10:10，则表示在该时间之前该账户可用。',
  `lock_state` char(1) DEFAULT NULL COMMENT '锁定状态为空时表示启用，为0时表示锁定，为1时表示启用。',
  `deptno` char(4) DEFAULT NULL,
  `allow_ips` varchar(255) DEFAULT NULL COMMENT '允许访问的IP为空时表示IP地址永不受限，允许访问的IP可以是一个，也可以是多个，当多个IP地址的时候，采用半角逗号分隔。允许IP是192.168.100.2，表示该用户只能在IP地址为192.168.100.2的机器上使用。',
  `createTime` char(19) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `edit_time` char(19) DEFAULT NULL,
  `edit_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('06f5fc056eac41558a964f96daa7f27c', 'ls', '李四', 'yf123', 'ls@163.com', '2111-11-27 21:50:05', '1', 'A001', '192.168.1.1,0:0:0:0:0:0:0:1', '2018-11-22 12:11:40', '李四', null, null);
INSERT INTO `tbl_user` VALUES ('40f6cdea0bd34aceb77492a1656d9fb3', 'zs', '张三', 'yf123', 'zs@qq.com', '2118-11-30 23:50:55', '1', 'A001', '192.168.1.1,192.168.1.2,127.0.0.1,0:0:0:0:0:0:0:1', '2018-11-22 11:37:34', '张三', null, null);




drop table if exists tbl_dic_type;

drop table if exists tbl_dic_value;

/*==============================================================*/
/* Table: tbl_dic_type                                          */
/*==============================================================*/
create table tbl_dic_type
(
   code                 varchar(255) not null comment '编码是主键，不能为空，不能含有中文。',
   name                 varchar(255),
   description          varchar(255),
   primary key (code)
);

/*==============================================================*/
/* Table: tbl_dic_value                                         */
/*==============================================================*/
create table tbl_dic_value
(
   id                   char(32) not null comment '主键，采用UUID',
   value                varchar(255) comment '不能为空，并且要求同一个字典类型下字典值不能重复，具有唯一性。',
   text                 varchar(255) comment '可以为空',
   order_no             varchar(255) comment '可以为空，但不为空的时候，要求必须是正整数',
   type_code            varchar(255) comment '外键',
   primary key (id)
);

INSERT INTO `tbl_dic_type` VALUES 
('appellation','称呼',''),
('clueState','线索状态',''),
('returnPriority','回访优先级',''),
('returnState','回访状态',''),
('source','来源',''),
('stage','阶段',''),
('transactionType','交易类型','');

INSERT INTO `tbl_dic_value` VALUES 
('06e3cbdf10a44eca8511dddfc6896c55','虚假线索','虚假线索',4,'clueState'),
('0fe33840c6d84bf78df55d49b169a894','销售邮件','销售邮件',8,'source'),
('12302fd42bd349c1bb768b19600e6b20','交易会','交易会',11,'source'),
('1615f0bb3e604552a86cde9a2ad45bea','最高','最高',2,'returnPriority'),
('176039d2a90e4b1a81c5ab8707268636','教授','教授',5,'appellation'),
('1e0bd307e6ee425599327447f8387285','将来联系','将来联系',2,'clueState'),
('2173663b40b949ce928db92607b5fe57','丢失线索','丢失线索',5,'clueState'),
('2876690b7e744333b7f1867102f91153','未启动','未启动',1,'returnState'),
('29805c804dd94974b568cfc9017b2e4c','成交','成交',7,'stage'),
('310e6a49bd8a4962b3f95a1d92eb76f4','试图联系','试图联系',1,'clueState'),
('31539e7ed8c848fc913e1c2c93d76fd1','博士','博士',4,'appellation'),
('37ef211719134b009e10b7108194cf46','资质审查','资质审查',1,'stage'),
('391807b5324d4f16bd58c882750ee632','丢失的线索','丢失的线索',8,'stage'),
('3a39605d67da48f2a3ef52e19d243953','聊天','聊天',14,'source'),
('474ab93e2e114816abf3ffc596b19131','低','低',3,'returnPriority'),
('48512bfed26145d4a38d3616e2d2cf79','广告','广告',1,'source'),
('4d03a42898684135809d380597ed3268','合作伙伴研讨会','合作伙伴研讨会',9,'source'),
('59795c49896947e1ab61b7312bd0597c','先生','先生',1,'appellation'),
('5c6e9e10ca414bd499c07b886f86202a','高','高',1,'returnPriority'),
('67165c27076e4c8599f42de57850e39c','夫人','夫人',2,'appellation'),
('68a1b1e814d5497a999b8f1298ace62b','因竞争丢失关闭','因竞争丢失关闭',9,'stage'),
('6b86f215e69f4dbd8a2daa22efccf0cf','web调研','web调研',13,'source'),
('72f13af8f5d34134b5b3f42c5d477510','合作伙伴','合作伙伴',6,'source'),
('7c07db3146794c60bf975749952176df','未联系','未联系',6,'clueState'),
('86c56aca9eef49058145ec20d5466c17','内部研讨会','内部研讨会',10,'source'),
('9095bda1f9c34f098d5b92fb870eba17','进行中','进行中',3,'returnState'),
('954b410341e7433faa468d3c4f7cf0d2','已有业务','已有业务',1,'transactionType'),
('966170ead6fa481284b7d21f90364984','已联系','已联系',3,'clueState'),
('96b03f65dec748caa3f0b6284b19ef2f','推迟','推迟',2,'returnState'),
('97d1128f70294f0aac49e996ced28c8a','新业务','新业务',2,'transactionType'),
('9ca96290352c40688de6596596565c12','完成','完成',4,'returnState'),
('9e6d6e15232549af853e22e703f3e015','需要条件','需要条件',7,'clueState'),
('9ff57750fac04f15b10ce1bbb5bb8bab','需求分析','需求分析',2,'stage'),
('a70dc4b4523040c696f4421462be8b2f','等待某人','等待某人',5,'returnState'),
('a83e75ced129421dbf11fab1f05cf8b4','推销电话','推销电话',2,'source'),
('ab8472aab5de4ae9b388b2f1409441c1','常规','常规',5,'returnPriority'),
('ab8c2a3dc05f4e3dbc7a0405f721b040','提案/报价','提案/报价',5,'stage'),
('b924d911426f4bc5ae3876038bc7e0ad','web下载','web下载',12,'source'),
('c13ad8f9e2f74d5aa84697bb243be3bb','价值建议','价值建议',3,'stage'),
('c83c0be184bc40708fd7b361b6f36345','最低','最低',4,'returnPriority'),
('db867ea866bc44678ac20c8a4a8bfefb','员工介绍','员工介绍',3,'source'),
('e44be1d99158476e8e44778ed36f4355','确定决策者','确定决策者',4,'stage'),
('e5f383d2622b4fc0959f4fe131dafc80','女士','女士',3,'appellation'),
('e81577d9458f4e4192a44650a3a3692b','谈判/复审','谈判/复审',6,'stage'),
('fb65d7fdb9c6483db02713e6bc05dd19','在线商场','在线商场',5,'source'),
('fd677cc3b5d047d994e16f6ece4d3d45','公开媒介','公开媒介',7,'source'),
('ff802a03ccea4ded8731427055681d48','外部介绍','外部介绍',4,'source');






drop table if exists tbl_clue;

drop table if exists tbl_clue_activity_relation;

drop table if exists tbl_clue_remark;

drop table if exists tbl_contacts;

drop table if exists tbl_contacts_activity_relation;

drop table if exists tbl_contacts_remark;

drop table if exists tbl_customer;

drop table if exists tbl_customer_remark;

drop table if exists tbl_tran;

drop table if exists tbl_tran_history;

drop table if exists tbl_tran_remark;

/*==============================================================*/
/* Table: tbl_clue                                              */
/*==============================================================*/
create table tbl_clue
(
   id                   char(32) not null,
   fullname             varchar(255),
   appellation          varchar(255),
   owner                char(32),
   company              varchar(255),
   job                  varchar(255),
   email                varchar(255),
   phone                varchar(255),
   website              varchar(255),
   mphone               varchar(255),
   state                varchar(255),
   source               varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   description          varchar(255),
   contact_summary       varchar(255),
   next_contact_time      char(10),
   address              varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_clue_activity_relation                            */
/*==============================================================*/
create table tbl_clue_activity_relation
(
   id                   char(32) not null,
   clue_id               char(32),
   activity_id           char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_clue_remark                                       */
/*==============================================================*/
create table tbl_clue_remark
(
   id                   char(32) not null,
   note_content          varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   edit_flag             char(1),
   clue_id               char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_contacts                                          */
/*==============================================================*/
create table tbl_contacts
(
   id                   char(32) not null,
   owner                char(32),
   source               varchar(255),
   customer_id           char(32),
   fullname             varchar(255),
   appellation          varchar(255),
   email                varchar(255),
   mphone               varchar(255),
   job                  varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   description          varchar(255),
   contact_summary       varchar(255),
   next_contact_time      char(10),
   address              varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_contacts_activity_relation                        */
/*==============================================================*/
create table tbl_contacts_activity_relation
(
   id                   char(32) not null,
   contacts_id           char(32),
   activity_id           char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_contacts_remark                                   */
/*==============================================================*/
create table tbl_contacts_remark
(
   id                   char(32) not null,
   note_content          varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   edit_flag             char(1),
   contacts_id           char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_customer                                          */
/*==============================================================*/
create table tbl_customer
(
   id                   char(32) not null,
   owner                char(32),
   name                 varchar(255),
   website              varchar(255),
   phone                varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   contact_summary       varchar(255),
   next_contact_time      char(10),
   description          varchar(255),
   address              varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_customer_remark                                   */
/*==============================================================*/
create table tbl_customer_remark
(
   id                   char(32) not null,
   note_content          varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   edit_flag             char(1),
   customer_id           char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_tran                                              */
/*==============================================================*/
create table tbl_tran
(
   id                   char(32) not null,
   owner                char(32),
   money                varchar(255),
   name                 varchar(255),
   expected_date         char(10),
   customer_id           char(32),
   stage                varchar(255),
   type                 varchar(255),
   source               varchar(255),
   activity_id           char(32),
   contacts_id           char(32),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   description          varchar(255),
   contact_summary       varchar(255),
   next_contact_time      char(10),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_tran_history                                      */
/*==============================================================*/
create table tbl_tran_history
(
   id                   char(32) not null,
   stage                varchar(255),
   money                varchar(255),
   expected_date         char(10),
   create_time           char(19),
   create_by             varchar(255),
   tran_id               char(32),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_tran_remark                                       */
/*==============================================================*/
create table tbl_tran_remark
(
   id                   char(32) not null,
   note_content          varchar(255),
   create_by             varchar(255),
   create_time           char(19),
   edit_by               varchar(255),
   edit_time             char(19),
   edit_flag             char(1),
   tran_id               char(32),
   primary key (id)
);




drop table if exists tbl_activity;

drop table if exists tbl_activity_remark;

/*==============================================================*/
/* Table: tbl_activity                                          */
/*==============================================================*/
create table tbl_activity
(
   id                   char(32) not null,
   owner                char(32),
   name                 varchar(255),
   start_date            char(10),
   end_date              char(10),
   cost                 varchar(255),
   description          varchar(255),
   create_time           char(19),
   create_by             varchar(255),
   edit_time             char(19),
   edit_by               varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: tbl_activity_remark                                   */
/*==============================================================*/
create table tbl_activity_remark
(
   id                   char(32) not null,
   note_content          varchar(255),
   create_time           char(19),
   create_by             varchar(255),
   edit_time             char(19),
   edit_by               varchar(255),
   edit_flag             char(1) comment '0表示未修改，1表示已修改',
   activity_id           char(32),
   primary key (id)
);
