package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressLv3 {

    @Id
    @GeneratedValue
    @Column(name = "addressLv3_id")
    private Long id;

    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressLv2_id")
    private AddressLv2 parent;

    public AddressLv3(String name) {
        this.name = name;
    }
}
