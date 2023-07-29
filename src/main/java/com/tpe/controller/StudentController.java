package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.repository.StudentRepository;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller // from this class when you send request there is a dispatcher servlet and this handler will send to the correct path.
@RestController // used to create RESTful API // but this also has the functionality of controller within the annotation so you dont need controller annotation.
@RequestMapping("/students") // http://localhost:8000/students
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;


    // method to get all students
    @GetMapping // we are going to read data, we need to use @GetMapping = getMethod() // http://localhost:8000/students + Get
    public ResponseEntity<List<Student>> getAll(){ // ResponseEntity has to do with the response codes from a request HTTP status Code + Students List
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students); // ok is for 200 status code.

    }
    //@RequestBody -notes- we will send JSON data and that should be mapped to Student Object. // http://localhost:8000/students + Post + JSON -> will serialize or deserialize to JSON object. With POST it will be deserialized
    // @Valid makes sure the data is not sent to the next layer if the data is not valid. null, blank etc.
    // Method used to create/add ** important
    @PostMapping() //
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        // @Valid we are checking if the response is null if it is then we will send from @Valid, If not then we will @RequestBody, and send to the next layer, mapped to student object.
        // @RequestBody takes the JSON data from Postman and maps the data to our student.  in the CreateStudent folder in postman you have to switch to POST method, write the JSON code in the Body tab-> this is where the @RequestBody is from.
        // note that when you are creating JSON data in Postman it is trying to match the student constructor parameters the same order in your code.
        studentService.saveStudent(student); // this is sending student to student service class
        Map<String,String> map = new HashMap<>();
        map.put("message", "New student has been added successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    // method to get student by Id
    // method to get student by Id using path variable

    @GetMapping("{id}") // you cannot use the same parameter /students because that is already in use for GetMapping annotation above.
    // http://localhost:8000/students/1
    public ResponseEntity<Student> getStudentByUsingPath(@PathVariable("id") Long id){
        Student student = studentService.findStudentById(id); // this is sending the id to the studentService class. save it to the student class.

        return new ResponseEntity<>(student, HttpStatus.OK); // same as ResponseEntity.ok(students)
    }

    // method to get student by Id using RequestParam
    @GetMapping("/query") // http://localhost:8080/query?id=1 -> this is how we type code into URL in postman ...
    public ResponseEntity<Student> getStudentByUsingRequestParam(@PathVariable("id")Long id){
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok(student);
        // both of these methods do the same thing (get Values from path)
        // best practice: where there are multiple parameters, then RequestParam is suggested because we can write
        // variable names and its more understandable.
    }

    // method to delete from database.

    @DeleteMapping("/{id}") // http://localhost:8080/student/id + DELETE
    public ResponseEntity<Map<String,String>> deleteStudentById(@PathVariable()Long id){
        studentService.deleteStudentById(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Student deleted successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    // method to update student
    @PutMapping({"/{id}"}) // http://localhost:8080/student/id + PUT + JSON

    public ResponseEntity<Map<String, String>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id, studentDTO);
        Map<String, String> map = new HashMap<>();
        map.put("message", "update student successful!");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // why do we create pageable?
    // when you have thousands of results say we are trying to get all students from a large university
    // and we call getAll()... this will return all of those students and we will have some issues, latency, or maybe shut the system down.
    // when we use pageable we can access our database with a predictible amount of results. if we want 20 per page or 10 per page we can request that amount.
    // will improve latency and will not shut down our program.

    @GetMapping("/page")  // http://localhost:8080/student/page?page=2&size=3&sort=lastName&direction=ASC + GET
    public ResponseEntity<Page<Student>>getAllStudentsWithPage(
            @RequestParam("page") int page, // page number starting from 0
            @RequestParam("size") int size, // how many results do we want per page
            @RequestParam("sort") String prop, // optional: if I want to sort... by lastName, grade, email whatever it might be
            @RequestParam("direction") Sort.Direction direction // sorting type ascending or descending
            // note that page size sort are all variable names if we use p, s, s, d these will be the variable names that show on Postman
            // we need to pass these values into pageable below (page,size,Sort.by(direction,prop)) for this to be read by postman.

    ){

        // create pageable object to be sent to Database
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop)); // property is which field do I want to sort by?
        Page<Student> studentsByPage = studentService.getAllStudentsWithPage(pageable);
        return ResponseEntity.ok(studentsByPage);



    }

    // note that we need an endpoint for anything that we want to search for, for every single search by lastName, id, grade etc.

    // here we are going to create an endpoint for method() searching by lastName;
    // Create method that searches by lastname
    @GetMapping("/queryLastName") // http://localhost:8080/students/queryLastName?lastName=jones

    public ResponseEntity<List<Student>>getSudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> students = studentService.findStudentByLastName(lastName);
        return ResponseEntity.ok(students);
    }

    // create method to get students by grade
    // note we are using List<> in search by grade and search by lastName because there could be values that are the same
    // if we use Map the key cannot be the same so you can use map with findByEmail because email needs to be unique.
    @GetMapping("/grade/{grade}") // http://localhost:8080/students/grade/90

    public ResponseEntity<List<Student>>getStudentByGrade(@PathVariable("grade") Integer grade){
        List<Student> students = studentService.findStudentByGrade(grade);
        return ResponseEntity.ok(students);
    }

    // can we get Data as DTO from Database?
    // Using JPQL we can map Entity Class to DTO using constructor we have set in DTO

    @GetMapping("/query/DTO") // http://localhost:8080/students/query/DTO?id=1
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id")Long id){
        StudentDTO studentDTO = studentService.findStudentByDTO(id);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping("/welcome")  //http://localhost:8080/students/welcome

    public String welcome(HttpServletRequest request){

        logger.warn("----------------Welcome{}", request.getServletPath()); // this servlet path will be stored in {} next to Welcome.
        return "Welcome to Student Controller";
        // what is the servlet path?? the HTTP localhost code above....
    }






}
