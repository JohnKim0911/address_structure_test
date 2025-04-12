package com.test.address.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressLv2 {

    @Id
    @GeneratedValue
    @Column(name = "addressLv2_id")
    private Long id;

    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressLv1_id")
    private AddressLv1 parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AddressLv3> childList = new ArrayList<>();

    public AddressLv2(String name) {
        this.name = name;
    }

    public void addChild(AddressLv3 child) {
        childList.add(child);
        child.setParent(this);
    }

}
