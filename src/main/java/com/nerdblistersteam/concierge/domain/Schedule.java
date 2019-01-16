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

    @OneToOne
    private User user;

    public void addUser(User user) {
        this.user = user;
    }

    @OneToOne
    private Room room;

    public void addRoom(Room room) {
        this.room = room;
    }

}
