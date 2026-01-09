INSERT INTO play_list_article(address,latitude,longitude,created,updated) VALUES('서울 용산구 이촌동 302-215',37.51802534526627,126.95889617705899,'2025-12-09 15:44:20','2025-12-09 15:44:20');
INSERT INTO hashtag(tag_name) VALUES('겨울');
INSERT INTO hashtag(tag_name) VALUES('행복');

INSERT INTO song(text,href)
VALUES
    ('Pentatonix','https://youtube.com/watch?v=pFjdfjrtf1Q');
INSERT INTO song (text,href)
VALUES
    ('[Playlist] 슬슬 연말 분위기를 내볼까요? | 퍼펙트 크리스마스 캐롤 플레이리스트🎄🎅🎁', 'https://youtube.com/watch?v=a_80o2lDYec');

INSERT INTO song_playlist_map(song_id, play_list_article_id)
    SELECT  s.id, p.id
    FROM song s
    CROSS JOIN play_list_article p
    WHERE p.address = '서울 용산구 이촌동 302-215'
    AND s.href IN (
    'https://youtube.com/watch?v=pFjdfjrtf1Q',
    'https://youtube.com/watch?v=a_80o2lDYec'
    );

INSERT INTO tag_playlist_map(hashtag_id,play_list_article_id)
    SELECT  h.id, p.id
    FROM hashtag h
    CROSS JOIN play_list_article p
    WHERE p.address = '서울 용산구 이촌동 302-215'
    AND h.tag_name IN('행복','겨울');
