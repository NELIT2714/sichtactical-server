package dev.nelit.server.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.AdminPermissions;

import java.util.Set;

public class AdminResponseDTO {

    @JsonProperty("id_admin")
    private Integer idAdmin;

    @JsonProperty("permission")
    private Set<AdminPermissions> permissions;

    public AdminResponseDTO(Integer idAdmin, Set<AdminPermissions> permissions) {
        this.idAdmin = idAdmin;
        this.permissions = permissions;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Set<AdminPermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<AdminPermissions> permissions) {
        this.permissions = permissions;
    }
}
