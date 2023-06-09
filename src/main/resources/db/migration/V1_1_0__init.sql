CREATE TABLE IF NOT EXISTS users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  last_auth_timestamp TIMESTAMP,
  registration_timestamp TIMESTAMP,

   PRIMARY KEY (id),
   CONSTRAINT reference_unique UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS game_history (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  user_id MEDIUMINT NOT NULL,
  step MEDIUMINT NOT NULL,
  choice VARCHAR(100) NOT NULL,
  result VARCHAR(100) NOT NULL,

   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
