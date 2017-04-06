# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            bigserial not null,
  name                          varchar(255),
  constraint pk_category primary key (id)
);

create table tool (
  id                            bigserial not null,
  name                          varchar(255),
  tool_description              varchar(255),
  stc                           varchar(255),
  tool_owner_id                 bigint,
  tc_id                         bigint,
  is_borrowable                 boolean,
  borrower_id                   bigint,
  image_file                    bytea,
  constraint pk_tool primary key (id)
);

create table users (
  id                            bigserial not null,
  username                      varchar(255),
  email                         varchar(255),
  password_hash                 varchar(255),
  constraint uq_users_username unique (username),
  constraint pk_users primary key (id)
);

alter table tool add constraint fk_tool_tool_owner_id foreign key (tool_owner_id) references users (id) on delete restrict on update restrict;
create index ix_tool_tool_owner_id on tool (tool_owner_id);

alter table tool add constraint fk_tool_tc_id foreign key (tc_id) references category (id) on delete restrict on update restrict;
create index ix_tool_tc_id on tool (tc_id);

alter table tool add constraint fk_tool_borrower_id foreign key (borrower_id) references users (id) on delete restrict on update restrict;
create index ix_tool_borrower_id on tool (borrower_id);


# --- !Downs

alter table if exists tool drop constraint if exists fk_tool_tool_owner_id;
drop index if exists ix_tool_tool_owner_id;

alter table if exists tool drop constraint if exists fk_tool_tc_id;
drop index if exists ix_tool_tc_id;

alter table if exists tool drop constraint if exists fk_tool_borrower_id;
drop index if exists ix_tool_borrower_id;

drop table if exists category cascade;

drop table if exists tool cascade;

drop table if exists users cascade;

