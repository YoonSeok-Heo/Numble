package com.numble.toss.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class UserEntity implements Persistable<String> {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role){
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }
    @Column
    private String deleteYn;

    @Column(nullable = false)
    private String joinDate;

    @Column(nullable = false)
    private String lastModifiedDate;



    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public String getId() {
        return email;
    }

}
