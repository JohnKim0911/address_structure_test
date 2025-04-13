package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lv1_id")
    private AddressLv1 lv1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lv2_id")
    private AddressLv2 lv2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lv3_id")
    private AddressLv3 lv3;

    public Address(AddressLv1 lv1, AddressLv2 lv2, AddressLv3 lv3) {
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
    }
}
