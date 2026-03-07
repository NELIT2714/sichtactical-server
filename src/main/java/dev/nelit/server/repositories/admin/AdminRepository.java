package dev.nelit.server.repositories.admin;

import dev.nelit.server.dto.admin.AdminResponseDTO;
import dev.nelit.server.entity.admin.Admin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdminRepository extends ReactiveCrudRepository<Admin, Integer> {
    Mono<Admin> findByIdUser(Integer idUser);

    Mono<Admin> findByIdAdmin(Integer idAdmin);
}
