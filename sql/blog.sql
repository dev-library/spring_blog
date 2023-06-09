# 블로그 테이블 생성 구문
CREATE TABLE blog(
	blog_id int auto_increment primary key,
	writer varchar(16) not null,
    blog_title varchar(200) not null,
    blog_content varchar(4000) not null,
    published_at datetime default now(),
    updated_at datetime default now(),
    blog_count int default 0
);
# 더미데이터 입력용 구문(테스트 DB에서만 사용합니다.)
INSERT INTO blog VALUES
    (null, '1번유저', '1번제목', '1번본문', now(), now(), null),
    (null, '2번유저', '2번제목', '2번본문', now(), now(), null),
    (null, '3번유저', '3번제목', '3번본문', now(), now(), null);