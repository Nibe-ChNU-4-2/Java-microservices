// EventUserDTO.java
package edu.yevynchuk.eventapp.dto;

import edu.yevynchuk.eventapp.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventUserDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long eventId;

    @NotNull
    private Role role;
}
