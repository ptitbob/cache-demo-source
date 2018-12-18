START TRANSACTION;

create unique index UX_PERSON
  on person(login);

create index IX_PERSON_CITY
  on person(city_insee);

create index IX_PERSON_NAME
  on person(firstname, lastname);

create unique index UX_MESSAGE
  on adm_message(code);

create index IX_MESSAGE_MESSAGE
  on adm_message(message);

COMMIT;
END TRANSACTION;