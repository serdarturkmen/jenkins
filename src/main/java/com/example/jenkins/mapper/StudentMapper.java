package com.example.jenkins.mapper;

import com.example.jenkins.dto.StudentDTO;
import com.example.jenkins.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper {

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);

}
