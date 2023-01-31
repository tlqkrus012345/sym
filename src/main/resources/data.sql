insert into post (title, text, hashtag, create_date, modify_date, writer, modifier) values
    ('Spring Boot Title', 'Hello Spring Boot', 'Green', '2022-11-10 00:19:12', '2022-04-02 17:35:51', 'Spring', 'Boot');

insert into post (title, text, hashtag, create_date, modify_date, writer, modifier) values
    ('Java Title', 'Hello Java', 'White', '2022-06-30 14:15:20', '2022-11-13 18:59:30', 'Java', 'OOP');

insert into post (title, text, hashtag, create_date, modify_date, writer, modifier) values
    ('MySQL Title', 'Hello MySQL', 'Blue', '2022-03-04 11:39:48', '2022-04-23 00:14:44', 'MySQL', 'RDBMS');

insert into comment (post_id, text, create_date, modify_date, writer, modifier) values
    (1, 'Spring WOW', '2022-11-11 00:19:12', '2022-11-12 00:19:12', 'mvc', 'api');

insert into comment (post_id, text, create_date, modify_date, writer, modifier) values
    (1, 'No', '2022-11-11 00:19:12', '2022-11-12 00:19:12', 'core', 'rest');

insert into comment (post_id, text, create_date, modify_date, writer, modifier) values
    (2, 'Java good', '2022-11-11 00:19:12', '2022-11-12 00:19:12', 'solid', 'c')
