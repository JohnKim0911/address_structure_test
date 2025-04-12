package com.test.address.repository;

import com.test.address.entity.AddressLv3;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressLv3Repository extends JpaRepository<AddressLv3, Long> {
    List<AddressLv3> findByName(String name);
}
