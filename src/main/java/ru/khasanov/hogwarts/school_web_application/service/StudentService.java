package ru.khasanov.hogwarts.school_web_application.service;

import org.springframework.stereotype.Service;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        return studentRepository.save(student);
    }

    public Student find(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student edit(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(e ->e.getAge()==age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getByAgeBetween(int min,int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public String gatStudentsFaculty (String name) {
        return studentRepository.findStudentByName(name).getFaculty();
    }

    public Integer getAmountOfStudents() {
        return studentRepository.getAmountOfStudent();
    }
    public Integer getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
