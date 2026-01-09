CREATE TABLE member (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256)
);

--CREATE TABLE authority (
--    id INTEGER AUTO_INCREMENT PRIMARY KEY,
--    authority VARCHAR(256),
--    member_id INTEGER,
--    FOREIGN KEY(member_id) REFERENCES member(id)
--);
--
--CREATE TABLE article (
--    id INTEGER AUTO_INCREMENT PRIMARY KEY,
--    title VARCHAR(256),
--    description VARCHAR(4096),
--    created DATETIME,
--    updated DATETIME,
--    member_id INTEGER,
--    FOREIGN KEY(member_id) REFERENCES member(id)
--);
--


CREATE TABLE hashtag (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(256)
);

--CREATE TABLE tag_article_map (
--    article_tag_id INTEGER AUTO_INCREMENT PRIMARY KEY,
--    created_at DATETIME,
--    updated_at DATETIME,
--    article_id INTEGER,
--    hashtag_id INTEGER,
--    FOREIGN KEY(article_id) REFERENCES article(id),
--    FOREIGN KEY(hashtag_id) REFERENCES hashtag(id)
--);



CREATE TABLE play_list_article (
       id INTEGER AUTO_INCREMENT PRIMARY KEY,
       latitude DECIMAL(11,8),
       longitude DECIMAL(11,8),
       address VARCHAR(256),
       created DATETIME,
       updated DATETIME
);

CREATE TABLE tag_playlist_map (
    playlist_tag_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    play_list_article_id INTEGER,
    hashtag_id INTEGER,
    created_at DATETIME,
    updated_at DATETIME,
     FOREIGN KEY(play_list_article_id) REFERENCES play_list_article(id),
     FOREIGN KEY(hashtag_id) REFERENCES hashtag(id)
);

CREATE TABLE Song (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(256),
    href VARCHAR(256)
);

CREATE TABLE song_playlist_map (
    playlist_song_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    play_list_article_id INTEGER,
    song_id INTEGER,
    created_at DATETIME,
    updated_at DATETIME,

    FOREIGN KEY(play_list_article_id) REFERENCES play_list_article(id),
    FOREIGN KEY(song_id) REFERENCES song(id)
);
