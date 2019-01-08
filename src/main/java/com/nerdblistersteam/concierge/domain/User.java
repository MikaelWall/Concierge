package com.nerdblistersteam.concierge.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;

//    @Transient
    @NotEmpty(message = "Please enter Password Confirmation")
    private String confirmPassword;
}
