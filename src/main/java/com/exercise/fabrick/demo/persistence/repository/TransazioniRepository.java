package com.exercise.fabrick.demo.persistence.repository;

import com.exercise.fabrick.demo.persistence.entity.Transazioni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioniRepository extends JpaRepository<Transazioni, Long> {
}