package edu.yevynchuk.eventapp.dto.mapper;

import edu.yevynchuk.eventapp.dto.UserDTO;
import edu.yevynchuk.eventapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }
}
