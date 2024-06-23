package ru.khasanov.hogwarts.school_web_application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.khasanov.hogwarts.school_web_application.controller.StudentController;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.StudentRepository;

import java.util.*;
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

    public Collection<String> getStudentsWithNameStartsWithA() {
        logger.info("Was invoked method for finding students who's name starts with A");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(e -> e.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfStudentsByStream() {
        logger.info("Was invoked method for getting average students age by stream");
        return studentRepository.findAll()
                .stream().mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    // вывод имен студентов в консоль тремя потоками.
    // Дз 4.6(параллельные потоки), задание 1
    public void printStudentNames() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());
        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });
        thread2.start();

        thread1.interrupt();
        thread2.interrupt();

    }
    // вывод имен студентов в консоль тремя потоками,
    // два из которых выполняются с помощью отдельного синхронизированного метода.
    // Дз 4.6(параллельные потоки), задание 2
    public void printStudentNamesSynchronously() {
        System.out.println(studentRepository.findById(13L).get().getName());
        System.out.println(studentRepository.findById(14L).get().getName());
        Thread thread1 = new Thread(() -> {
            outputStudentNamesSynchronously(15);
            outputStudentNamesSynchronously(16);
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            outputStudentNamesSynchronously(17);
            outputStudentNamesSynchronously(18);

        });
        thread2.start();

        thread1.interrupt();
        thread2.interrupt();
    }
    private synchronized void  outputStudentNamesSynchronously(long id) {
        Student student = studentRepository.findById(id).orElse(null);
        System.out.println(student.getName());
    }
}
