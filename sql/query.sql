-- H2 DB 모든 테이블 지우기
--drop all objects;

-- 모든 테이블 조회
SELECT * FROM MEMBER;
SELECT * FROM ADDRESS_LV1;
SELECT * FROM ADDRESS_LV2;
SELECT * FROM ADDRESS_LV3;

-- 주소 db 조인. (한눈에 전체보기)
SELECT
    lv1.name AS lv1,
    lv2.name AS lv2,
    lv3.name AS lv3,
    lv1.address_lv1_id AS lv1_id,
    lv2.address_lv2_id AS lv2_id,
    lv3.address_lv3_id AS lv3_id
FROM
    address_lv1 lv1
JOIN
    address_lv2 lv2 ON lv2.address_lv1_id = lv1.address_lv1_id
LEFT JOIN
    address_lv3 lv3 ON lv3.address_lv2_id = lv2.address_lv2_id

