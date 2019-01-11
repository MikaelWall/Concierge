INSERT INTO DESCRIPTION (ID,TAG)
VALUES
    ('1', 'vit'),
    ('2', 'hdmi'),
    ('3', 'stor'),
    ('4', 'liten'),
    ('5', 'medium'),
    ('6', 'papperskorg'),
    ('7', 'fönster'),
    ('8', 'projektor');

INSERT INTO ROLE (ID,NAME)
VALUES
    ('1', 'admin'),
    ('2', 'user'),
    ('3', 'guest');

INSERT INTO ROOM (ID,NAME)
VALUES
    ('1', 'konferans'),
    ('2', 'matsal'),
    ('3', 'hallen'),
    ('4', 'badrum'),
    ('5', 'köket');

INSERT INTO ROOMS_DESCRIPTIONS (ROOM_ID,DESCRIPTION_ID)
VALUES
    ('1', '1'),
    ('1', '2'),
    ('1', '3'),
    ('2', '4'),
    ('2', '6'),
    ('2', '7'),
    ('2', '2'),
    ('3', '5'),
    ('3', '8'),
    ('4', '1'),
    ('4', '3'),
    ('4', '6'),
    ('5', '1'),
    ('5', '2'),
    ('5', '5'),
    ('5', '6'),
    ('5', '7');

INSERT INTO USER (ID,ACTIVATION_CODE,EMAIL,ENABLED,FIRST_NAME,LAST_NAME,PASSWORD)
VALUES
    ('1', 'tre','a@gmail.com','true','Henrik','A','123'),
    ('2', 'tre','b@gmail.com','true','Kherota','B','123'),
    ('3', 'tre','c@gmail.com','true','Björn','C','123'),
    ('4', 'tre','d@gmail.com','true','Te Hung','D','123'),
    ('5', 'tre','e@gmail.com','true','Mikael','E','123'),
    ('6', 'tre','f@gmail.com','false','Yuna','F','123'),
    ('7', 'tre','g@gmail.com','false','Rikku','G','123'),
    ('8', 'tre','h@gmail.com','true','Tidus','H','abc'),
    ('9', 'tre','i@gmail.com','true','Wakka','I','abc');


INSERT INTO USERS_ROLES (USER_ID,ROLE_ID)
VALUES
    ('1', '1'),
    ('1', '2'),
    ('2', '2'),
    ('3', '3'),
    ('4', '1'),
    ('5', '2'),
    ('6', '3'),
    ('7', '1'),
    ('8', '2'),
    ('8', '3'),
    ('9', '3');




INSERT INTO SCHEDULE (ID,START,STOP,ROOM_ID,USER_ID)
VALUES
    ('1', '2019-01-13 08:00:00','2019-01-13 12:00:00','1','1'),
    ('2', '2019-01-14 08:30:00','2019-01-13 12:00:00','1','1'),
    ('3', '2019-01-13 12:00:00','2019-01-13 15:00:00','1','2'),
    ('4', '2019-01-13 08:00:00','2019-01-13 12:00:00','2','3'),
    ('5', '2019-01-14 15:00:00','2019-01-13 16:00:00','3','4'),
    ('6', '2019-01-15 15:00:00','2019-01-13 15:15:00','4','4'),
    ('7', '2019-01-15 08:00:00','2019-01-13 12:00:00','5','5'),
    ('8', '2019-01-15 12:00:00','2019-01-13 15:00:00','5','9'),
    ('9', '2019-01-16 08:00:00','2019-01-19 12:00:00','4','9'),
    ('10', '2019-01-15 08:00:00','2019-01-13 12:00:00','3','9'),
    ('11', '2019-01-15 08:00:00','2019-01-13 12:00:00','1','9');





