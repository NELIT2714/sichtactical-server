package dev.nelit.server.services.referral;

import dev.nelit.server.repositories.user.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;

@Service
public class ReferralServiceImpl implements ReferralService {

    private final UserRepository userRepository;

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 12;

    public ReferralServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<String> generate() {
        return Mono.defer(() -> {
            String code = generateCode();
            return userRepository.existsByReferralCode(code)
                .flatMap(exists -> exists ? generate() : Mono.just(code));
        });
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHANUM.charAt(SECURE_RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}
