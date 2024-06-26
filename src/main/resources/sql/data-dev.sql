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

--member
insert into member (member_id, email, nickname, password, description, coin, type, last_upload) values
                                                                                                    (1, 'test@naver.com', 'test_user', '1234', '안뇽 나는 테스트 유저야!', 100, 'LOCAL', current_timestamp), -- 베팅 생성
                                                                                                    (2, 'test2@naver.com', 'test_user2', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp), -- 베팅 생성 실패
                                                                                                    (3, 'test2@naver.com', 'test_user3', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp), -- 베팅 생성 실패
                                                                                                    (4, 'test4@naver.com', 'test_user4', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20000, 'test20000@naver.com', 'test_user20000', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20001, 'test20001@naver.com', 'test_user20001', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20002, 'test20002@naver.com', 'test_user20002', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20003, 'test20003@naver.com', 'test_user20003', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20004, 'test20004@naver.com', 'test_user20004', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20005, 'test20004@naver.com', 'test_user20005', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20006, 'test20006@naver.com', 'test_user20006', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20007, 'test20007@naver.com', 'test_user20007', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (20008, 'test20008@naver.com', 'test_user20008', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp),
                                                                                                    (30000, 'test30000@naver.com', 'test_user30000', '1234', '안뇽 나는 테스트 유저야!', 0, 'LOCAL', current_timestamp);

insert into characters (character_id, exp, character_type_id, member_id) values (1, 250, 1, 1),
                                                                                (2, 200, 2, 1),
                                                                                (3, 100, 1, 2),
                                                                                (4, 100, 1, 20000),
                                                                                (5, 100, 2, 20001),
                                                                                (6, 100, 3, 20002),
                                                                                (7, 100, 1, 20003),
                                                                                (8, 100, 2, 20004),
                                                                                (9, 100, 3, 20005),
                                                                                (10, 100, 1, 20006),
                                                                                (11, 100, 2, 20007),
                                                                                (12, 100, 3, 20008),
                                                                                (13, 100, 1, 30000);

update member set main_character_id = 1 where member_id = 1;
update member set main_character_id = 2 where member_id = 2;
update member set main_character_id = 3 where member_id = 3;
update member set main_character_id = 4 where member_id = 20000;
update member set main_character_id = 5 where member_id = 20001;
update member set main_character_id = 6 where member_id = 20002;
update member set main_character_id = 7 where member_id = 20003;
update member set main_character_id = 8 where member_id = 20004;
update member set main_character_id = 9 where member_id = 20005;
update member set main_character_id = 10 where member_id = 30000;

--relation
insert into relation (member_id, friend_id, status) values
                                                              (1, 2, 'ACCEPTED'),
                                                              (2, 1, 'ACCEPTED'),
                                                              (1, 3, 'ACCEPTED'),
                                                              (3, 1, 'ACCEPTED'),
                                                              (1, 20000, 'ACCEPTED'),
                                                              (20000, 1, 'ACCEPTED'),
                                                              (1, 20001, 'BLOCKED'),
                                                              (20003, 1, 'BLOCKED'),
                                                              (1, 20005, 'BLOCKED'),
                                                              (1, 20007, 'BLOCKED'),
                                                              (20008, 1, 'BLOCKED'),
                                                              (1, 20006, 'WAITING'),
                                                              (1, 20008, 'WAITING'),
                                                              (20005, 1, 'WAITING'),
                                                              (20004, 1, 'WAITING');

--category
insert into category (category_id, member_id, contents, scope, is_activated, color, seq) values
                                                                                             (10001, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 1), -- 베팅 생성, 생성 실패
                                                                                             (10002, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 2),
                                                                                             (10003, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 3),
                                                                                             (10004, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 4),
                                                                                             (10005, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 5),
                                                                                             (10006, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 6),
                                                                                             (10007, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 7),
                                                                                             (10008, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 8),
                                                                                             (10009, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 9),
                                                                                             (10010, 1, '테스트_카테고리','PUBLIC', true, 'BLUE', 10),
                                                                                             (10011, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 11),
                                                                                             (10012, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 12),
                                                                                             (10013, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 13),
                                                                                             (10014, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 14),
                                                                                             (10015, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 15),
                                                                                             (10016, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 16),
                                                                                             (10017, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 17),
                                                                                             (10018, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 18),
                                                                                             (10019, 1, '테스트_카테고리','FRIENDS', true, 'BLUE', 19),
                                                                                             (10020, 1, '테스트_카테고리','FRIENDS', false, 'BLUE', 1),
                                                                                             (10021, 1, '테스트_카테고리','PUBLIC', false, 'BLUE', 1),
                                                                                             (10022, 2, '테스트_카테고리2','PUBLIC', true, 'PINK', 1), -- 베팅 생성 실패
                                                                                             (10023, 3, '테스트_카테고리3','PUBLIC', true, 'PINK', 1),
                                                                                             (10024, 4, '테스트_카테고리4','PUBLIC', true, 'PINK', 1);

--todo
insert into todo (todo_id, member_id, category_id, contents, date, is_done) values
                                                                                (20001, 1, 10001, '투두1', current_date, false), -- 베팅 생성, 삭제
                                                                                (20002, 2, 10021, '투두2', current_date, false), -- 베팅 생성 실패, 삭제 실패
                                                                                (20003, 1, 10001, '투두3', date_add(current_date, interval -1 day), true), -- 베팅 생성 실패
                                                                                (20004, 1, 10001, '투두4', current_date, true), -- 베팅 생성 실패
                                                                                (20005, 2, 10022, '투두5', date_add(current_date, interval -1 day), true), -- 나의 베팅 조회
                                                                                (20006, 3, 10023, '투두6', current_date, false),
                                                                                (20007, 1, 10002, '투두7', date_add(current_date, interval -1 day), false),
                                                                                (20008, 4, 10024, '투두8', current_date, false),
                                                                                (20009, 3, 10023, '투두9', date_add(current_date, interval -1 day), false),
                                                                                (20010, 1, 10002, '투두10', current_date, false);

--betting
insert into betting (betting_id, member_id, todo_id, name, is_achieved) values
                                                                            (30001, 1, 20001, '베팅1', null), -- 베팅 삭제
                                                                            (30002, 2, 20002, '베팅2', null), -- 베팅 삭제 실패
                                                                            (30003, 2, 20005, '베팅3', true), -- 나의 베팅 조회
                                                                            (30004, 3, 20006, '베팅4', null),
                                                                            (30005, 1, 20007, '베팅5', false),
                                                                            (30006, 4, 20008, '베팅6', null),
                                                                            (30007, 3, 20009, '베팅7', true);

--chatRoom
insert into chat_room (chat_room_id, betting_id) values
                                                    (30001, 30001),
                                                    (30002, 30002),
                                                    (30003, 30003),
                                                    (30004, 30004),
                                                    (30005, 30005),
                                                    (30006, 30006),
                                                    (30007, 30007);

update betting set chat_room_id = 30001 where betting_id = 30001;
update betting set chat_room_id = 30002 where betting_id = 30002;
update betting set chat_room_id = 30003 where betting_id = 30003;
update betting set chat_room_id = 30004 where betting_id = 30004;
update betting set chat_room_id = 30005 where betting_id = 30005;
update betting set chat_room_id = 30006 where betting_id = 30006;
update betting set chat_room_id = 30007 where betting_id = 30007;

--memberBetting
insert into member_betting (member_id, betting_id, cost, prediction) values
                                                                        (1, 30002, 10, true), -- 나의 베팅 조회
                                                                        (1, 30003, 10, false); -- 나의 베팅 조회

--itemType
insert into item_type (item_type_id, name, img, price, grade, exp, description) values
                                                                                    (1, '테스트 아이템1', '테스트 아이템 이미지1', 10, '테스트 아이템 등급1', 10, '테스트 아이템 설명1'),
                                                                                    (2, '테스트 아이템2', '테스트 아이템 이미지2', 20, '테스트 아이템 등급2', 20, '테스트 아이템 설명2'),
                                                                                    (3, '테스트 아이템3', '테스트 아이템 이미지3', 30, '테스트 아이템 등급3', 30, '테스트 아이템 설명3'),
                                                                                    (4, '테스트 아이템4', '테스트 아이템 이미지4', 40, '테스트 아이템 등급4', 40, '테스트 아이템 설명4'),
                                                                                    (20000, '아이템 이름', '이미지 url', 10, '아이템 등급', 10, '아이템 설명');