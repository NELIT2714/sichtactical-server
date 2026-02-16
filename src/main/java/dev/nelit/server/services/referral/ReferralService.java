package dev.nelit.server.services.referral;

import reactor.core.publisher.Mono;

public interface ReferralService {
    Mono<String> generate();
}
