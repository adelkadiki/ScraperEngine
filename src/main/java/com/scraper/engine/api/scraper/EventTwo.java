package com.scraper.engine.api.scraper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scraper.engine.api.entity.Event;
import com.scraper.engine.api.service.EventService;


@Configuration
public class EventTwo implements EventConfig {

	
	@Autowired
	EventService service;
	
	@Override
	public void config(EventService service) {
		
		ArrayList<Event> eventList = new ArrayList<>();
		  
		
		try {
			Document page = Jsoup.connect("https://www.computerworld.com/article/3313417/tech-event-calendar-shows-conferences-and-it-expos-updated.html").get();
		
			
			Elements EventDate = page.select("#cwsearchabletable tr td:nth-child(3)");
			
			Elements EventName = page.select("#cwsearchabletable tr th a");
			
			Elements website = page.select(".masthead span");
		
			Elements Eventlocation = page.select("#cwsearchabletable tr td:nth-child(5)");

			int size = EventName.size();
			
			
			
			for(int i=0; i<size; i++) {
			
				Event event = new Event();
				
				String date  = EventDate.get(i).text();
				String name  = EventName.get(i).text();             // getting required data
				String location = Eventlocation.get(i).text();
				
				if(service.findByName(name.toString())==null) {    // avoid duplicates 
				
				
				
				event.setLocation(location);
				event.setName(name);
				event.setWebsite(website.text().toString());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d", Locale.US); 
				LocalDate newDate = LocalDate.parse(date, formatter);                               // parse into Date format     
				event.setDate(newDate);
			
				eventList.add(event);
			}
			
			}
		
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
			
		
			
			service.saveEvents(eventList);                                      // save to database
			
		
	}
}
