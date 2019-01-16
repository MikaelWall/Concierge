package com.nerdblistersteam.concierge.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Description {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String tag;

    @ManyToMany(mappedBy = "descriptions")
    private Collection<Room> rooms;
}
