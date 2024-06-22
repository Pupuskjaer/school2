package ru.khasanov.hogwarts.school_web_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khasanov.hogwarts.school_web_application.service.InfoService;

import java.util.stream.Stream;

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
    @GetMapping("something")
    public Long getSomething() {
        long time = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        time = System.currentTimeMillis() - time;

        return time;
    }

}
