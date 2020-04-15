package com.example.SpringSecurity.entity.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String email;

    @Embedded
    private Name name;

//    private String username;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "UserRole",joinColumns=@JoinColumn( name = "UserId",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "RoleId",referencedColumnName = "id"))
    private List<Role> roles;


    private boolean is_deleted;
    private boolean is_active = false;
    private boolean isAccountNotLocked = true;
    private boolean is_enabled;

    //Getters and Setters


    public boolean isAccountNotLocked() {
        return isAccountNotLocked;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        isAccountNotLocked = accountNotLocked;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_unabled) {
        this.is_enabled = is_unabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddresses(Address address) {
        if(addresses == null){
            addresses = new ArrayList<>();
        }
        addresses.add(address);
        address.setUser(this);
        this.setAddresses(addresses);
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name=" + name +
                ", password='" + password + '\'' +
//                ", addresses=" + addresses +
                ", grantAuthorities=" + roles +
                ", is_deleted=" + is_deleted +
                ", is_active=" + is_active +
                '}';
    }
}

