package com.scraper.engine.api.controller;


import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.scraper.engine.api.scraper.EventOne;
import com.scraper.engine.api.scraper.EventTwo;
import com.scraper.engine.api.service.EventService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EventController {

	@Autowired
	EventService service;

	@Autowired
	EventOne techmeme;

	@Autowired
	EventTwo computeWorld;
	
	

	//Compute World website api ;
	@GetMapping("/computeworld")
	public CompletableFuture<ResponseEntity> saveComputeWorldWebsiteDate() {

		try {

			computeWorld.config(service);
			
			
			return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).build());

		} catch (Exception e) {
			
			return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
		}

	}
	

	// Techmeme webiste api
	@GetMapping("/techmeme")
	public CompletableFuture<ResponseEntity> saveTechmemeWebsiteDate() {

		try {

			techmeme.config(service);

			
			return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).build());

		} catch (Exception e) {

			
			return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
		}

	}

	// Get events
	@GetMapping("/getEvents")
	public CompletableFuture<ResponseEntity> getEvents() {

		return service.getEvents().thenApply(ResponseEntity::ok);

	}

}
