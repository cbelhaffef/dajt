package com.cbelhaffef.dajt.model.user;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.office.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long userId;

    private String username = "";
    @JsonIgnore
    private String password = "";
    private String firstname;
    private String lastname;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    //@JsonIgnore
    @JsonIgnore
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="office_id")
    private Office office;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee")
    List<Folder> folders;

    public User(){
        this("new", "PASSWORD", Role.USER, "new", "new", true);
    }

    public User(String username, String password, String firstname, String lastname){
        this(username, password, Role.USER, firstname, lastname, true);
    }

    public User(String username, String password, Role role, String firstname, String lastname){
        this(username, password, role, firstname, lastname, true);
    }

    public User(String username, String password, Role role, String firstname, String lastname, boolean isActive){
        this.setUsername(username);
        this.setEmail(username);
        this.setPassword(new BCryptPasswordEncoder().encode(password));
        this.setRole(role);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setActive(isActive);
    }

    public String getFullName(){
        return this.firstname + " " + this.lastname;
    }
}
