package dev.nelit.server.entity.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_admins")
public class Admin {

    @Id
    @JsonProperty("id_admin")
    @Column("id_admin")
    private Integer idAdmin;

    @JsonProperty("id_user")
    @Column("id_user")
    private Integer idUser;

    public Admin(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
