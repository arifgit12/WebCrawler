package in.arifalimondal.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        //service.validateToken(token);
        return "Token is valid";
    }
}
