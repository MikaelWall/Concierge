package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Room;
import com.nerdblistersteam.concierge.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Optional<Room> findByName(String name) {
        return roomRepository.findByName(name);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}
