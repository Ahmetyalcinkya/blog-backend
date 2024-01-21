package com.blog.BlogBackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authority", schema = "blog")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "authority")
    private String authority;

    @OneToMany(mappedBy = "authority",cascade = CascadeType.ALL)
    private List<User> users;

    public void addUser(User user){
        if(users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
