package org.msvdev.ee.shop.service;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.entity.Role;
import org.msvdev.ee.shop.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Получить роль обычного пользователя
     */
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow();
    }
}