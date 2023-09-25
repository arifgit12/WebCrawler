package in.arifalimondal.auth.service.security;

import in.arifalimondal.auth.entity.RefreshToken;
import in.arifalimondal.auth.repository.RefreshTokenRepository;
import in.arifalimondal.auth.repository.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserCredentialRepository userInfoRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username){
        logger.info("Create Refresh token of user: {}", username);
        RefreshToken refreshToken = RefreshToken.builder()
                                        .userInfo(userInfoRepository.findByName(username).get())
                                        .token(UUID.randomUUID().toString())
                                        .expiryDate(Instant.now().plusMillis(600000))
                                        .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
