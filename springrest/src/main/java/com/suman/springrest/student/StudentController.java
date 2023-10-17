package com.suman.springrest.student;

import java.time.LocalDate;
import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "{email}")
    public Optional<Student> findByEmail(@PathVariable("email") String email) {
        return studentService.findStudentByEmail(email);
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "/d/{email}")
    public void deleteByEmail(@PathVariable("email") String email) {
        studentService.deleteByEmail(email);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudentById(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudentById(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudentById(@PathVariable("studentId") Long studentId,@RequestParam(required = false) String name,@RequestParam(required = false) String email,@RequestParam(required = false) LocalDate dob){
        studentService.updateStudentById(studentId,name,email, String.valueOf(dob));
    }

    @PutMapping
    public void updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
    }
}
