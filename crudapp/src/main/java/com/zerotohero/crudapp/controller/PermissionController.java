package com.zerotohero.crudapp.controller;

import com.zerotohero.crudapp.dto.request.PermissionRequest;
import com.zerotohero.crudapp.dto.response.ApiResponse;
import com.zerotohero.crudapp.dto.response.PermissionResponse;
import com.zerotohero.crudapp.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> deletePermission(@PathVariable String name){
        permissionService.delete(name);
        return ApiResponse.<Void>builder()
                .message("Xóa thành công")
                .build();
    }
}
