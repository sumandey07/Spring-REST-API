package com.suman.springrest.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentByEmail(String email){
        Optional <Student> studentOptional = studentRepository.findStudentByEmail(email);
        if (studentOptional.isEmpty()){
            throw new IllegalStateException("Student with email: "+ email + " does not exist");
        }
        return studentOptional;
    }

    public void addNewStudent(Student student) {
        Optional <Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Email Already Taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudentById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("Student with id "+ studentId + " does not exist" );
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void deleteByEmail(String email) {
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        if (student.isEmpty()) {
            throw new IllegalStateException("Student with email: "+ email + " does not exist" );
        }
            studentRepository.deleteStudentByEmail(email);
    }

    @Transactional
    public void updateStudentById(Long studentId, String name, String email,String dob) {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException("Student with id " + studentId + " does not exist."));

        if (name !=null && !name.isEmpty() && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }

        if (email !=null && !email.isEmpty() && !Objects.equals(student.getEmail(),email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent())
                throw new IllegalStateException("Email Already Taken");
            student.setEmail(email);
        }
        if (dob !=null && !dob.isEmpty() && !Objects.equals(String.valueOf(student.getDob()),dob)){
            student.setDob(LocalDate.parse(dob));
        }
    }
    @Transactional
    public void updateStudent(Student student) {
        Student s = studentRepository.findStudentByEmail(student.getEmail()).stream().findFirst().orElse(null);
        if (s!=null) {
            if (student.getName() != null && !student.getName().isEmpty() && !Objects.equals(s.getName(), student.getName())) {
                s.setName(student.getName());
            }

            if (student.getEmail() != null && !student.getEmail().isEmpty() && !Objects.equals(s.getEmail(), student.getEmail())) {
                Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
                if (studentOptional.isPresent())
                    throw new IllegalStateException("Email Already Taken");
                s.setEmail(student.getEmail());
            }
            String DOB = String.valueOf(student.getDob());
            if (DOB != null && !DOB.isEmpty() && !Objects.equals(String.valueOf(s.getDob()), DOB)) {
                s.setDob(LocalDate.parse(DOB));
            }
        }
        else {
            throw new IllegalStateException("Student with email: "+ student.getEmail() + " does not exist" );
        }
    }

}
