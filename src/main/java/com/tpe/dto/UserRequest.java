package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Enter First Name")
    private String firstName;

    @NotBlank(message = "Enter Last Name")
    private String lastName;

    @NotBlank(message = "Enter userName")
    @Size(min=5,max=10,message="Please provide a username between {min} and {max}")
    private String username;

    @NotBlank(message = "Enter password")
    private String password;

}
