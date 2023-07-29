package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// note that JpaRepository comes with a lot of basic queries that we will not have to write queries for.
// this will make it much easier to use such as we can use findAll() rather than SELECT * from std_student; etc.
@Repository // we are extending from JpaRepository Spring will understand that it is a repo class
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);

    List<Student> findByLastName(String lastName);

    // so in order to get these methods we need to write JPQL @Query
    @Query ("SELECT s FROM Student s WHERE s.grade=:pGrade")
    List<Student> findStudentByGrade(@Param("pGrade") Integer grade);

    // using SQL

    @Query(value = "SELECT * FROM tbl_student s WHERE s.grade=:pGrade", nativeQuery = true)
    List<Student> findStudentByGradeWithSQL(@Param("pGrade") Integer grade);

    // using JPQL we are going to map student entity object to DTO
    // here we are returning studentDTO to service layer.

    @Query( "SELECT new com.tpe.dto.StudentDTO (s) FROM Student s WHERE s.id = :id")
    Optional<StudentDTO> findStudentDTOById(@Param("id")Long id);
    // why are we using Optional? because we may want to get empty class instead of getting null.
    // Homework is try to write a query where grade is between two integers and you need 2 @Param to get both parameters.
    // Using JPQL we can map our query to the DTO class giving us more security.
    // if you look at the other queries we are querying from the Student Class directly and this will return all the data from the student object we request.
    // this can be a security issue****
    // that is why we use the DTO class because this class we only have the bare necessities we need.
    // you can structure the DTO class with whatever variables you want without data like SSN, DOB, Address etc..
    // we can have variables like first name , lastName, Grade, age, major, etc...
}

