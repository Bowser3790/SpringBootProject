package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // method is now ready to be used. -> now we can send this to the controller to be routed back to the user.
        // this code is talking to the student repo class. We just queried the database and
        // now we need the info to come to the service class. The info is stored in the List we just created in the repo class.
        // the studentService class talks to the repo class -> database
        // on the other side the service class speaks with the controller class
        // service class handles all logic before sending back to controller class.

    }

    public void saveStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email already exists in Database");
        }
        studentRepository.save(student);

    }

    // remember that in the service class this is where we handle all the logic
    // the first question we need to ask ourselves is what type of issues coule we face here.
    // null value,
    public Student findStudentById(Long id) {
        // findById() will return Optional class (might not have a student associtated with the id and it will return an empty object.
        // so we needed to customize an exception.
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + id + " is not found.")
        ); // we cannot just create a student object here because id is an optional data field.
        return student;
    }


    public void deleteStudentById(Long id) {
        Student student = findStudentById(id);
        studentRepository.delete(student);
    }

    // There are three scenarios that we have to account for with DTO (we are creating DTO because we dont want anyone to have access to our data in repository class and database.) The DTO adds an additional layer to specifically talk to repository class.)
    /*
    Scenario #1:
    If the user enters updated email and the email already exists in database
    ABC@gmail.com -> new email: ABC@gmail.com
    Same email you already have.
    --> updates email


    Scenario #2:
    If the user enters updated email and the updated email already exists in the database
    example if someone else already is using the email.
    --> throws exception


    Scenario #3:
    If user enters updated email and the updated email is not existing, you are able to successfully update the email.
     --> update
     */

    public void updateStudent(Long id, StudentDTO studentDTO){
        // check if new email exists in database
        boolean existsEmail = studentRepository.existsByEmail(studentDTO.getEmail());

        Student existingStudent = findStudentById(id);

        if(existsEmail && !existingStudent.getEmail().equals(studentDTO.getEmail())){
            // new email exists in DB AND old email is not new email
            throw new ConflictException("email already exists");

        // Let's say everything is OK and new email can update (no exception is thrown)
            // we now need to Map our data from DTO class to the @Entity class.
            // below we are mapping our student DTO to our student entity.
        }
        existingStudent.setName(studentDTO.getName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setGrade(studentDTO.getGrade());

        // currently this is in RAM data
        // we have not saved to the database
        // with the below code we will save persistent data.
        studentRepository.save(existingStudent);
    }


    public Page<Student> getAllStudentsWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    // what happens if we query database for a grade and its not there?
    // we are going to get an empty list
    public List<Student> findStudentByGrade(Integer grade) {

        return studentRepository.findStudentByGrade(grade);
    }

    // what are the scenarios here?
    // 1. we get the student by id
    // 2. we do not get the student by id, if we only have 10 data id's and we query for id 100...

    public StudentDTO findStudentByDTO(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(
                ()-> new ResourceNotFoundException( id + " is not valid. Enter a new Value."));
        }

    }

