package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @NonNull
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

    public void addDescription(Description description) {
        descriptions.add(description);
    }

    public void addDescriptions(Set<Description> descriptions) {
        descriptions.forEach(this::addDescription);
    }
}
