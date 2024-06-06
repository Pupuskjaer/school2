package ru.khasanov.hogwarts.school_web_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student Student) {
        return ResponseEntity.ok(studentService.add(Student));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student foundStudent = studentService.find(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student Student) {
        Student foundStudent = studentService.edit(Student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getByAge/{age}")
    public ResponseEntity<Collection<Student>> students(@PathVariable int age) {
        Collection<Student> students = studentService.getStudentsByAge(age);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("getByAgeBetween")
    public ResponseEntity<Collection<Student>> getByAgeBetween(
            @RequestParam int min
            , @RequestParam int max) {
        if (min < 0 || max < 0) {
            ResponseEntity.badRequest().build();
        }
        Collection<Student> foundStudent = studentService.getByAgeBetween(min, max);
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("getStudentsFaculty")
    public ResponseEntity<String> getStudentsFaculty(@RequestParam String name) {
        if (!name.isEmpty() && !name.isBlank()) {
            return ResponseEntity.ok(studentService.gatStudentsFaculty(name));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAmountOfStudents")
    public int getAMountOfStudents() {
        return studentService.getAmountOfStudents();
    }

    @GetMapping("/getAverageStudentAge")
    public int getAverageStudentAge() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/getLastFiveStudents")
    public List<Student> getLastFiveStudents(){
        return studentService.getLastFiveStudents();
    }
}
