package com.supamenu.www.repositories;

import com.supamenu.www.models.AbuseReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAbuseReportRepository extends JpaRepository<AbuseReport , UUID> {
}
