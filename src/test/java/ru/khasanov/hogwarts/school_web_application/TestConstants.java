package ru.khasanov.hogwarts.school_web_application;

import ru.khasanov.hogwarts.school_web_application.model.Faculty;
import ru.khasanov.hogwarts.school_web_application.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestConstants {
    public final static Faculty FACULTY_1 = new Faculty(1L, "Juventus", "Black-White");
    public final static Faculty FACULTY_2 = new Faculty(2L, "F.C.Barcelona", "Blaugrana");
    public final static Faculty FACULTY_3 = new Faculty(3L, "Real Madrid", "White");
    public final static Faculty FACULTY_4 = new Faculty(4L, "Manchester United", "Red");
    public final static Faculty FACULTY_5 = new Faculty(5L, "Manchester City", "Blue");

    public final static Student STUDENT_1 = new Student(1L, "Antonio di Natale", 23, FACULTY_1);
    public final static Student STUDENT_2 = new Student(2L, "Alessandro de Rossi", 42, FACULTY_3);
    public final static Student STUDENT_3 = new Student(3L, "Hanz-Dieter Flick", 46, FACULTY_4);
    public final static Student STUDENT_4 = new Student(4L, "Gavi", 19, FACULTY_2);
    public final static Student STUDENT_5 = new Student(5L, "Pedri", 26, FACULTY_2);
    public final static Student STUDENT_6 = new Student(6L, "Frenkie De Jong", 26, FACULTY_5);
    public final static Collection<Student> STUDENTS_LIST = new ArrayList<>(List.of(
            STUDENT_1,STUDENT_2,STUDENT_3,STUDENT_4,STUDENT_5,STUDENT_6
    ));


    public final static List<Integer> AGE_LIST = new ArrayList<>(List.of(
            19,26,23,42,46
    ));
    public final static List<Integer> NULL_AGE_LIST = new ArrayList<>(List.of(
            27,35,45
    ));

    /*
    -------------------------------------------------------------------------------------------------------------------
                For MVC-tests
    -------------------------------------------------------------------------------------------------------------------
     */
    public  static final Student TEST_MVC_STUDENT = new Student(1L, "Antonio", 23);
    public  static final Long ID_OF_TEST_MVC_STUDENT = TEST_MVC_STUDENT.getId();
    public  static final String NAME_OF_TEST_MVC_STUDENT = TEST_MVC_STUDENT.getName();
    public  static final int AGE_OF_TEST_MVC_STUDENT = TEST_MVC_STUDENT.getAge();

    public  static final Student TEST_MVC_STUDENT2 = new Student(1L, "Ricardo", 29);
    public  static final Long ID_OF_TEST_MVC_STUDENT2 = TEST_MVC_STUDENT2.getId();
    public  static final String NAME_OF_TEST_MVC_STUDENT2 = TEST_MVC_STUDENT2.getName();
    public  static final int AGE_OF_TEST_MVC_STUDENT2 = TEST_MVC_STUDENT2.getAge();

    public  static final Faculty TEST_MVC_FACULTY = new Faculty(1L, "Brentford", "Red");
    public  static final Long ID_OF_TEST_MVC_FACULTY = TEST_MVC_FACULTY.getId();
    public  static final String NAME_OF_TEST_MVC_FACULTY = TEST_MVC_FACULTY.getName();
    public  static final String COLOR_OF_TEST_MVC_FACULTY = TEST_MVC_FACULTY.getColor();

    public  static final Faculty TEST_MVC_FACULTY2 = new Faculty(1L, "Tenerife", "Blue");
    public  static final Long ID_OF_TEST_MVC_FACULTY2 = TEST_MVC_FACULTY2.getId();
    public  static final String NAME_OF_TEST_MVC_FACULTY2 = TEST_MVC_FACULTY2.getName();
    public  static final String COLOR_OF_TEST_MVC_FACULTY2 = TEST_MVC_FACULTY2.getColor();
}
