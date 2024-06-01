package ru.khasanov.hogwarts.school_web_application.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.function.ServerResponse;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.AvatarRepository;
import ru.khasanov.hogwarts.school_web_application.repositories.FacultyRepository;
import ru.khasanov.hogwarts.school_web_application.repositories.StudentRepository;
import ru.khasanov.hogwarts.school_web_application.service.AvatarService;
import ru.khasanov.hogwarts.school_web_application.service.FacultyService;
import ru.khasanov.hogwarts.school_web_application.service.StudentService;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.khasanov.hogwarts.school_web_application.TestConstants.*;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void testPostGetStudent() throws Exception {
        // отправляемый объект
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", ID_OF_TEST_MVC_STUDENT);
        studentObject.put("name", NAME_OF_TEST_MVC_STUDENT);
        studentObject.put("age", AGE_OF_TEST_MVC_STUDENT);

        when(studentRepository.save(any(Student.class))).thenReturn(TEST_MVC_STUDENT);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_TEST_MVC_STUDENT))
                .andExpect(jsonPath("$.name").value(NAME_OF_TEST_MVC_STUDENT))
                .andExpect(jsonPath("$.age").value(AGE_OF_TEST_MVC_STUDENT));
    }

    @Test
    void testGetFindById() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(TEST_MVC_STUDENT));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/find/" + ID_OF_TEST_MVC_STUDENT)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_TEST_MVC_STUDENT))
                .andExpect(jsonPath("$.name").value(NAME_OF_TEST_MVC_STUDENT))
                .andExpect(jsonPath("$.age").value(AGE_OF_TEST_MVC_STUDENT));
    }

@Test
    void testDeleteStudentById() throws Exception {
        mockMvc.perform(
                delete("/student/" + ID_OF_TEST_MVC_STUDENT))
                .andExpect(status().isOk());
        verify(studentRepository,only()).deleteById(ID_OF_TEST_MVC_STUDENT);
    }



    @Test
    void testEditStudent() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ID_OF_TEST_MVC_STUDENT2);
        jsonObject.put("name", NAME_OF_TEST_MVC_STUDENT2);
        jsonObject.put("age", AGE_OF_TEST_MVC_STUDENT2);

        when(studentRepository.save(any(Student.class))).thenReturn(TEST_MVC_STUDENT2);

        mockMvc.perform(put("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentRepository, only()).save(TEST_MVC_STUDENT2);
    }

    @Test
    void testGetStudentsByAge() throws Exception {
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(
                STUDENT_5
                ,STUDENT_6
        ));
        when(studentRepository.findAll()).thenReturn(expectedStudents);
        int age = 26;
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getByAge/" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Student> responseStudents = objectMapper.readValue(
                jsonResponse
                ,new TypeReference<List<Student>>() {});
        assertThat(responseStudents.equals(expectedStudents));
    }

    @Test
    void testGetByAgeBetween() throws Exception {
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(
                STUDENT_1
                , STUDENT_2
                , STUDENT_3
        ));
        int min = 20;
        int max = 30;
        when(studentRepository.findByAgeBetween(min, max)).thenReturn(expectedStudents);
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getByAgeBetween?min=" + min + "&max=" + max)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Student> responseStudents = objectMapper.readValue(
                jsonResponse
                ,new TypeReference<List<Student>>() {});
        assertThat(responseStudents.equals(expectedStudents));
    }


}

