package dev.nelit.server.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.AdminPermissions;

import java.util.Set;

public class AdminAddDTO {

    @JsonProperty("id_user")
    private Integer idUser;

    @JsonProperty("permissions")
    private Set<AdminPermissions> permissions;

    public Integer getIdUser() {
        return idUser;
    }

    public Set<AdminPermissions> getPermissions() {
        return permissions;
    }
}
