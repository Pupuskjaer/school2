package ru.khasanov.hogwarts.school_web_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khasanov.hogwarts.school_web_application.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Avatar findByStudentId(Long studentId);

}
