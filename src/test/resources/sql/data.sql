--member
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values (1, 'test@naver.com', 'test_user', '1234', '안뇽 나는 테스트 유저야!', 100, 'LOCAL', current_timestamp);
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values (2, 'test2@naver.com', 'test_user2', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp);

--category
insert into category (category_id, member_id, contents, is_public, is_activated, color, seq) values
                                                                                                 (10001, 1, '테스트_카테고리',true, true, 'BLUE', 1),
                                                                                                 (10002, 1, '테스트_카테고리',true, true, 'BLUE', 2),
                                                                                                 (10003, 1, '테스트_카테고리',true, true, 'BLUE', 3),
                                                                                                 (10004, 1, '테스트_카테고리',true, true, 'BLUE', 4),
                                                                                                 (10005, 1, '테스트_카테고리',true, true, 'BLUE', 5),
                                                                                                 (10006, 1, '테스트_카테고리',true, true, 'BLUE', 6),
                                                                                                 (10007, 1, '테스트_카테고리',true, true, 'BLUE', 7),
                                                                                                 (10008, 1, '테스트_카테고리',true, true, 'BLUE', 8),
                                                                                                 (10009, 1, '테스트_카테고리',true, true, 'BLUE', 9),
                                                                                                 (10010, 1, '테스트_카테고리',true, true, 'BLUE', 10),
                                                                                                 (10011, 1, '테스트_카테고리',true, true, 'BLUE', 11),
                                                                                                 (10012, 1, '테스트_카테고리',true, true, 'BLUE', 12),
                                                                                                 (10013, 1, '테스트_카테고리',true, true, 'BLUE', 13),
                                                                                                 (10014, 1, '테스트_카테고리',true, true, 'BLUE', 14),
                                                                                                 (10015, 1, '테스트_카테고리',true, true, 'BLUE', 15),
                                                                                                 (10016, 1, '테스트_카테고리',true, true, 'BLUE', 16),
                                                                                                 (10017, 1, '테스트_카테고리',true, true, 'BLUE', 17),
                                                                                                 (10018, 1, '테스트_카테고리',true, true, 'BLUE', 18),
                                                                                                 (10019, 1, '테스트_카테고리',true, true, 'BLUE', 19),
                                                                                                 (10021, 2, '테스트_카테고리2',true, true, 'PINK', 1);

--todo
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20001, 1, 10001, '투두1', current_date, false);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20002, 2, 10021, '투두2', current_date, false);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20003, 1, 10001, '투두3', '2024-01-01', true);
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values (20004, 1, 10001, '투두4', current_date, true);

--itemType
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (1, '테스트 아이템1', '테스트 아이템 이미지1', 10, '테스트 아이템 등급1', 10, '테스트 아이템 설명1');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (2, '테스트 아이템2', '테스트 아이템 이미지2', 20, '테스트 아이템 등급2', 20, '테스트 아이템 설명2');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (3, '테스트 아이템3', '테스트 아이템 이미지3', 30, '테스트 아이템 등급3', 30, '테스트 아이템 설명3');
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (4, '테스트 아이템4', '테스트 아이템 이미지4', 40, '테스트 아이템 등급4', 40, '테스트 아이템 설명4');
