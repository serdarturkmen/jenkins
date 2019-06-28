package com.example.jenkins.mapper;

import com.example.jenkins.dto.StudentDTO;
import com.example.jenkins.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

public class StudentMapperTest {


    @Spy
    private StudentMapperImpl studentMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldMapEntityToDTO() {
        //Given
        Student student = Student.builder()
                .firstName("Serdar")
                .lastName("Turkmen").build();

        //When
        StudentDTO studentDTO = studentMapper.toDTO(student);

        //Then
        assertThat(studentDTO.getFirstName(), is("Serdar"));

    }



}