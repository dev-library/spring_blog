## 댓글 테이블 설정
CREATE TABLE reply(
	reply_id int primary key auto_increment,
    blog_id int not null,
    reply_writer varchar(40) not null,
    reply_content varchar(200) not null,
    published_at datetime default now(),
    updated_at datetime default now()
);
# 외래키 설정
# blog_id에는 기존에 존재하는 글의 blog_id만 들어가야 한다.
alter table reply add constraint fk_reply foreign key (blog_id) references blog(blog_id);

# 더미 데이터 입력(테스트 DB에서만 사용합니다.)
INSERT INTO reply VALUES(null, 2, "댓글쓴사람", "1빠댓글이다~~~~", now(), now()),
(null, 2, "짹짹이", "짹짹쨲쨲쨲쨰꺠ㅉ꺠ㅉㄲㅉ꺢", now(), now()),
(null, 2, "바둑이", "멍멍멍멍머엄엄엄어멍머어멍~~~~", now(), now()),
(null, 2, "야옹이", "고양이가 어떻게 댓글을 씀?", now(), now()),
(null, 3, "개발고수", "아 REST써버 개쉽당", now(), now());
