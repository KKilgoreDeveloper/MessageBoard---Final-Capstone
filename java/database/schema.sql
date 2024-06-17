BEGIN TRANSACTION;

--Drop tables
DROP TABLE IF EXISTS votes_comment;
DROP TABLE IF EXISTS votes_post;
DROP TABLE IF EXISTS user_moderator_forum;
DROP TABLE IF EXISTS user_favorite_forum;
DROP TABLE IF EXISTS user_forum;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS forum;
DROP TABLE IF EXISTS users;

--Upvotes and downvotes enum type created
CREATE TYPE vote AS ENUM ('upvote','downvote');

--Create tables
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    role varchar(50) NOT NULL
);

CREATE TABLE forum (
    forum_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    forum_name VARCHAR(50) NOT NULL UNIQUE,
    time_stamp TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    forum_id INT,
    title VARCHAR(50) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    up_votes INT NOT NULL,
    down_votes INT NOT NULL,
    time_stamp TIMESTAMP NOT NULL,
    location VARCHAR(50) NOT NULL,
    FOREIGN KEY (forum_id) REFERENCES forum(forum_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    message VARCHAR(1000) NOT NULL,
    time_stamp TIMESTAMP NOT NULL,
    post_id INT NOT NULL,
    reply_to INT,
    location VARCHAR(50) NOT NULL,
    --Reply_to foreign key set to comment_id (idk if this will work how we want it to, but hopefully)
    FOREIGN KEY (reply_to) REFERENCES comments(comment_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE user_forum (
    user_id INT NOT NULL,
    forum_id INT NOT NULL,
    PRIMARY KEY (user_id, forum_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (forum_id) REFERENCES forum(forum_id)
);

CREATE TABLE user_favorite_forum (
    forum_id INT NOT NULL,
    forum_user_id INT NOT NULL,
    PRIMARY KEY (forum_id, forum_user_id),
    FOREIGN KEY (forum_user_id) REFERENCES users(user_id),
    FOREIGN KEY (forum_id) REFERENCES forum(forum_id)
);

CREATE TABLE user_moderator_forum (
    forum_id INT NOT NULL,
    moderator_user_id INT NOT NULL,
    PRIMARY KEY (forum_id, moderator_user_id),
    FOREIGN KEY (moderator_user_id) REFERENCES users(user_id),
    FOREIGN KEY (forum_id) REFERENCES forum(forum_id)
);

--Vote tables
CREATE TABLE votes_post (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    vote vote,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

CREATE TABLE votes_comment (
    user_id INT NOT NULL,
    comment_id INT NOT NULL,
    vote vote,
    PRIMARY KEY (user_id, comment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

--Insert users
INSERT INTO users (username, password_hash, role) VALUES ('mattymattmattcat', '$2a$10$Nw1szXQbDHdsZ0UMGwDYuuj11LV.4KadomqE9qGDkTMxwK11x93xa', 'user');
INSERT INTO users (username, password_hash, role) VALUES ('evenstephen', '$2a$10$sNsoK44rCWny3PZhArEAquufjxedqO9wME4NcyjBv2MUj8KNSJhci', 'user');
INSERT INTO users (username, password_hash, role) VALUES ('codythegoat', '$2a$10$C9/OkOaFoKH6PR3nEfy26OTKb4WI3Gxv9tv.QkPaSXiW1ISBDGXwe', 'user');
INSERT INTO users (username, password_hash, role) VALUES ('admin1', '$2a$10$sISyTikQ87CywjFmipzat.JsTueOQ7BBDxIwkTSo69suRCGzZWLvS', 'ADMIN');

--Insert forums
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (1, 'Movie Reviews', '2024-06-05 10:00:00');
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (1, 'Book Recommendations', '2024-06-05 10:29:00');
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (3, 'Software Things', '2024-06-05 10:36:00');
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (2, 'Let''s Talk Music', CURRENT_TIMESTAMP);
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (2, 'Videogames', CURRENT_TIMESTAMP);
INSERT INTO forum (user_id, forum_name, time_stamp) VALUES (3, 'Pet Discussions', CURRENT_TIMESTAMP);

--Insert user forums
INSERT INTO user_forum (user_id, forum_id) VALUES (1, 1);
INSERT INTO user_forum (user_id, forum_id) VALUES (1, 2);
INSERT INTO user_forum (user_id, forum_id) VALUES (3, 3);
INSERT INTO user_forum (user_id, forum_id) VALUES (2, 4);
INSERT INTO user_forum (user_id, forum_id) VALUES (2, 5);
INSERT INTO user_forum (user_id, forum_id) VALUES (3, 6);

--Movie Reviews posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 1, 'Welcome to Movie Reviews!', 'This is the first post in Movie Reviews :)', 10, 0, '2024-06-05 11:47:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 1, 'Jurassic Park Review', 'Honestly, I''m just here for the dinosaurs', 20, 0, '2024-06-05 11:50:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 1, 'Jurassic Park Review: Response', 'TO WHOEVER POSTED SAYING THEY''RE JUST WATCHING JURASSIC PARK FOR THE DINOSAURS CLEARLY ISN''T FULLY APPRECIATING THE ARTISTRY THAT COMES FROM SPIELBERG.', 0, 6, '2024-06-05 11:52:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 1, 'Why Back To The Future is better than Infinity War', 'According to our top movie analysts, Back to the Future is leagues more realistic', 11, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 1, 'My favorite movies', 'Let''s start a thread of our favorite movies! Comment down below with your favorite, I''ll start: It''s Such a Beautiful Day by Don Hertzfeldt', 3, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 1, 'Evil Dead Review', 'Haven''t seen it :D', 5, 7, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 1, 'The Spongebob Movie', 'Literally the best film on planet earth, and it features my favorite band, Ween', 9, 6, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 1, 'Why you should watch A24 movies', 'Who doesn''t love an indie film???', 10, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 1, 'DELAYED', 'All the movies I''ve been excited about have been delayed in production due to the 2023 writer''s strike :((' , 8, 40, CURRENT_TIMESTAMP, 'USA');

--Book Recommendations posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 2, 'Welcome to Book Recommendations!', 'This is the first post in Book Recommendations :)', 10, 3, '2024-06-05 12:04:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 2, 'My Favorite Books', 'I actually only reread Meditations by Marcus Aerelius every second of the day. Highly recommend!', 20, 8, '2024-06-05 12:10:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 2, 'Recommendations needed!', 'I havent read in 25 years, I need my book fix. Any recs? My favorite genre is horror.', 5, 6, '2024-06-05 12:30:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 2, 'Help me find a new book!', 'I need a book that''s a dark fantasy western pleeeaaase', 11, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 2, 'My current reading list :)', 'NONE! I don''t read.', 0, 12, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 2, 'Need a book SERIES recommend', 'Enough SINGULAR books, I need a SERIES', 5, 0, CURRENT_TIMESTAMP, 'USA');

--Software Things posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 3, 'Welcome to Software Things!', 'This is the first post in Software Things :)', 12, 3, '2024-06-05 12:13:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 3, 'I need help with my code', 'How do I write a for loop? My instructor wont tell me', 5, 8, '2024-06-05 12:10:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 3, 'Need Project ideas', 'Seriously guys, I cant think of anything.', 30, 6, '2024-06-05 12:30:00', 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 3, 'Physical health of a software developer', 'Does anyone else have back and neck problems from this profession?', 11, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 3, 'Game Development', 'Wouldn''t it be cool if someone made a simple pixelart game of a coyote that can walk around the screen and not do much else', 3, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 3, 'Preferred language', 'What''s y''all''s preferred programming language?', 3, 0, CURRENT_TIMESTAMP, 'USA');

--Misc. posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, null, 'Hi Stephen', 'Hi Stephen', 200, 0, '2024-06-10 12:13:00', 'NRL');

--Let's Talk Music posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 4, 'What''s a song you can''t live without?', 'I personally wouldn''t be the same without Rick Astley''s Never Gonna Give You Up', 18, 3, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 4, 'Favorite Bands', 'What''s your favorite band? Comment below!', 12, 2, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 4, 'Need song recs', 'I listen to just about anything so throw your recommendations my way :)', 35, 6, CURRENT_TIMESTAMP, 'USA');

--VideoGames posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 5, 'Cozy Games', 'I''m in DESPERATE need of some cozy games in my life. Any recs?', 9, 0, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (2, 5, 'Best console?', 'What''s your preferred console to use?', 12, 5, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 5, 'Game help', 'I can''t seem to beat the computer on pong, somebody help.', 35, 0, CURRENT_TIMESTAMP, 'USA');

-- Pet Discussion Posts
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 6, 'Need help!', 'My pet iguana won''t stop scratching me! I''m at my wits'' end. Any advice?', 18, 10, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (1, 6, 'First time pet owner', 'Hello! I plan on getting myself my first ever pet and I''d like to know your recommendations for the best beginner pet :)', 12, 2, CURRENT_TIMESTAMP, 'USA');
INSERT INTO posts (user_id, forum_id, title, message, up_votes, down_votes, time_stamp, location)
VALUES (3, 6, 'Beta fish care PSA', 'If you can''t give them a proper setup, DON''T BUY ONE', 50, 9, CURRENT_TIMESTAMP, 'USA');

--Comments for misc. posts
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Hi Stephen', '2024-06-07 12:34:00', 22, NULL, 'NLR');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Hi Stephen', '2024-06-07 12:34:00', 22, NULL, 'NLR');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Hi Stephen', '2024-06-07 12:34:00', 22, NULL, 'NLR');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Hi Stephen', '2024-06-07 12:34:00', 22, NULL, 'NLR');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Hi Stephen', '2024-06-07 12:34:00', 22, NULL, 'NLR');

-- Comments for "Jurassic Park Review: Response"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I prefer Jaws', '2024-06-07 09:34:00', 3, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'The dinosaurs are why we''re all here bro', '2024-06-07 09:38:00', 3, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I kinda agree tho', '2024-06-07 09:39:00', 3, NULL, 'USA');

-- Comments for "Welcome to Movie Reviews"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Make me mod.', '2024-06-07 09:34:00', 1, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Can you ban the spielberg guy?', '2024-06-07 09:38:00', 1, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Hi!! Make me admin pls :)', '2024-06-07 09:39:00', 1, NULL, 'USA');

-- Comments for "Jurassic Park Review"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'YESS, SO ARE WE!!!', '2024-06-07 09:34:00', 2, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'DINOSAURS RAHHHHHH ! ! !', '2024-06-07 09:38:00', 2, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'THIS GUY GETS IT.', '2024-06-07 09:39:00', 2, NULL, 'USA');

-- Comments for "Welcome to Book Recommendations!"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Yay!! It''s like our own little bookclub!', '2024-06-07 09:34:00', 10, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Can we recommend comics?', '2024-06-07 09:38:00', 10, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I''m writing a 500 book series with 50 books per arc, would I be allowed to post 3 of my books a day? It''d TECHNICALLY be a recommendation if you think about it.', '2024-06-07 09:39:00', 10, NULL, 'USA');

--Comments for "My Favorite Books"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Wow! Every second of the day? When do you find the time to sleep??', '2024-06-07 09:34:00', 11, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Who asked', '2024-06-07 09:38:00', 11, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I LOVE THAT BOOK!!', '2024-06-07 09:39:00', 11, NULL, 'USA');

--Comments for "Recommendations needed!"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Seeing as you haven''t read for 25 years, could you provide some context to what your current reading level is?', '2024-06-07 09:34:00', 12, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'House of Leaves by Mark Danielewski', '2024-06-07 09:38:00', 12, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'fix??', '2024-06-07 09:39:00', 12, NULL, 'USA');

--Comments for "Welcome to Software Things!"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Make me mod :)', '2024-06-07 10:34:00', 16, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Make me mod :))', '2024-06-07 10:38:00', 16, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Ban the other two guys :)))', '2024-06-07 10:39:00', 16, NULL, 'USA');

--Comments for "I need help with my code"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Same issue here, my instructor refuses to rexplain to me arrays for the 46th time :/', '2024-06-07 10:34:00', 17, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Yeah, I''m not helping you with that.', '2024-06-07 10:38:00', 17, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, '"for (int i = 0; i < array.length; i++) {System.out.println(i);}" , Hope that helps!', '2024-06-07 10:39:00', 17, NULL, 'USA');

--Comments for "Need Project ideas"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Maybe try creating a vending machine application', '2024-06-07 10:34:00', 18, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'You should make something like Venmo! But, call it something else.', '2024-06-07 10:38:00', 18, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'You should make a message board or something.', '2024-06-07 10:39:00', 18, NULL, 'USA');

--Comments for "Why you should watch A24 movies"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I love A24!!', CURRENT_TIMESTAMP, 8, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'What is that', CURRENT_TIMESTAMP, 8, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'Every A24 movie I''ve seen has been mid (I''m lying)', CURRENT_TIMESTAMP, 8, NULL, 'USA');

--Comments for "My favorite movies"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Day of The Dead', CURRENT_TIMESTAMP, 5, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'O Brother, Where Art Thou', CURRENT_TIMESTAMP, 5, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Spirited Away', CURRENT_TIMESTAMP, 5, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Night of the living dead', CURRENT_TIMESTAMP, 5, NULL, 'USA');

--Comments for "Why Back To The Future is better than Infinity War"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'I believe this', CURRENT_TIMESTAMP, 5, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'Blasphemy', CURRENT_TIMESTAMP, 5, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Interesting take', CURRENT_TIMESTAMP, 5, NULL, 'USA');

--Comments for "Beta Fish Care PSA"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'SO TRUE', CURRENT_TIMESTAMP, 31, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Everytime I see a beta fish in a bowl with no filter, I die a little inside', CURRENT_TIMESTAMP, 31, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'I treat my beta fish like ROYALTY', CURRENT_TIMESTAMP, 31, NULL, 'USA');

--Comments for "First Time Pet Owner"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Get a dog or cat ez', CURRENT_TIMESTAMP, 30, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'GET A MOUSE. I own several :)', CURRENT_TIMESTAMP, 30, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Reptiles are underrated, get a snake!', CURRENT_TIMESTAMP, 30, NULL, 'USA');

--Comments for "Need Help!"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Maybe should''ve done more research before getting an iguana', CURRENT_TIMESTAMP, 29, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'All iguanas do that! I have one and I''m COVERED in scratches', CURRENT_TIMESTAMP, 29, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'If it''s a juvenile, that''s normal. If it''s fully grown then that''s dangerous and you didn''t do a great job socializing it :/', CURRENT_TIMESTAMP, 29, NULL, 'USA');

--Comments for "Game Help"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, '1972 called, they want their game back', CURRENT_TIMESTAMP, 28, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'You can''t beat PONG???', CURRENT_TIMESTAMP, 28, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I also struggle with pong', CURRENT_TIMESTAMP, 28, NULL, 'USA');

--Comments for "Best Console"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'PC, obviously', CURRENT_TIMESTAMP, 27, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'Xbox for multiplayer games, PS4 for single player', CURRENT_TIMESTAMP, 27, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'The nintendo switch fs', CURRENT_TIMESTAMP, 27, NULL, 'USA');

--Comments for "Cozy Games"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'You should play Spiritfarer!', CURRENT_TIMESTAMP, 26, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Stardew Valley, ofc!', CURRENT_TIMESTAMP, 26, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'I honestly find older Pokemon to be relatively cozy, that could just be me tho', CURRENT_TIMESTAMP, 26, NULL, 'USA');

--Comments for "Need Song Recs"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Worms by Viagra Boys', CURRENT_TIMESTAMP, 25, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'The Clue by R.M.F.C.', CURRENT_TIMESTAMP, 25, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'Collector by Daffo', CURRENT_TIMESTAMP, 25, NULL, 'USA');

--Comments for "Favorite Bands"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'I LOVE WEEN', CURRENT_TIMESTAMP, 24, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'The Warning is pretty cool', CURRENT_TIMESTAMP, 24, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'The Crane Wives!!', CURRENT_TIMESTAMP, 24, NULL, 'USA');

--Comments for "What's a Song You Cant Live Without"
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (3, 'Why would you say that, this is a rickroll-free zone', CURRENT_TIMESTAMP, 23, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (1, 'Oh yeah, I personally love All Star by Smash Mouth', CURRENT_TIMESTAMP, 23, NULL, 'USA');
INSERT INTO comments (user_id, message, time_stamp, post_id, reply_to, location)
VALUES (2, 'Can any of you give a normal answer??', CURRENT_TIMESTAMP, 23, NULL, 'USA');


COMMIT TRANSACTION;