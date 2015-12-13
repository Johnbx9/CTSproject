# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table borrow (
  id                        serial not null,
  users_id                  bigint,
  tools_id                  bigint,
  constraint pk_borrow primary key (id))
;

create table category (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table tool (
  id                        bigserial not null,
  name                      varchar(255),
  tool_description          varchar(255),
  tool_owner_id             bigint,
  tc_id                     bigint,
  isborrow                  integer,
  borrower_id               bigint,
  constraint pk_tool primary key (id))
;

create table users (
  id                        bigserial not null,
  username                  varchar(255),
  email                     varchar(255),
  password_hash             varchar(255),
  address                   varchar(255),
  constraint uq_users_username unique (username),
  constraint pk_users primary key (id))
;

alter table borrow add constraint fk_borrow_users_1 foreign key (users_id) references users (id);
create index ix_borrow_users_1 on borrow (users_id);
alter table borrow add constraint fk_borrow_tools_2 foreign key (tools_id) references tool (id);
create index ix_borrow_tools_2 on borrow (tools_id);
alter table tool add constraint fk_tool_toolOwner_3 foreign key (tool_owner_id) references users (id);
create index ix_tool_toolOwner_3 on tool (tool_owner_id);
alter table tool add constraint fk_tool_tc_4 foreign key (tc_id) references category (id);
create index ix_tool_tc_4 on tool (tc_id);
alter table tool add constraint fk_tool_borrower_5 foreign key (borrower_id) references users (id);
create index ix_tool_borrower_5 on tool (borrower_id);



# --- !Downs

drop table if exists borrow cascade;

drop table if exists category cascade;

drop table if exists tool cascade;

drop table if exists users cascade;

