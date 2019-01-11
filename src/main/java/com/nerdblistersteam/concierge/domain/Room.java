package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

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

    @NotEmpty
    private int seatNum;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Schedule schedule;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rooms_descriptions",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "description_id", referencedColumnName = "id")
    )
    private List<Description> descriptions = new ArrayList<>();
}
