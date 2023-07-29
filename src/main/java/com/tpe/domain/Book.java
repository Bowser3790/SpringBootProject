package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_book")
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName") //this name is used for json data
    // @Column(name = "book_book") //this is for column name in table
    private String name;

    @ManyToOne
    @JoinColumn(name="student_id")
    @JsonIgnore()// this is stopping infinite loop and stack overflow// what is happening? Remember when we deleted one
    // part of the string method when we were using Hibernate? this is saying take JSON code and deserialize to java and
    // java is saying deserialize to JSON. so this
    // is causing both sides to serialize and deserialize without stopping the code when finished it continues to run.
    private Student student;

    // to ignore infinite calling
    /*
    @JsonIgnore is a Java annotation that tells the Jackson library
    to ignore a property during JSON data conversion.
    */

    //getter


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Student getStudent() {
        return student;
    }
}
