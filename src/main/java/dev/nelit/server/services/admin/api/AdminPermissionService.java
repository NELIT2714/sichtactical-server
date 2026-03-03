package dev.nelit.server.services.admin.api;

import dev.nelit.server.entity.admin.AdminPermission;
import dev.nelit.server.enums.AdminPermissions;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface AdminPermissionService {

    Mono<Void> addPermissions(int adminID, Set<AdminPermissions> permissions);

    Mono<Set<AdminPermission>> getPermissions(int adminID);

    Mono<Void> removePermissions(int adminID);

}
