package dev.nelit.server.services.referral;

import dev.nelit.server.repositories.user.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReferralServiceImpl implements ReferralService {

    private final UserRepository userRepository;

    public ReferralServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<String> generate() {
        return Mono.fromSupplier(() -> RandomStringUtils.randomAlphanumeric(12).toUpperCase())
            .flatMap(code -> userRepository.existsByReferralCode(code)
                .flatMap(exists -> exists ? generate() : Mono.just(code)));
    }
}
