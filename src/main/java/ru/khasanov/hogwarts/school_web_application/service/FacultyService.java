package ru.khasanov.hogwarts.school_web_application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for adding faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty find(long id) {
        logger.info("Was invoked method for finding faculty by id = {}",id);
        return facultyRepository.findById(id).get();
    }

    public Faculty edit(Faculty faculty) {
        logger.info("Was invoked method for editing faculty");
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        logger.info("Was invoked method for deleting faculty");
        facultyRepository.deleteById(id);
    }


    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for finding faculty by color = {}",color);
        return facultyRepository.findAll()
                .stream()
                .filter(e ->e.getColor().equals(color))
                .collect(Collectors.toList());
    }
    public Collection<Faculty> getFacultyByNameOrColor(String name,String color) {
        logger.info("Was invoked method for finding faculty by name or color");
        return facultyRepository.findFacultiesByNameOrColorIgnoreCase(name,color);
    }

    public Collection<Student> getFacultysStudents(String name) {
        logger.info("Was invoked method for finding all faculty's students");
        return facultyRepository.findFacultyByNameIgnoreCase(name).getStudents();
    }
}
