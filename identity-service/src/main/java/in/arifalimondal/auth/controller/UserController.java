package in.arifalimondal.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminResource() {
        logger.info("Test Authorized Admin User : " + getName());
        return String.format("This is a admin resource of User %s", getName());
    }

    @GetMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_ORDER')")
    public String userResource() {
        logger.info("Test Authorized Role User : " + getName());
        return String.format("This is a order resource of User %s", getName());
    }

    private String getName() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
