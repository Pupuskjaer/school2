package ru.khasanov.hogwarts.school_web_application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.khasanov.hogwarts.school_web_application.controller.StudentController;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        logger.info("Was invoked method for adding student = {}", student);
        return studentRepository.save(student);
    }

    public Student find(long id) {
        logger.info("Was invoked method for finding student by id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student edit(Student student) {
        logger.info("Was invoked method for editing student");
        return studentRepository.save(student);
    }

    public void delete(long id) {
        logger.info("Was invoked method for deleting student by id ={}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for finding collection of students by age = {}", age);
        return studentRepository.findAll()
                .stream()
                .filter(e ->e.getAge()==age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getByAgeBetween(int min,int max) {
        logger.info("Was invoked method for finding students by age range [{},{}]",min,max );
        return studentRepository.findByAgeBetween(min, max);
    }

    public String gatStudentsFaculty (String name) {
        logger.info("Was invoked method for finding student's faculty by name = {}",name);
        return studentRepository.findStudentByName(name).getFaculty();
    }

    public Integer getAmountOfStudents() {
        logger.info("Was invoked method for getting amount of students");
        return studentRepository.getAmountOfStudent();
    }
    public Integer getAverageAgeOfStudents() {
        logger.info("Was invoked method for getting the average students age");
        return studentRepository.getAverageAgeOfStudents();
    }
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method to get last five students");
        return studentRepository.getLastFiveStudents();
    }
}
