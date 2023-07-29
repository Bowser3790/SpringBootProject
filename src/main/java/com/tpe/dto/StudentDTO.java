package com.tpe.dto;


import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be white space")
    @Size(min=2, max = 25, message = "firstName ${validatedValue} must be between {min} and {max}")
    private String name;


    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be white space")
    @Size(min=2, max = 25, message = "firstName ${validatedValue} must be between {min} and {max}")
    private String lastName;

    private Integer grade;

    @Email(message = "please provide valid email.") // it will check @ and .
    private String email;

    private String phoneNumber;

    private LocalDateTime createdDate = LocalDateTime.now();

    // if we do not have this constructor with the Student parameter then we will not be able to create new DTO object with
    // the student parameter. This connects with the StudentRepository Class (  @Query( "SELECT new com.tpe.dto.StudentDTO)
    public StudentDTO (Student student){
        this.id = student.getId();
        this.name = student.getName();
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
        this.createdDate = student.getCreatedDate();
    }

}
