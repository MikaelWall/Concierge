package com.nerdblistersteam.concierge.service;

import com.nerdblistersteam.concierge.domain.Schedule;
import com.nerdblistersteam.concierge.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
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

    public void delete(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }
}
