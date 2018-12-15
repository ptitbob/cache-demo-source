drop table if exists adm_message;
drop sequence if exists adm_message_sequence;

create sequence adm_message_sequence start 100 cache 10;

create table adm_message (
  adm_message_id bigint not null default nextval('adm_message_sequence') primary key ,
  code varchar(10) not null,
  message varchar(100)
);
