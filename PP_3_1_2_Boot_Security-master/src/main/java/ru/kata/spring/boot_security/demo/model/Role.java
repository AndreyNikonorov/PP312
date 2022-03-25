package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid", nullable = false)
    private Long roleid;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Role(){}

    public Role(String roleName) {
        this.name = roleName;
    }




    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(long id) {
        this.roleid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
