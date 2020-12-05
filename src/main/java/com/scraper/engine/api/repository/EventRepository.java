package com.scraper.engine.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scraper.engine.api.entity.Event;



public interface EventRepository extends JpaRepository<Event, Integer> {

Event findByName(String name);	


	
}
