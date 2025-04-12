package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address_lv1")
public class AddressLv1 {

    @Id
    @GeneratedValue
    @Column(name = "addressLv1_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AddressLv2> childList = new ArrayList<>();

    public AddressLv1(String name) {
        this.name = name;
    }

    public void addChild(AddressLv2 child) {
        childList.add(child);
        child.setParent(this);
    }
}
