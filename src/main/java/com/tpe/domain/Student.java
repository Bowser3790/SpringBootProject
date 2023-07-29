package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="tbl_name")
@Getter //-> This creates getter and setter, you can use the annotations for this - Lombok library offers this
@Setter //-> see getter notes
@ToString //-> this creates a string method.
@AllArgsConstructor //-> this creates constructor for all arguments that you will need a value for the args in the object creation
@NoArgsConstructor //-> this creates an empty constructor.
// @RequiredArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) //-> we dont want to create a setter for this it should be system generated
    private Long id;

    @Column(nullable = false, length = 25)
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be white space")
    @Size(min=2, max = 25, message = "firstName ${validatedValue} must be between {min} and {max}")
    private String name;

    @Column(nullable = false, length = 25)
    //@Setter -> this will give you a setter at variable level and this will be the only avail variable. (you would need to put setter
    // individually for each one that you wanted as an argument.
    private String lastName;

    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "please provide valid email.") // it will check @ and .

    private String email;

    private String phoneNumber;

    @Setter(AccessLevel.NONE) // -> system generated createdDate do not want values to be given for this
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student") // see notes for full explaination 5/22/2023 at 6:08PM
    private List<Book> books = new ArrayList<>();

}
