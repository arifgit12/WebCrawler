package in.arifalimondal.auth.service.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JweConfiguration {

    @Value("${security.jwt.encryptionSecret}")
    private String encryptionSecret;

}
