CREATE TABLE  IF NOT EXISTS tutorials(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    published varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Tutorial VALUES('Physics','Introduction to Physics','HC Verma');
INSERT INTO Tutorial VALUES('Math','Introduction to Linear Algebra','LC singh');
INSERT INTO Tutorial VALUES('Chemistry','Introduction to Chemistry','LK Kant');
INSERT INTO Tutorial VALUES('Biology','Introduction to Biology','PC yadav');