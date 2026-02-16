package dev.nelit.server.mappers;

import dev.nelit.server.dto.user.UserResponseDTO;
import dev.nelit.server.dto.user.UserTelegramDataResponseDTO;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.entity.user.UserTelegramData;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponseDTO toResponseDTO(User user, UserTelegramData tg) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setIdUser(user.getIdUser());
        dto.setIdUserTelegramData(tg.getIdUserTelegramData());
        dto.setCallSign(user.getCallSign());
        dto.setXpTotal(user.getXpTotal());
        dto.setBalance(user.getBalance());
        dto.setReferralCode(user.getReferralCode());
        dto.setTelegramData(toTelegramDTO(tg));
        return dto;
    }

    private static UserTelegramDataResponseDTO toTelegramDTO(UserTelegramData tg) {
        UserTelegramDataResponseDTO dto = new UserTelegramDataResponseDTO();
        dto.setIdUserTelegramData(tg.getIdUserTelegramData());
        dto.setTelegramId(tg.getTelegramId());
        dto.setFirstName(tg.getFirstName());
        dto.setLastName(tg.getLastName());
        dto.setUsername(tg.getUsername());
        dto.setPhoneNumber(tg.getPhoneNumber());
        dto.setLanguageCode(tg.getLanguageCode());
        dto.setIsPremium(tg.getIsPremium());
        return dto;
    }
}
