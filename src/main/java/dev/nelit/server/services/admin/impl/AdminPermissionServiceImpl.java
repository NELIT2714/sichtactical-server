package dev.nelit.server.services.admin.impl;

import dev.nelit.server.entity.admin.AdminPermission;
import dev.nelit.server.enums.AdminPermissions;
import dev.nelit.server.repositories.admin.AdminPermissionRepository;
import dev.nelit.server.services.admin.api.AdminPermissionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {

    private final AdminPermissionRepository adminPermissionRepository;
    private final TransactionalOperator tx;

    public AdminPermissionServiceImpl(AdminPermissionRepository adminPermissionRepository, TransactionalOperator tx) {
        this.adminPermissionRepository = adminPermissionRepository;
        this.tx = tx;
    }

    @Override
    public Mono<Void> addPermissions(int adminID, Set<AdminPermissions> permissions) {
        return tx.transactional(
            Flux.fromIterable(permissions)
                .flatMap(permission -> adminPermissionRepository.save(new AdminPermission(adminID, permission))
                    .onErrorResume(DuplicateKeyException.class, e -> Mono.empty()))
                .then()
        );
    }

    @Override
    public Mono<Set<AdminPermission>> getPermissions(int adminID) {
        return adminPermissionRepository.findAllByIdAdmin(adminID).collect(Collectors.toSet());
    }

    @Override
    public Mono<Void> removePermissions(int adminID) {
        return tx.transactional(adminPermissionRepository.deleteAllByIdAdmin(adminID));
    }
}
