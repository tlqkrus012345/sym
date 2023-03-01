package com.sym.sympathy.repository;

import com.sym.sympathy.domain.PsychologicalSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SympathyRepository extends JpaRepository<PsychologicalSurvey, Long> {
}
