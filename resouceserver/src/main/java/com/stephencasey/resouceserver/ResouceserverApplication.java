package com.stephencasey.resouceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SpringBootApplication
public class ResouceserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResouceserverApplication.class, args);
    }

}

@Service
class GreetingsService {

    @PreAuthorize("hasAuthority('SCOPE_message:read')")
    public Map<String, String> greet() {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("message", "Hello, " + jwt.getSubject());
    }
}

@Controller
@ResponseBody
class GreetingsController {

    private final GreetingsService greetingsService;

    public GreetingsController(GreetingsService greetingsService) {
        this.greetingsService = greetingsService;
    }

    @GetMapping("/")
    public Map<String, String> greet() {
        return greetingsService.greet();
    }
}