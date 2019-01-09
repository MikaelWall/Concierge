package com.nerdblistersteam.concierge.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
}
