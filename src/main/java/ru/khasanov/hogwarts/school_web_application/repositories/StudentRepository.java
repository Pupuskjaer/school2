package ru.khasanov.hogwarts.school_web_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.khasanov.hogwarts.school_web_application.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    Student findStudentByName(String name);

    // запрос для получения всех студентов из таблицы student
    @Query(value = "select count(*) from student", nativeQuery = true)
    int getAmountOfStudent();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    int getAverageAgeOfStudents();

    @Query(value = "select * from student order by id desc limit 4", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
