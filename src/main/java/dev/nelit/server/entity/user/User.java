package dev.nelit.server.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "tbl_users")
public class User {

    @Id
    @JsonProperty("id_user")
    @Column("id_user")
    private Integer idUser;

    @JsonProperty("id_user_telegram_data")
    @Column("id_user_telegram_data")
    private Integer idUserTelegramData;

    @JsonProperty("call_sign")
    @Column("call_sign")
    private String callSign;

    @JsonProperty("xp_total")
    @Column("xp_total")
    private Integer xpTotal;

    @JsonProperty("balance")
    @Column("balance")
    private BigDecimal balance;

    @JsonProperty("referral_code")
    @Column("referral_code")
    private String referralCode;

    public User(Integer idUserTelegramData, String referralCode) {
        this.idUserTelegramData = idUserTelegramData;
        this.referralCode = referralCode;
    }

    public Integer getIdUser() {
        return idUser;
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
}
