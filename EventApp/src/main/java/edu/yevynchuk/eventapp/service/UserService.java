package edu.yevynchuk.eventapp.service;

import edu.yevynchuk.eventapp.dto.UserCreateDTO;
import edu.yevynchuk.eventapp.dto.UserDTO;
import edu.yevynchuk.eventapp.dto.mapper.UserMapper;
import edu.yevynchuk.eventapp.exception.UserNotFoundException;
import edu.yevynchuk.eventapp.model.User;
import edu.yevynchuk.eventapp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return userMapper.toDTO(user);
    }

    public UserDTO addUser(UserCreateDTO userCreateDTO) {
        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        User user = User.builder()
                .username(userCreateDTO.getUsername())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword()) // обов’язково
                .build();

        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        if (!user.getEmail().equals(userDTO.getEmail())) {
            userRepository.findByEmail(userDTO.getEmail())
                    .ifPresent(existingUser -> {
                        throw new IllegalArgumentException("Email is already taken");
                    });
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        return userMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
