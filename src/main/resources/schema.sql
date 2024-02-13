CREATE TABLE  IF NOT EXISTS tutorials(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    published varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);