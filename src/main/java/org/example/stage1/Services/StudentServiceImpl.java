package org.example.stage1.Services;


import org.example.stage1.DTOs.StudentDto;
import org.example.stage1.Entities.Student;
import org.example.stage1.Exceptions.AlreadyExists;
import org.example.stage1.Exceptions.NotExists;
import org.example.stage1.Mappers.StudentMapper;
import org.example.stage1.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotExists("Student with ID " + id + " does not exist"));
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    public StudentDto addStudent(StudentDto studentDto) {
        if(studentRepository.findByEmail(studentDto.getEmail()).isPresent()){
            throw new AlreadyExists("Student with email " + studentDto.getEmail() + " already exists");
        }

        Student student = studentMapper.toEntity(studentDto);

        Student added = studentRepository.save(student);
        return studentMapper.toDto(added);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long id,StudentDto studentDto) {
        if(studentDto.getId() != null && !studentDto.getId().equals(id)) {
            throw new AlreadyExists("Student ID mismatch: provided ID " + studentDto.getId() + " does not match path ID " + id);
        }

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotExists("Student with ID " + id + " does not exist"));

        studentRepository.findByEmail(studentDto.getEmail())
                .ifPresent(student -> {
                    if (!student.getId().equals(id)) {
                        throw new AlreadyExists("Student with email " + studentDto.getEmail() + " already exists");
                    }
                });

        studentMapper.updateEntityFromDto(existingStudent, studentDto);

        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toDto(updatedStudent);
    }


    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new NotExists("Student with ID " + id + " does not exist");
        }

        studentRepository.deleteById(id);
    }
}
