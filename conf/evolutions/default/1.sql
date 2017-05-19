# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tool (
  id                            bigserial not null,
  name                          varchar(255),
  description                   varchar(255),
  user_id                       bigint,
  borrower_id                   bigint,
  image_file                    bytea,
  constraint pk_tool primary key (id)
);

create table users (
  id                            bigserial not null,
  username                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  address                       varchar(255),
  constraint uq_users_username unique (username),
  constraint pk_users primary key (id)
);

alter table tool add constraint fk_tool_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_tool_user_id on tool (user_id);

alter table tool add constraint fk_tool_borrower_id foreign key (borrower_id) references users (id) on delete restrict on update restrict;
create index ix_tool_borrower_id on tool (borrower_id);


# --- !Downs

alter table if exists tool drop constraint if exists fk_tool_user_id;
drop index if exists ix_tool_user_id;

alter table if exists tool drop constraint if exists fk_tool_borrower_id;
drop index if exists ix_tool_borrower_id;

drop table if exists tool cascade;

drop table if exists users cascade;

