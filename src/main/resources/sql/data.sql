--member
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values (1, 'test@naver.com', 'test_user', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp);

--category
insert into category (category_id, member_id, contents, is_public, is_activated, color, seq) values (10001, 1, '테스트_카테고리',true, true, 1, 1);