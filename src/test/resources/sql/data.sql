--member
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values (1, 'test@naver.com', 'test_user', '1234', '안뇽 나는 테스트 유저야!', 100, 'LOCAL', current_timestamp);
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values (2, 'test2@naver.com', 'test_user2', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp);

--category
insert into category (category_id, member_id, contents, is_public, is_activated, color, seq) values (10001, 1, '테스트_카테고리',true, true, 1, 1);
insert into category (category_id, member_id, contents, is_public, is_activated, color, seq) values (10002, 2, '테스트_카테고리2',true, true, 1, 1);

--todo
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20001, 1, 10001, '투두1', current_date, false);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20002, 2, 10002, '투두2', current_date, false);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20003, 1, 10001, '투두3', '2024-01-01', true);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20004, 1, 10001, '투두4', current_date, true);

--itemType
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (1, '테스트 아이템1', '테스트 아이템 이미지1', 10, '테스트 아이템 등급1', 10, '테스트 아이템 설명1');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (2, '테스트 아이템2', '테스트 아이템 이미지2', 20, '테스트 아이템 등급2', 20, '테스트 아이템 설명2');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (3, '테스트 아이템3', '테스트 아이템 이미지3', 30, '테스트 아이템 등급3', 30, '테스트 아이템 설명3');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (4, '테스트 아이템4', '테스트 아이템 이미지4', 40, '테스트 아이템 등급4', 40, '테스트 아이템 설명4');
