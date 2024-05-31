package ru.khasanov.hogwarts.school_web_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khasanov.hogwarts.school_web_application.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    Student findStudentByName(String name);

}
