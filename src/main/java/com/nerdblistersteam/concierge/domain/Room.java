package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
