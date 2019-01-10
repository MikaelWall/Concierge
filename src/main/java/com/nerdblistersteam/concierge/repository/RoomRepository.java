package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);
}
