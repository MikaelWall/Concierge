package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private LocalDateTime start;

    @NonNull
    private LocalDateTime stop;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Room room;

}
