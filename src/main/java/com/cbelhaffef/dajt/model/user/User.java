package com.cbelhaffef.dajt.model.user;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.office.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
    private String password = "";
    private String firstName;
    private String lastName;
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

    public User(String username, String password, String firstName, String lastName){
        this(username, password, Role.USER, firstName, lastName, true);
    }

    public User(String username, String password, Role role, String firstName, String lastName){
        this(username, password, role, firstName, lastName, true);
    }

    public User(String username, String password, Role role, String firstName, String lastName, boolean isActive){
        this.setUsername(username);
        this.setEmail(username);
        this.setPassword(new BCryptPasswordEncoder().encode(password));
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setActive(isActive);
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
