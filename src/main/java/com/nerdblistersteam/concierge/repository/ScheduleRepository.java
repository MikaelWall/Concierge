package com.nerdblistersteam.concierge.repository;

import com.nerdblistersteam.concierge.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
