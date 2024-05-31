package ru.khasanov.hogwarts.school_web_application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.khasanov.hogwarts.school_web_application.model.Avatar;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
private String avatarsDir; // переменная, содержащая занчение из свойств


    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public Avatar add(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    private Avatar findAvatarByStudentId(Long studentId) {
        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar!=null) {
            return avatar;
        }
        return new Avatar(studentId);
    }



    public Avatar edit(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public void delete(long id) {
        avatarRepository.deleteById(id);
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        // получаем студента
        Student student = studentService.find(studentId);
        // создаем переменную, в которой будем хранить путь до директории,
        // в которой будет храниться файл с изображением
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        // Создание нужной нам директории для хранения данных.
        // Удаляем из нее файл, если он уже там присутствует
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is,1024);
                BufferedOutputStream bos = new BufferedOutputStream(os,1024);
        ) {
            bis.transferTo(bos);  // запуск процесса передачи данных
        }
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf("." )+1);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findById(id).get();
    }
}
