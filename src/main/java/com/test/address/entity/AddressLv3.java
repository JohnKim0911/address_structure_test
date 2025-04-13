package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address_lv3", uniqueConstraints = @UniqueConstraint(columnNames = {"address_lv2_id", "name"}))
public class AddressLv3 {

    @Id
    @GeneratedValue
    @Column(name = "address_lv3_id")
    private Long id;

    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lv2_id")
    private AddressLv2 parent;

    public AddressLv3(String name) {
        this.name = name;
    }
}
