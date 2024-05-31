package ru.khasanov.hogwarts.school_web_application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.khasanov.hogwarts.school_web_application.TestConstants.*;

@RestController
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPostAddingFaculty() {
        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.POST
                , new HttpEntity<>(FACULTY_1)
                , Faculty.class
        );
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testPutEditingFaculty() {
        ResponseEntity<Faculty> responseFaculty = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.POST
                , new HttpEntity<>(FACULTY_1)
                , Faculty.class
        );

        Faculty testFaculty = new Faculty(
                responseFaculty.getBody().getId(),
                FACULTY_1.getName(),
                FACULTY_1.getColor());
        assertThat(responseFaculty.getBody()).isNotNull().isEqualTo(testFaculty);
        testFaculty.setName("testFaculty");
        testFaculty.setColor("24");
        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.PUT
                , new HttpEntity<>(testFaculty)
                , Faculty.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testDeleteFaculty() {
        ResponseEntity<Faculty> responseFaculty = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.POST
                , new HttpEntity<>(FACULTY_1)
                , Faculty.class
        );
        Faculty testFaculty = new Faculty(
                responseFaculty.getBody().getId(),
                FACULTY_1.getName(),
                FACULTY_1.getColor());
        assertThat(responseFaculty.getBody()).isNotNull().isEqualTo(testFaculty);
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty" + testFaculty.getId()
                ,HttpMethod.DELETE
                ,null
                , Void.class
        );
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void testFindById() {
        long id = addingFaculty(FACULTY_2);

        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/find/" + id
                , HttpMethod.GET
                , null
                , Faculty.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    void testGetFacultyByColor() {
        addingFaculty(FACULTY_5);

        ResponseEntity<Collection<Faculty>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/getByColor/" + FACULTY_5.getColor()
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Collection<Faculty>>() {
                }
        );
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    void testGetByNameOrColor() {
        //добавление в базу данных объектов факультетов
        long idFaculty4 = addingFaculty(FACULTY_4);
        long idFaculty5 = addingFaculty(FACULTY_3);

        // запрос на получение коллекции факультетов по цвету
        ResponseEntity<Collection<Faculty>> responseOnlyColor = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/getByNameOrColor?color=" + FACULTY_4.getColor()
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Collection<Faculty>>() {
                }
        );
        assertThat(responseOnlyColor.getStatusCode().equals(HttpStatus.OK));
        assertThat(responseOnlyColor.getBody()).isNotNull();
        assertThat(responseOnlyColor.getBody().size()).isEqualTo(1);

        // запрос на получение коллекции факультетов по имени
        ResponseEntity<Collection<Faculty>> responseOnlyName = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/getByNameOrColor?name=" + FACULTY_3.getName()
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Collection<Faculty>>() {
                }
        );
        assertThat(responseOnlyName.getStatusCode().equals(HttpStatus.OK));
        assertThat(responseOnlyName.getBody()).isNotNull();
        assertThat(responseOnlyName.getBody().size()).isEqualTo(1);

        // запрос на получение коллекции факультетов по цвету и имени
        ResponseEntity<Collection<Faculty>> responseBothColorAndName = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/getByNameOrColor?color="
                        + FACULTY_4.getColor() + "&&name=" + FACULTY_3.getName()
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<Collection<Faculty>>() {
                }
        );
        assertThat(responseBothColorAndName.getStatusCode().equals(HttpStatus.OK));
        assertThat(responseBothColorAndName.getBody()).isNotNull();
        assertThat(responseBothColorAndName.getBody().size()).isEqualTo(2);
    }

    @Test
    void testGetFacultysStudent() {

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/getFacultysStudents/?name=" + FACULTY_4.getName(),
                HttpMethod.GET
                , null
                , new ParameterizedTypeReference<List<Student>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Student> students = response.getBody();
        assertNotNull(students);
        assertEquals(2, students.size());

    }

    private long addingFaculty(Faculty faculty) {
        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.POST
                , new HttpEntity<>(faculty)
                , Faculty.class
        );
        return response.getBody().getId();
    }

}
