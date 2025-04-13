-- H2 DB 모든 테이블 지우기
--drop all objects;

-- 모든 테이블 조회
SELECT * FROM MEMBER;
SELECT * FROM ADDRESS;
SELECT * FROM ADDRESS_LV1;
SELECT * FROM ADDRESS_LV2;
SELECT * FROM ADDRESS_LV3;

-- 주소 조회 (주소명까지 join)
SELECT
    a.address_id,
    a.address_lv1_id AS lv1_id,
    a.address_lv2_id AS lv2_id,
    a.address_lv3_id AS lv3_id,
    lv1.name AS lv1_name,
    lv2.name AS lv2_name,
    lv3.name AS lv3_name
FROM address a
LEFT JOIN address_lv1 lv1 ON a.address_lv1_id= lv1.address_lv1_id
LEFT JOIN address_lv2 lv2 ON a.address_lv2_id= lv2.address_lv2_id
LEFT JOIN address_lv3 lv3 ON a.address_lv3_id= lv3.address_lv3_id;

-- 회원 조회 (주소명까지 join)
SELECT
    m.member_id,
    m.name,
    m.address_id,
    lv1.name AS lv1,
    lv2.name AS lv2,
    lv3.name AS lv3,
    m.detail_address
FROM MEMBER m
LEFT JOIN address on m.address_id = address.address_id
LEFT JOIN address_lv1 AS lv1 on address.address_lv1_id = lv1 .address_lv1_id
LEFT JOIN address_lv2 AS lv2 on address.address_lv2_id = lv2 .address_lv2_id
LEFT JOIN address_lv3 AS lv3 on address.address_lv3_id = lv3 .address_lv3_id;