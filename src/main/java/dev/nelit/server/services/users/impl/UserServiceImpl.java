package dev.nelit.server.services.users.impl;

import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.dto.user.UserResponseDTO;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.mappers.UserMapper;
import dev.nelit.server.repositories.user.UserRepository;
import dev.nelit.server.services.referral.ReferralServiceImpl;
import dev.nelit.server.services.users.api.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTelegramDataServiceImpl telegramDataService;
    private final ReferralServiceImpl referralService;

    public UserServiceImpl(UserRepository userRepository, UserTelegramDataServiceImpl telegramDataService, ReferralServiceImpl referralService) {
        this.userRepository = userRepository;
        this.telegramDataService = telegramDataService;
        this.referralService = referralService;
    }

    @Override
    public Mono<UserResponseDTO> upsertUser(UserUpsertDTO dto) {
        return telegramDataService.upsertTelegramData(dto)
            .flatMap(tgData ->
                userRepository.getUserByIdUserTelegramData(tgData.getIdUserTelegramData())
                    .switchIfEmpty(
                        referralService.generate().flatMap(code ->
                            userRepository.save(new User(tgData.getIdUserTelegramData(), code)))
                    )
                    .map(user -> UserMapper.toResponseDTO(user, tgData))
            );
    }

    @Override
    public Mono<UserResponseDTO> getUserResponse(String telegramID) {
        return telegramDataService.getUserByTelegramID(telegramID)
            .flatMap(userTelegramData ->
                userRepository.getUserByIdUserTelegramData(userTelegramData.getIdUserTelegramData())
                    .map(user -> UserMapper.toResponseDTO(user, userTelegramData)));
    }
}
