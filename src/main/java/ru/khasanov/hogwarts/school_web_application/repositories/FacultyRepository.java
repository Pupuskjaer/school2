package ru.khasanov.hogwarts.school_web_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khasanov.hogwarts.school_web_application.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    Collection<Faculty> findFacultiesByNameOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByNameIgnoreCase(String name);

}
