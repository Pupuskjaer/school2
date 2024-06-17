package ru.khasanov.hogwarts.school_web_application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    public Integer getPort() {
        return port;
    }

}
