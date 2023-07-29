package com.tpe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// we are creating this class for the username and security.

// what is the relationship between user and role? many to many
// this could be an interview question => one to many => many users can have many roles.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25,nullable = false)
    private String firstName;

    @Column(length = 25,nullable = false)
    private String lastName;
    @Column(length = 25,nullable = false, unique = true)
    private String userName;

    @Column(length = 255,nullable = false)// base64 -> the password will convert to 64 characters
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // If we want to see the user's role directly we need to set fetch to Eager.
    @JoinTable(name= "tbl_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>(); // to store unique roles Student, Admin
    // remember if 2nd part is many by default its fetch type is lazy
    // if 2nd part is one it is eager by default... if you want to change this you need to explicitly set to EAGER like we did above.








}
