package com.test.address;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.address.entity.AddressLv1;
import com.test.address.entity.AddressLv2;
import com.test.address.entity.AddressLv3;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.insertAllAddresses(initService.readJson());
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public Map<String, Object> readJson() {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            try (InputStream jsonInputStream = new ClassPathResource("addresses.json").getInputStream()) {
                return mapper.readValue(jsonInputStream, typeRef);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read address JSON", e);
            }
        }

        @Transactional
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
                        }
                        lv1.addChild(lv2);
                    }
                }

                em.persist(lv1); // Cascade will persist all children
            }
        }

/*
        public void initMembers() {

        }
*/

    }
}
