package dev.nelit.server.services.admin.api;

import dev.nelit.server.dto.admin.AdminResponseDTO;
import dev.nelit.server.enums.AdminPermissions;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface AdminService {

    Mono<AdminResponseDTO> getAdminResponse(int userID);

    Mono<List<AdminResponseDTO>> getAdmins();

    Mono<AdminResponseDTO> getAdmin(int adminID);

    Mono<Void> upsertAdmin(int userID, Set<AdminPermissions> permissions);

    Mono<Void> removeAdmin(int userID);
}
