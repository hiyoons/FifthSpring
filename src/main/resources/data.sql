--INSERT INTO member(name, email, password) VALUES('윤서준', 'SeojunYoon@hanbit.co.kr','$2a$12$sdhjsPSNXWXA1VGYCmdFnufQjd4LSlcNXgVAjPEWvW3Zy21AZomU2');
--INSERT INTO member(name, email, password) VALUES('윤광철', 'KwangcheolYoon@hanbit.co.kr','$2y$10$wlioSvb.9Eauxrqxl3eCuuVrf48pXbEWSqv8sNpRWeUWIlQIW/dYC');
--INSERT INTO member(name, email, password) VALUES('공미영', 'MiyeongKong@hanbit.co.kr','$2y$10$wlioSvb.9Eauxrqxl3eCuuVrf48pXbEWSqv8sNpRWeUWIlQIW/dYC');
--INSERT INTO member(name, email, password) VALUES('김도윤', 'DoyunKim@hanbit.co.kr','$2y$10$wlioSvb.9Eauxrqxl3eCuuVrf48pXbEWSqv8sNpRWeUWIlQIW/dYC');
--
--INSERT INTO authority(authority,member_id) VALUES  ('ROLE_ADMIN',2);
--
--
--INSERT INTO article(title, description, created, updated, member_id) VALUES('첫번째 게시글 제목', '첫번째 게시글 본문', '2022-09-19 18:11:20', CURRENT_TIMESTAMP, 1);
--INSERT INTO article(title, description, created, updated, member_id) VALUES('두번째 게시글 제목', '두번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);
--INSERT INTO article(title, description, created, updated, member_id) VALUES('세번째 게시글 제목', '세번째 게시글 본문', '2020-09-19 18:11:20', CURRENT_TIMESTAMP, 3);
--INSERT INTO article(title, description, created, updated, member_id) VALUES('네번째 게시글 제목', '네번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);
INSERT INTO play_list_article(address,latitude,longitude,created,updated) VALUES('서울 용산구 이촌동 302-215',37.51802534526627,126.95889617705899,'2025-12-09 15:44:20','2025-12-09 15:44:20');
INSERT INTO hashtag(tag_name) VALUES('겨울');
INSERT INTO hashtag(tag_name) VALUES('행복');
-- 1단계: Song 테이블에 먼저 INSERT (song_data 컬럼명은 실제 구조에 맞게 수정)
INSERT INTO song(text,href)
VALUES
    ('Pentatonix','https://youtube.com/watch?v=pFjdfjrtf1Q');
INSERT INTO song (text,href)
VALUES
    ('[Playlist] 슬슬 연말 분위기를 내볼까요? | 퍼펙트 크리스마스 캐롤 플레이리스트🎄🎅🎁', 'https://youtube.com/watch?v=a_80o2lDYec');

-- 2단계: 방금 INSERT한 Song들을 매핑
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
--INSERT INTO  tag_playlist_map(playlistArticle_id,hashtag_id,createdAt,updatedAt) (SELECT article_id,created AS created_at,updated AS updated_at  FROM play_list_article WHERE address="서울 용산구 이촌동 302-215");
//{"latitude":37.51802534526627,"longitude":126.95889617705899,"address":"서울 용산구 이촌동 302-215","tagList":["겨울","플리"],"songs":[{"href":"https://youtube.com/watch?v=pFjdfjrtf1Q","text":"Pentatonix - That's Christmas to Me (Official Video)"},{"href":"https://youtube.com/watch?v=a_80o2lDYec","text":"[Playlist] 슬슬 연말 분위기를 내볼까요? | 퍼펙트 크리스마스 캐롤 플레이리스트🎄🎅🎁 | Best Christmas Pop Songs Of All Time"}]}

