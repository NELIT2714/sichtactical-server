package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.dto.admin.AdminResponseDTO;
import dev.nelit.server.dto.event.EventMemberDataDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UserResponseDTO {

    @JsonProperty("id_user")
    private Integer idUser;

    @JsonProperty("id_user_telegram_data")
    private Integer idUserTelegramData;

    @Size(max = 20)
    @JsonProperty("call_sign")
    private String callSign;

    @Min(0)
    @JsonProperty("xp_total")
    private Integer xpTotal;

    @DecimalMin(value = "0.0")
    @JsonProperty("balance")
    private BigDecimal balance;

    @Size(min = 12, max = 12)
    @JsonProperty("referral_code")
    private String referralCode;

    @JsonProperty("telegram_data")
    private UserTelegramDataResponseDTO telegramData;

    @NotNull
    @JsonProperty("save_data")
    private Boolean saveData;

    @JsonProperty("last_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EventMemberDataDTO lastData;

    @JsonProperty("admin_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdminResponseDTO adminData;

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

    public AdminResponseDTO getAdminData() {
        return adminData;
    }

    public void setAdminData(AdminResponseDTO adminData) {
        this.adminData = adminData;
    }
}
