package dev.nelit.server.services.admin.impl;

import dev.nelit.server.dto.admin.AdminResponseDTO;
import dev.nelit.server.entity.admin.Admin;
import dev.nelit.server.entity.admin.AdminPermission;
import dev.nelit.server.enums.AdminPermissions;
import dev.nelit.server.repositories.admin.AdminRepository;
import dev.nelit.server.services.admin.api.AdminPermissionService;
import dev.nelit.server.services.admin.api.AdminService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminPermissionService adminPermissionService;
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminPermissionService adminPermissionService, AdminRepository adminRepository) {
        this.adminPermissionService = adminPermissionService;
        this.adminRepository = adminRepository;
    }

    @Override
    public Mono<AdminResponseDTO> getAdminResponse(int userID) {
        return adminRepository.findByIdUser(userID)
            .flatMap(admin -> adminPermissionService.getPermissions(admin.getIdAdmin())
                .map(permissions -> new AdminResponseDTO(
                    admin.getIdAdmin(),
                    permissions.stream().map(AdminPermission::getPermission).collect(Collectors.toSet())
                )));
    }

    @Override
    public Mono<Void> upsertAdmin(int userID, Set<AdminPermissions> permissions) {
        return adminRepository.findByIdUser(userID)
            .flatMap(existingAdmin ->
                adminPermissionService.removePermissions(existingAdmin.getIdAdmin())
                    .then(Mono.defer(() -> adminPermissionService.addPermissions(existingAdmin.getIdAdmin(), permissions)))
                    .thenReturn(existingAdmin)
            )
            .switchIfEmpty(
                adminRepository.save(new Admin(userID))
                    .flatMap(admin ->
                        adminPermissionService.addPermissions(admin.getIdAdmin(), permissions)
                            .onErrorResume(DuplicateKeyException.class, e -> Mono.empty())
                            .thenReturn(admin)
                    )
            )
            .then();
    }

    @Override
    public Mono<Void> removeAdmin(int userID) {
        return adminRepository.deleteById(userID);
    }
}
