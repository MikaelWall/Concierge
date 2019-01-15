package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAll();

    List<Schedule> findByStart(LocalDateTime startTime);

    List<Schedule> findByStop(LocalDateTime stopTime);
}
