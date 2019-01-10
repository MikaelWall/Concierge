package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.repository.RoomRepository;

import java.util.Optional;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findByName(String name) {
        return roomRepository.findByName(name);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
