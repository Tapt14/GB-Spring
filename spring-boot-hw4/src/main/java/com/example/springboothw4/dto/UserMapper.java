package com.example.springboothw4.dto;

import com.example.springboothw4.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (uses = { ProductMapper.class })
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "productDTO", target = "product")
    User toUser(UserDTO userDTO);

    @InheritInverseConfiguration
    UserDTO fromUser(User user);

    List<User> toUserList(List<UserDTO> userDTOs);

    List<UserDTO> fromUserList(List<User> categories);

}
