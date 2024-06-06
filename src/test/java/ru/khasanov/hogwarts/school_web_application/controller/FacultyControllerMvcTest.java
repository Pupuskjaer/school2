package ru.khasanov.hogwarts.school_web_application.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.FacultyRepository;
import ru.khasanov.hogwarts.school_web_application.service.FacultyService;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.khasanov.hogwarts.school_web_application.TestConstants.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void testPostFaculty() throws Exception {

        // отправляемый объект
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", ID_OF_TEST_MVC_FACULTY);
        facultyObject.put("name", NAME_OF_TEST_MVC_FACULTY);
        facultyObject.put("color", COLOR_OF_TEST_MVC_FACULTY);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(TEST_MVC_FACULTY);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_TEST_MVC_FACULTY))
                .andExpect(jsonPath("$.name").value(NAME_OF_TEST_MVC_FACULTY))
                .andExpect(jsonPath("$.color").value(COLOR_OF_TEST_MVC_FACULTY));
    }

    @Test
    void testEditFaculty() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ID_OF_TEST_MVC_FACULTY2);
        jsonObject.put("name", NAME_OF_TEST_MVC_FACULTY2);
        jsonObject.put("color", COLOR_OF_TEST_MVC_FACULTY2);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(TEST_MVC_FACULTY2);

        mockMvc.perform(put("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facultyRepository, only()).save(TEST_MVC_FACULTY2);
    }

    @Test
    void testDeleteFaculty() throws Exception {

        mockMvc.perform(
                        delete("/faculty/" + ID_OF_TEST_MVC_FACULTY))
                .andExpect(status().isOk());
        verify(facultyRepository,only()).deleteById(ID_OF_TEST_MVC_FACULTY);
    }

    @Test
    void testGetFacultyById() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(TEST_MVC_FACULTY));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find/" + ID_OF_TEST_MVC_FACULTY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_TEST_MVC_FACULTY))
                .andExpect(jsonPath("$.name").value(NAME_OF_TEST_MVC_FACULTY))
                .andExpect(jsonPath("$.color").value(COLOR_OF_TEST_MVC_FACULTY));
    }

    @Test
    void testGetFacultyByColor() throws Exception {
        List<Faculty> expectedFaculties = new ArrayList<>(Arrays.asList(
                TEST_MVC_FACULTY,
                TEST_MVC_FACULTY2
        ));
        when(facultyRepository.findAll()).thenReturn(expectedFaculties);
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getByColor/" + COLOR_OF_TEST_MVC_FACULTY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Faculty> responseFaculties = objectMapper.readValue(
                jsonResponse
                ,new TypeReference<List<Faculty>>() {});
        assertThat(responseFaculties.equals(expectedFaculties));
    }

    @Test
    void testGetFacultyByNameOrColor() throws Exception {
        List<Faculty> expectedFaculties = new ArrayList<>(Arrays.asList(
                TEST_MVC_FACULTY,
                TEST_MVC_FACULTY2
        ));
        when(facultyRepository.findFacultiesByNameOrColorIgnoreCase(NAME_OF_TEST_MVC_FACULTY,COLOR_OF_TEST_MVC_FACULTY2))
                .thenReturn(expectedFaculties);
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getByNameOrColor?name=" + NAME_OF_TEST_MVC_FACULTY + "&color=" + COLOR_OF_TEST_MVC_FACULTY2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Faculty> responseFaculties = objectMapper.readValue(
                jsonResponse
                ,new TypeReference<List<Faculty>>() {});
        assertThat(responseFaculties.equals(expectedFaculties));
    }


}
