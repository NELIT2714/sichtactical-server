package dev.nelit.server.repositories.admin;

import dev.nelit.server.entity.admin.AdminPermission;
import dev.nelit.server.enums.AdminPermissions;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface AdminPermissionRepository extends ReactiveCrudRepository<AdminPermission, Integer> {
    Mono<Void> deleteByIdAdminAndPermission(Integer idAdmin, AdminPermissions permission);

    Flux<AdminPermission> findAllByIdAdmin(Integer idAdmin);

    Mono<Void> deleteAllByIdAdmin(Integer idAdmin);


}
