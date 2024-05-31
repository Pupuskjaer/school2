package ru.khasanov.hogwarts.school_web_application.service;

import org.springframework.stereotype.Service;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty find(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty edit(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        facultyRepository.deleteById(id);
    }


    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(e ->e.getColor().equals(color))
                .collect(Collectors.toList());
    }
    public Collection<Faculty> getFacultyByNameOrColor(String name,String color) {
        return facultyRepository.findFacultiesByNameOrColorIgnoreCase(name,color);
    }

    public Collection<Student> getFacultysStudents(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name).getStudents();
    }
}
