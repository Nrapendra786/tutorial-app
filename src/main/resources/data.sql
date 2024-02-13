INSERT INTO tutorials VALUES(1,'Physics','Introduction to Physics','HC Verma');
select
    t.description,
    t.id,
    t.published,
    t.title
from
    tutorials t;

INSERT INTO tutorials VALUES(2,'Math','Introduction to Linear Algebra','LC singh');