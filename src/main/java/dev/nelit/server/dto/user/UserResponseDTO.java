package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.dto.event.EventMemberDataDTO;

import java.math.BigDecimal;

public class UserResponseDTO {

    @JsonProperty("id_user")
    private Integer idUser;

    @JsonProperty("id_user_telegram_data")
    private Integer idUserTelegramData;

    @JsonProperty("call_sign")
    private String callSign;

    @JsonProperty("xp_total")
    private Integer xpTotal;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("referral_code")
    private String referralCode;

    @JsonProperty("telegram_data")
    private UserTelegramDataResponseDTO telegramData;

    @JsonProperty("save_data")
    private Boolean saveData;

    @JsonProperty("last_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EventMemberDataDTO lastData;

    public UserResponseDTO() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUserTelegramData() {
        return idUserTelegramData;
    }

    public void setIdUserTelegramData(Integer idUserTelegramData) {
        this.idUserTelegramData = idUserTelegramData;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public Integer getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(Integer xpTotal) {
        this.xpTotal = xpTotal;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public UserTelegramDataResponseDTO getTelegramData() {
        return telegramData;
    }

    public void setTelegramData(UserTelegramDataResponseDTO telegramData) {
        this.telegramData = telegramData;
    }

    public Boolean getSaveData() {
        return saveData;
    }

    public void setSaveData(Boolean saveData) {
        this.saveData = saveData;
    }

    public EventMemberDataDTO getLastData() {
        return lastData;
    }

    public void setLastData(EventMemberDataDTO lastData) {
        this.lastData = lastData;
    }
}
