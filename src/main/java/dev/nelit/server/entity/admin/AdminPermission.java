package dev.nelit.server.entity.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.AdminPermissions;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_admins_permissions")
public class AdminPermission {

    @Id
    @JsonProperty("id_admin_permission")
    @Column("id_admin_permission")
    private Integer idAdminPermission;

    @JsonProperty("id_admin")
    @Column("id_admin")
    private Integer idAdmin;

    @Column("permission")
    @JsonProperty("permission")
    private AdminPermissions permission;

    public AdminPermission(Integer idAdmin, AdminPermissions permission) {
        this.idAdmin = idAdmin;
        this.permission = permission;
    }

    public Integer getIdAdminPermission() {
        return idAdminPermission;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public AdminPermissions getPermission() {
        return permission;
    }

    public void setPermission(AdminPermissions permission) {
        this.permission = permission;
    }
}
