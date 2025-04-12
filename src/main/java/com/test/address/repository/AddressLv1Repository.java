package com.test.address.repository;

import com.test.address.entity.AddressLv1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressLv1Repository extends JpaRepository<AddressLv1, Long> {
    AddressLv1 findByName(String name);
}
