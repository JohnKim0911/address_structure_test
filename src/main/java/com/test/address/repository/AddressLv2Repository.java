package com.test.address.repository;

import com.test.address.entity.AddressLv2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressLv2Repository extends JpaRepository<AddressLv2, Long> {
    List<AddressLv2> findByName(String name);
}
