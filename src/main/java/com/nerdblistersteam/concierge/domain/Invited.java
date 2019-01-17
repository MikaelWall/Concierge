package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Invited {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Size(min = 8, max = 20)
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    private String addedByFullName;

    @NonNull
    @NotEmpty(message = "Du måste ange ett förnamn.")
    private String firstName;

    @NonNull
    @NotEmpty(message = "Du måste ange ett efternamn.")
    private String lastName;

    @NonNull
    private boolean isAdmin;

    private String activationCode;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
