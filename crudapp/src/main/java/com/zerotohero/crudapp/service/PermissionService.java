package com.zerotohero.crudapp.service;

import com.zerotohero.crudapp.dto.request.PermissionRequest;
import com.zerotohero.crudapp.dto.response.PermissionResponse;
import com.zerotohero.crudapp.entity.Permission;
import com.zerotohero.crudapp.mapper.PermissionMapper;
import com.zerotohero.crudapp.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        Permission permission=permissionMapper.toPermission(request);
        return permissionMapper.toPermissionReponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll(){
        var permissions= permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionReponse).collect(Collectors.toList());
    }

    public void delete(String name){
        permissionRepository.deleteById(name);
    }
}
