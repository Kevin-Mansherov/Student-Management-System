package org.example.stage1.controllers;


import org.example.stage1.DTOs.StudentDto;
import org.example.stage1.Response.StandardResponse;
import org.example.stage1.Services.StudentService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    //get all students
    @GetMapping()
    public ResponseEntity<StandardResponse> getAllStudents(){
        List<StudentDto> students = studentService.getAllStudents();
        StandardResponse response= new StandardResponse("success", students,null);
        return ResponseEntity.ok(response);
    }

    //get student by id
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getStudent(@PathVariable Long id) {
        StudentDto student = studentService.getStudentById(id);
        StandardResponse response = new StandardResponse("success", student,null, null);
        return ResponseEntity.ok(response);
    }

    //add a new student
    @PostMapping()
    public ResponseEntity<StandardResponse> addStudent(@Valid @RequestBody StudentDto student) {
        StudentDto added = studentService.addStudent(student);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(added.getId())
                .toUri();

        StandardResponse response = new StandardResponse("success", added, null);

        return ResponseEntity.created(location).body(response);
    }

    //update student
    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto student) {
        StudentDto updated = studentService.updateStudent(id, student);
        StandardResponse response = new StandardResponse("success", updated, null);
        return ResponseEntity.ok(response);
    }

    //delete student
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
