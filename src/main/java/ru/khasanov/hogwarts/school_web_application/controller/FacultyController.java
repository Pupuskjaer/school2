package ru.khasanov.hogwarts.school_web_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.add(faculty));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id) {
        Faculty foundFaculty = facultyService.find(id);
        if (foundFaculty==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        Faculty foundFaculty = facultyService.edit(faculty);
        if (foundFaculty==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getByColor/{color}")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@PathVariable String color) {
        Collection<Faculty> faculties = facultyService.getFacultyByColor(color);
        if (faculties.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }
    @GetMapping("getByNameOrColor")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam(required = false) String name
            ,@RequestParam(required = false) String color) {
        Collection<Faculty> faculties = facultyService.getFacultyByNameOrColor(name, color);
        if (!faculties.isEmpty()) {
            return ResponseEntity.ok(faculties);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("getFacultysStudents")
    public ResponseEntity<Collection<Student>> getFacultysStudents(@RequestParam String name) {
        if (!name.isEmpty()) {
            return ResponseEntity.ok(facultyService.getFacultysStudents(name));
        }
        return ResponseEntity.notFound().build();
    }
}
