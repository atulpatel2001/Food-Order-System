package com.food.app.user.mapper;

import com.food.app.user.dto.RoleDto;
import com.food.app.user.model.Role;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {


    public  static Role mapToRole(RoleDto roleDto,Role role){

        role.setName(roleDto.getName());
        return role;
    }


    public static RoleDto mapToRoleDto(Role role,RoleDto roleDto){

        roleDto.setName(role.getName());
        return roleDto;
    }

    public static List<RoleDto> mapListRoleDto(List<RoleRepresentation> roleRepresentationList){
        List<RoleDto> roles =new ArrayList<>();
        if(!roleRepresentationList.isEmpty()){
            roleRepresentationList.forEach(roleRepo->roles.add(mapRole(roleRepo)));
        }
        return roles;
    }

    public static RoleDto mapRole(RoleRepresentation roleRepo) {
        RoleDto role=new RoleDto();
        role.setName(roleRepo.getName());
        return role;
    }

    public static RoleRepresentation mapToRepresentation(RoleDto role){
        RoleRepresentation roleRepresentation=new RoleRepresentation();
        roleRepresentation.setName(role.getName());
        return roleRepresentation;
    }
}
