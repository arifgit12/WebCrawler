package in.arifalimondal.auth.service;

import in.arifalimondal.auth.entity.User;
import in.arifalimondal.auth.repository.UserCredentialRepository;
import in.arifalimondal.auth.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public String saveUser(User credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateTokenWithAuthorities(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
