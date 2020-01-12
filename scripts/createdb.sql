drop database bookapp;
create database bookapp;

use bookapp;

create TABLE books
(
  id int  NOT NULL AUTO_INCREMENT,
  title varchar (64),
  author varchar(64),
  num_pages int,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);


