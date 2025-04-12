package com.test.address.service;

import com.test.address.repository.AddressLv1Repository;
import com.test.address.repository.AddressLv2Repository;
import com.test.address.repository.AddressLv3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressLv1Repository addressLv1Repository;
    private final AddressLv2Repository addressLv2Repository;
    private final AddressLv3Repository addressLv3Repository;

}
