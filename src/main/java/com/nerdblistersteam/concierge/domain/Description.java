package com.nerdblistersteam.concierge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    @JsonIgnore
    private Collection<Room> rooms;
}
