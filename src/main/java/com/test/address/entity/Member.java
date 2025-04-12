package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressLv1_id")
    private AddressLv1 addressLv1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressLv2_id")
    private AddressLv2 addressLv2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressLv3_id")
    private AddressLv3 addressLv3;

    private String detailAddress;
    private String fullAddress;

    public Member(String name, AddressLv1 addressLv1, AddressLv2 addressLv2, AddressLv3 addressLv3, String detailAddress) {
        this.name = name;
        this.addressLv1 = addressLv1;
        this.addressLv2 = addressLv2;
        this.addressLv3 = addressLv3;
        this.detailAddress = detailAddress;
        this.fullAddress = addressLv1.getName() + " " + addressLv2.getName();
        if (addressLv3 != null) {
            this.fullAddress += " " + addressLv3.getName();
        }
        this.fullAddress += " " + detailAddress;
    }
}
