-- member initialization
INSERT INTO member (login_id, login_password)
VALUES ('guest1', 'guest1'),
       ('guest2', 'guest2'),
       ('guest3', 'guest3'),
       ('guest4', 'guest4'),
       ('guest5', 'guest5');

-- friendship initialization
INSERT INTO friendship (younger_member_id, older_member_id)
SELECT
    CASE WHEN m1.member_id < m2.member_id THEN m1.member_id ELSE m2.member_id END,
    CASE WHEN m1.member_id < m2.member_id THEN m2.member_id ELSE m1.member_id END
FROM member m1, member m2
WHERE m1.login_id = 'guest1' AND m2.login_id = 'guest2';

INSERT INTO friendship (younger_member_id, older_member_id)
SELECT
    CASE WHEN m1.member_id < m2.member_id THEN m1.member_id ELSE m2.member_id END,
    CASE WHEN m1.member_id < m2.member_id THEN m2.member_id ELSE m1.member_id END
FROM member m1, member m2
WHERE m1.login_id = 'guest2' AND m2.login_id = 'guest3';

INSERT INTO friendship (younger_member_id, older_member_id)
SELECT
    CASE WHEN m1.member_id < m2.member_id THEN m1.member_id ELSE m2.member_id END,
    CASE WHEN m1.member_id < m2.member_id THEN m2.member_id ELSE m1.member_id END
FROM member m1, member m2
WHERE m1.login_id = 'guest2' AND m2.login_id = 'guest5';

INSERT INTO friendship (younger_member_id, older_member_id)
SELECT
    CASE WHEN m1.member_id < m2.member_id THEN m1.member_id ELSE m2.member_id END,
    CASE WHEN m1.member_id < m2.member_id THEN m2.member_id ELSE m1.member_id END
FROM member m1, member m2
WHERE m1.login_id = 'guest1' AND m2.login_id = 'guest5';