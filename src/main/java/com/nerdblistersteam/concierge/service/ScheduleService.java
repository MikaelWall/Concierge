package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Schedule;
import com.nerdblistersteam.concierge.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleService {

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findByStart(LocalDateTime startTime) {
        return scheduleRepository.findByStart(startTime);
    }

    public List<Schedule> findByStop(LocalDateTime stopTime) {
        return scheduleRepository.findByStop(stopTime);
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
