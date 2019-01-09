package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    @OneToOne(mappedBy = "schedules")
    private Room room;

}
