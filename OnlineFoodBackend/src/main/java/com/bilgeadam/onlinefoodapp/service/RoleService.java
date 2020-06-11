package com.bilgeadam.onlinefoodapp.service;

import com.bilgeadam.onlinefoodapp.domain.Role;
import com.bilgeadam.onlinefoodapp.repo.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

}
