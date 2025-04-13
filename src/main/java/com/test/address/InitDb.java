package com.test.address;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.address.entity.*;
import com.test.address.repository.AddressRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.insertAllAddresses(initService.readJson());
        initService.initMembers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final AddressRepository addressRepository;

        public Map<String, Object> readJson() {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            try (InputStream jsonInputStream = new ClassPathResource("addresses.json").getInputStream()) {
                return mapper.readValue(jsonInputStream, typeRef);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read address JSON", e);
            }
        }

        public void insertAllAddresses(Map<String, Object> addressMap) {
            for (Map.Entry<String, Object> lv1Entry : addressMap.entrySet()) {
                String lv1Name = lv1Entry.getKey();
                Object lv2Obj = lv1Entry.getValue();

                AddressLv1 lv1 = new AddressLv1(lv1Name);

                if (lv2Obj instanceof List) {
                    // Only 2 levels: AddressLv1 → AddressLv2
                    List<String> lv2Names = (List<String>) lv2Obj;
                    for (String lv2Name : lv2Names) {
                        AddressLv2 lv2 = new AddressLv2(lv2Name);
                        lv1.addChild(lv2);

                        // Insert Address (Lv1, Lv2, null)
                        Address address = new Address(lv1, lv2, null);
                        em.persist(address);
                    }
                } else if (lv2Obj instanceof Map) {
                    // 3 levels: AddressLv1 → AddressLv2 → AddressLv3
                    Map<String, List<String>> lv2Map = (Map<String, List<String>>) lv2Obj;
                    for (Map.Entry<String, List<String>> lv2Entry : lv2Map.entrySet()) {
                        AddressLv2 lv2 = new AddressLv2(lv2Entry.getKey());
                        List<String> lv3Names = lv2Entry.getValue();
                        for (String lv3Name : lv3Names) {
                            AddressLv3 lv3 = new AddressLv3(lv3Name);
                            lv2.addChild(lv3);

                            // Insert Address (Lv1, Lv2, Lv3)
                            Address address = new Address(lv1, lv2, lv3);
                            em.persist(address);
                        }
                        lv1.addChild(lv2);
                    }
                }

                em.persist(lv1); // Persist hierarchy
            }
        }

        public void initMembers() {
            long addressSize = addressRepository.count();

            for (int i = 0; i < 100; i++) {
                Address address = getRandomAddress(addressSize);
                Member member = new Member("홍길동", address, "xxx로 어딘가");
                em.persist(member);
            }
        }

        private Address getRandomAddress(long addressSize) {
            Random random = new Random();
            long randomAddressId = 1 + random.nextLong(addressSize); //address IDs start from 1
            return addressRepository.findById(randomAddressId).get();
        }

    }
}
