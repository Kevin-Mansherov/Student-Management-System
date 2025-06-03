package org.example.stage1.Mappers;




import org.example.stage1.DTOs.StudentDto;
import org.example.stage1.Entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {


    public Student toEntity(StudentDto student) {
        if (student == null) {
            return null;
        }

        Student entity = new Student();
        entity.setId(student.getId());
        entity.setFirstName(student.getFirstName());
        entity.setLastName(student.getLastName());
        entity.setEmail(student.getEmail());
        entity.setAge(student.getAge());

        return entity;
    }

    public StudentDto toDto(Student entity){
        if(entity == null) return null;

        StudentDto dto = new StudentDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());
        return dto;

    }

    public void updateEntityFromDto(Student entity, StudentDto studentDto) {
        if (studentDto == null || entity == null) {
            return;
        }

        entity.setFirstName(studentDto.getFirstName());
        entity.setLastName(studentDto.getLastName());
        entity.setEmail(studentDto.getEmail());
        entity.setAge(studentDto.getAge());
    }
}
