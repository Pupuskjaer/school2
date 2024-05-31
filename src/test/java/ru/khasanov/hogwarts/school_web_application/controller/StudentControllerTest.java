package ru.khasanov.hogwarts.school_web_application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.StudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.khasanov.hogwarts.school_web_application.TestConstants.*;

@RestController
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void testPostAddStudent() throws Exception {
        ResponseEntity<Student> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/student"
                , HttpMethod.POST
                , new HttpEntity<>(STUDENT_1)
                , Student.class);
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void testPutEditStudent() throws Exception{
        Student responseStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student"
                , STUDENT_1
                , Student.class);
        Student testStudent = new Student(
                responseStudent.getId(),
                STUDENT_1.getName(),
                STUDENT_1.getAge());
        assertThat(responseStudent).isNotNull().isEqualTo(testStudent);
        testStudent.setName("Marco Van Basten");
        testStudent.setAge(24);
        ResponseEntity<Student> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/student"
                , HttpMethod.PUT
                , new HttpEntity<>(testStudent)
                , Student.class);
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void testDeleteStudent() {
        Student responseStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student"
                , STUDENT_1
                , Student.class);
        Student testStudent = new Student(
                responseStudent.getId(),
                STUDENT_1.getName(),
                STUDENT_1.getAge());
        assertThat(responseStudent).isNotNull().isEqualTo(testStudent);
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + testStudent.getId()
                ,HttpMethod.DELETE
                ,null
                , Void.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testGetByID() throws Exception {
        long id = addingStudent(STUDENT_1);

        Student response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/find/" + id
                , Student.class);
        assertThat(response).isNotNull();
        assertEquals(STUDENT_1.getName(),response.getName());
        assertEquals(STUDENT_1.getAge(),response.getAge());

        deleteStudent(id);
    }

    @Test
    void testGetByAge() {
        for (Student student : STUDENTS_LIST) {
            addingStudent(student);
        }
//проверка возрастов, которые есть в листе
        for (Integer age : AGE_LIST) {
            ResponseEntity<List<Student>> response = this.restTemplate.exchange(
                    "http://localhost:" + port + "/student/getByAge/" + age
                    , HttpMethod.GET
                    , null
                    , new ParameterizedTypeReference<List<Student>>() {
                    }

            );
            assertThat(response.getStatusCode().equals(HttpStatus.OK));
            assertThat(response.getBody()).isNotNull();
        }
        // проверка возрастов, которых в листе нет
        for (Integer age : NULL_AGE_LIST) {
            ResponseEntity<List<Student>> response = this.restTemplate.exchange(
                    "http://localhost:" + port + "/student/getByAge/" + age
                    , HttpMethod.GET
                    , null
                    , new ParameterizedTypeReference<List<Student>>() {
                    }

            );
            assertThat(response.getStatusCode().equals(HttpStatus.OK));
            assertThat(response.getBody()).isNull();
        }


    }

    @Test
    void testGetByAgeBetween() {
        int min = 10;
        int max = 15;

        for (Student student : STUDENTS_LIST) {
            addingStudent(student);
        }
        for (int i = 0; i <= STUDENTS_LIST.size(); i = i + 5) {
            ResponseEntity<List<Student>> response = restTemplate.exchange(
                    "http://localhost:" + port + "/student/getByAgeBetween?min=" + min + "&&max=" + max
                    , HttpMethod.GET
                    , null
                    , new ParameterizedTypeReference<List<Student>>() {}
            );
            List<Student> students = response.getBody();
            assert students != null;
            for (Student student : students) {
                assertTrue(student.getAge() >= min && student.getAge() <= max);
            }
            min =+ i;
            max =+ i;
        }
    }

    @Test
    void testGetFacultyByStudent() {
        ResponseEntity<String> response = this. restTemplate.exchange(
                "http://localhost:" + port + "/student/getStudentsFaculty?name=" + STUDENT_5.getName()
                , HttpMethod.GET
                , null
                , String.class
        );
        assertThat(response.getBody()).isNotNull();
        ResponseEntity<String> secondResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/student/getStudentsFaculty?name=" + STUDENT_4.getName()
                , HttpMethod.GET
                , null
                , String.class
        );
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }


    private Long addingStudent(Student student) {
        ResponseEntity<Student> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/student"
                , HttpMethod.POST
                , new HttpEntity<>(student)
                , Student.class);
        return response.getBody().getId();
    }
    private void deleteStudent(long id) {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + id
                ,HttpMethod.DELETE
                ,null
                , Void.class
        );
    }
}
