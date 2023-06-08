CREATE TABLE IF NOT EXISTS users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  last_auth_timestamp TIMESTAMP,
  registration_timestamp TIMESTAMP,
   PRIMARY KEY (id),
   CONSTRAINT reference_unique UNIQUE (login)
);
