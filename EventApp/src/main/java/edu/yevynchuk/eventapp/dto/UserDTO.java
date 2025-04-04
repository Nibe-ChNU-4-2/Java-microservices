package edu.yevynchuk.eventapp.dto;

import edu.yevynchuk.eventapp.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "Ім'я користувача не може бути пустим")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @ValidEmail
    private String email;
}
