
--commit
--rollback

begin;

CREATE TABLE user (
  id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  is_active BOOLEAN NOT NULL,
  created TIMESTAMP,
  update_date TIMESTAMP,
  last_login TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE phone (
  id UUID NOT NULL,
  number VARCHAR(255) NOT NULL,
  citycode VARCHAR(255),
  contrycode VARCHAR(255),
  user_id UUID,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);
