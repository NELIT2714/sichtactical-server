package dev.nelit.server.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.AdminPermissions;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class AdminAddDTO {

    @NotNull
    @JsonProperty("id_user")
    private Integer idUser;

    @NotEmpty
    @JsonProperty("permissions")
    private Set<AdminPermissions> permissions;

    public Integer getIdUser() {
        return idUser;
    }

    public Set<AdminPermissions> getPermissions() {
        return permissions;
    }
}
