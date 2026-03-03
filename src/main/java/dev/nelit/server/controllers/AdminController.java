package dev.nelit.server.controllers;


import dev.nelit.server.dto.admin.AdminAddDTO;
import dev.nelit.server.services.admin.api.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> upsertAdmin(@RequestBody AdminAddDTO adminDTO) {
        return adminService.upsertAdmin(adminDTO.getIdUser(), adminDTO.getPermissions())
            .then(Mono.fromCallable(() -> ResponseEntity.ok(Map.of("status", true))));
    }
}
