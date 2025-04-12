package com.test.address;


import com.test.address.entity.AddressLv1;
import com.test.address.entity.AddressLv2;
import com.test.address.entity.AddressLv3;
import com.test.address.entity.Member;
import com.test.address.repository.AddressLv1Repository;
import com.test.address.repository.AddressLv2Repository;
import com.test.address.repository.AddressLv3Repository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initAddress1();
        initService.initAddress2();
        initService.initMembers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final AddressLv1Repository addressLv1Repository;
        private final AddressLv2Repository addressLv2Repository;
        private final AddressLv3Repository addressLv3Repository;

        /**
         *  "서울특별시": ["종로구", "중구", "용산구"]
         */
        public void initAddress1() {
            AddressLv2 addressLv2_a = new AddressLv2("종로구");
            AddressLv2 addressLv2_b = new AddressLv2("중구");
            AddressLv2 addressLv2_c = new AddressLv2("용산구");
            em.persist(addressLv2_a);
            em.persist(addressLv2_b);
            em.persist(addressLv2_c);

            AddressLv1 addressLv1 = new AddressLv1("서울특별시");
            em.persist(addressLv1);

            addressLv1.addChild(addressLv2_a);
            addressLv1.addChild(addressLv2_b);
            addressLv1.addChild(addressLv2_c);
        }

        /**
         *   "경기도": {
         *     "수원시": ["장안구", "권선구"],
         *     "성남시": ["수정구", "중원구", "분당구"],
         *    }
         */
        public void initAddress2() {
            AddressLv3 lv3_a = new AddressLv3("장안구"); //수원시
            AddressLv3 lv3_b = new AddressLv3("권선구"); //수원시
            em.persist(lv3_a);
            em.persist(lv3_b);

            AddressLv3 lv3_c = new AddressLv3("수정구"); //성남시
            AddressLv3 lv3_d = new AddressLv3("중원구"); //성남시
            AddressLv3 lv3_e = new AddressLv3("분당구"); //성남시
            em.persist(lv3_c);
            em.persist(lv3_d);
            em.persist(lv3_e);

            AddressLv2 lv2_a = new AddressLv2("수원시");
            AddressLv2 lv2_b = new AddressLv2("성남시");
            em.persist(lv2_a);
            em.persist(lv2_b);

            AddressLv1 lv1 = new AddressLv1("경기도");
            em.persist(lv1);

            lv1.addChild(lv2_a); //경기도 수원시
            lv1.addChild(lv2_b); //경기도 성남시

            lv2_a.addChild(lv3_a); //경기도 수원시 장안구
            lv2_a.addChild(lv3_b); //경기도 수원시 권선구

            lv2_b.addChild(lv3_c); //경기도 성남시 수정구
            lv2_b.addChild(lv3_d); //경기도 성남시 중원구
            lv2_b.addChild(lv3_e); //경기도 성남시 분당구
        }

        public void initMembers() {
            createMember("홍길동", "서울특별시", "종로구", null, "xx로 어딘가");
            createMember("김길동", "서울특별시", "중구", null, "xx로 어딘가");
            createMember("이길동", "서울특별시", "용산구", null, "xx로 어딘가");

            createMember("장길동", "경기도", "수원시", "장안구", "xx로 어딘가");
            createMember("송길동", "경기도", "수원시", "권선구", "xx로 어딘가");
            createMember("길동", "경기도", "성남시", "수정구", "xx로 어딘가");
            createMember("황길동", "경기도", "성남시", "중원구", "xx로 어딘가");
            createMember("오길동", "경기도", "성남시", "분당구", "xx로 어딘가");
        }

        private void createMember(String name, String addressLv1, String addressLv2, String addressLv3, String detailAddress) {
            AddressLv1 lv1 = addressLv1Repository.findByName(addressLv1);

            List<AddressLv2> lv2List = addressLv2Repository.findByName(addressLv2);
            AddressLv2 lv2 = lv2List.get(0);

            AddressLv3 lv3 = null;
            if (addressLv3 != null) {
                List<AddressLv3> lv3List = addressLv3Repository.findByName(addressLv3);
                lv3 = lv3List.get(0);
            }

            Member member = new Member(name, lv1, lv2, lv3, detailAddress);
            em.persist(member);
        }
    }
}
