package ru.khasanov.hogwarts.school_web_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.khasanov.hogwarts.school_web_application.model.Avatar;
import ru.khasanov.hogwarts.school_web_application.model.Student;
import ru.khasanov.hogwarts.school_web_application.service.AvatarService;

import java.util.List;

@RestController
public class AvatarTController {
    private AvatarService avatarService;

    public AvatarTController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/getPage")
    public ResponseEntity<List<Avatar>> getAvatarListPage (@RequestParam("page") Integer pageNumber,@RequestParam("size") Integer pageSize) {

        List<Avatar> avatarPages = avatarService.findAllAvatars(pageNumber,pageSize);
        if (avatarPages.contains(null) || avatarPages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatarPages);
    }
}
