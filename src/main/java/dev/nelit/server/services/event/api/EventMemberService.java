package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventMemberDataDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.user.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EventMemberService {
    Mono<EventMemberDataDTO> getLastMemberData(int userID);

    Mono<List<EventMemberDataDTO>> getMembers(Event event);

    Mono<Void> signUp(Event event, User user, EventSignUpDTO eventSignUpDTO);

    Mono<Void> signOut(int eventID, int userID);
}
