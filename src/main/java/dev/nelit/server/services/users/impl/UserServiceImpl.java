package dev.nelit.server.services.users.impl;

import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.dto.user.UserResponseDTO;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.mappers.UserMapper;
import dev.nelit.server.repositories.user.UserRepository;
import dev.nelit.server.services.event.impl.EventMemberServiceImpl;
import dev.nelit.server.services.referral.ReferralServiceImpl;
import dev.nelit.server.services.users.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTelegramDataServiceImpl telegramDataService;
    private final ReferralServiceImpl referralService;
    private final EventMemberServiceImpl eventMemberService;

    private final TransactionalOperator tx;

    public UserServiceImpl(UserRepository userRepository, UserTelegramDataServiceImpl telegramDataService, ReferralServiceImpl referralService, EventMemberServiceImpl eventMemberService, TransactionalOperator tx) {
        this.userRepository = userRepository;
        this.telegramDataService = telegramDataService;
        this.referralService = referralService;
        this.eventMemberService = eventMemberService;
        this.tx = tx;
    }

    @Override
    public Mono<User> getUser(int userID) {
        return userRepository.findById(userID).switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "User not found")));
    }

    @Override
    public Mono<UserResponseDTO> upsertUser(UserUpsertDTO dto) {
        return tx.transactional(telegramDataService.upsertTelegramData(dto)
            .flatMap(tgData ->
                userRepository.getUserByIdUserTelegramData(tgData.getIdUserTelegramData())
                    .flatMap(user -> {
                        if (!Objects.equals(user.getSaveData(), dto.getSaveData()) && dto.getSaveData() != null) {
                            user.setSaveData(dto.getSaveData());
                            return userRepository.save(user);
                        }

                        return Mono.just(user);
                    })
                    .switchIfEmpty(referralService.generate().flatMap(code -> userRepository.save(new User(tgData.getIdUserTelegramData(), code))))
                    .map(user -> UserMapper.toResponseDTO(user, tgData))
            ));
    }

    public Mono<UserResponseDTO> getUserResponse(String telegramID) {
        return telegramDataService.getUserByTelegramID(telegramID)
            .flatMap(userTelegramData ->
                userRepository.getUserByIdUserTelegramData(userTelegramData.getIdUserTelegramData())
                    .flatMap(user -> {
                        UserResponseDTO response = UserMapper.toResponseDTO(user, userTelegramData);
                        return eventMemberService.getLastMemberData(user.getIdUser())
                            .doOnNext(response::setLastData)
                            .thenReturn(response);
                    })
            );
    }
}
