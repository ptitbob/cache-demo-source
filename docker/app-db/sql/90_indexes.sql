START TRANSACTION;

create unique index UX_MESSAGE
  on adm_message(code);

create index IX_MESSAGE_MESSAGE
  on adm_message(message);

COMMIT;
END TRANSACTION;