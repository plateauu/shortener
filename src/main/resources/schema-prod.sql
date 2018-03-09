DROP TABLE IF EXISTS URL;

CREATE TABLE URL(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  short_url VARCHAR(100),
  long_url VARCHAR(200),
  creation_date TIMESTAMP,
  modification_date TIMESTAMP
);