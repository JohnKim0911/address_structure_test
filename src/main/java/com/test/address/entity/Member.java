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

    public Member(String name, AddressLv1 addressLv1, AddressLv2 addressLv2, AddressLv3 addressLv3, String detailAddress) {
        this.name = name;
        this.addressLv1 = addressLv1;
        this.addressLv2 = addressLv2;
        this.addressLv3 = addressLv3;
        this.detailAddress = detailAddress;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();

        if (addressLv1 != null) {
            sb.append(addressLv1.getName()).append(" ");
        }
        if (addressLv2 != null) {
            sb.append(addressLv2.getName()).append(" ");
        }
        if (addressLv3 != null) {
            sb.append(addressLv3.getName()).append(" ");
        }
        if (detailAddress != null) {
            sb.append(detailAddress);
        }

        return sb.toString().trim();
    }

}
