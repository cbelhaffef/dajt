package com.cbelhaffef.dajt.api.user;

import com.cbelhaffef.dajt.api.folder.Folder;
import com.cbelhaffef.dajt.api.office.Office;
import com.cbelhaffef.dajt.api.role.Role;
import com.cbelhaffef.dajt.api.role.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="user")
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

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name="office_id")
    private Office office;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee")
    private List<Folder> folders;

    public User() {
    }

    public User(String username, String firstname, String lastname, String email, Office office) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.office = office;
    }

    public User(String username, String firstname, String lastname, String email, Office office, String password,Set<Role> roles, boolean enabled){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.office = office;
        this.setPassword(new BCryptPasswordEncoder().encode(password));
        this.setRoles(roles);
        this.setEnabled(enabled);
    }

    public String getFullName(){
        return this.firstname + " " + this.lastname;
    }

    public List<String> getRolesCodes() {
        return this.getRoles() != null ? this.getRoles().stream().map(Role::getCode).map(RoleCodeEnum::name).collect(Collectors.toList()) : null;
    }

    public String[] getRolesCodesArray() {
        List<String> rolesCodes =  getRolesCodes();
        return rolesCodes != null ? rolesCodes.stream().toArray(String[]::new) : null;
    }
}
