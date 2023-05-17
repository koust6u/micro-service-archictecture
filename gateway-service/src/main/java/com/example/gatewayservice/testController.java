package com.example.gatewayservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class testController {

    private final Environment env;
    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Gateway Service:"
                + ", port(local.server.port)=" + env.getProperty("local.server.port"))
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", with token secret=" + env.getProperty("token.secret")
                + ", with token time=" + env.getProperty("token.expiration_time");
    }
}
