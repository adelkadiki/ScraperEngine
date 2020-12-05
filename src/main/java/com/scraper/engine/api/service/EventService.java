package com.scraper.engine.api.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.scraper.engine.api.entity.Event;
import com.scraper.engine.api.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	EventRepository repository;
	
	
	
	
	// Saving events 
	@Async
	public CompletableFuture<List<Event>> saveEvents(List<Event> events){
		
		repository.saveAll(events);
		return CompletableFuture.completedFuture(events);
	}
	
	
	// Get events
	@Async
	public CompletableFuture<List<Event>> getEvents(){
		
		List<Event> events = repository.findAll();
		 return CompletableFuture.completedFuture(events);
	}
	
	
	// Find event by name to verify before saving in database
	public Event findByName(String name) {
					
		return repository.findByName(name);
	}
	
	
		
	
	// Get events method for testing repository.findAll() and not included in system functionality
	public List<Event> testGetEvents(){
		return repository.findAll();
	}
	 
	
	
}