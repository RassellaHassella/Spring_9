package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

//    public String getNoPrefix(){
//        String pr = "ROLE_";
//        return role.substring(pr.length());
//    }
    public Role(){}

    public Role(String name) {
        this.role = name;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        this.role = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        return role.equals(role.role);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + role .hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Role{role='" + role + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}