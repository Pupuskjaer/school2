package ru.khasanov.hogwarts.school_web_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khasanov.hogwarts.school_web_application.service.InfoService;

@RestController
@RequestMapping("/port")
public class infoController {

    public final InfoService infoService;

    public infoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public Integer getPort() {
        return infoService.getPort();
    }

}
