package org.example.stage1.Services;


import org.example.stage1.DTOs.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    StudentDto addStudent(StudentDto student);

    StudentDto updateStudent(Long id, StudentDto student);

    void deleteStudent(Long id);
}
