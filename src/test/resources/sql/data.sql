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
insert into item_type (item_type_id, name, img, price, grade, exp, description) values (20000, '아이템 이름', '이미지 url', 10, '아이템 등급', 10, '아이템 설명');

--characterType
insert into character_type (character_type_id, name, img, level, species, description) values
                                                                                           (1, '캐릭터1', '캐릭터1 이미지', 0, 1, '캐릭터1 설명'),
                                                                                           (10, '캐릭터1-1', '캐릭터1-1 이미지', 1, 1, '캐릭터1-1 설명'),
                                                                                           (100, '캐릭터1-2', '캐릭터1-2 이미지', 2, 1, '캐릭터1-2 설명');
insert into character_type (character_type_id, name, img, level, species, description) values
                                                                                           (2, '캐릭터2', '캐릭터2 이미지', 0, 2, '캐릭터2 설명'),
                                                                                           (20, '캐릭터2-1', '캐릭터2-1 이미지', 1, 2, '캐릭터2-1 설명'),
                                                                                           (200, '캐릭터2-2', '캐릭터2-2 이미지', 2, 2, '캐릭터2-2 설명');
insert into character_type (character_type_id, name, img, level, species, description) values
                                                                                           (3, '캐릭터3', '캐릭터3 이미지', 0, 3, '캐릭터3 설명'),
                                                                                           (30, '캐릭터3-1', '캐릭터3-1 이미지', 1, 3, '캐릭터3-1 설명'),
                                                                                           (300, '캐릭터3-2', '캐릭터3-2 이미지', 2, 3, '캐릭터3-2 설명');

