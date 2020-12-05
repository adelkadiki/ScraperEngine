package com.scraper.engine.api.scraper;

import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.scraper.engine.api.entity.Event;
import com.scraper.engine.api.service.EventService;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EventOne implements EventConfig {

	@Autowired
	EventService service;
	
@Override	
public void config(EventService  service) {
	
	ArrayList<Event> eventsList = new ArrayList<>();
	
	try {
		Document page = Jsoup.connect("https://www.techmeme.com/events").get();
		
		Elements eventDate = page.select(".rhov div:nth-child(1)") ;
		Elements eventName = page.select(".rhov div:nth-child(2)");
		Elements eventLocation = page.select(".rhov div:nth-child(3)");
		Elements names = page.select(".rhov");
		String websiteName = page.title() ;
		
		
		
		
		websiteName = (String) websiteName.subSequence(18, websiteName.length());
		int size = names.size();
		
		
		for(int i=0; i<size; i++) {
			
			String date  = eventDate.get(i).text();
			String name  = eventName.get(i).text();
			String location  = eventLocation.get(i).text();
			String newDate="";
			
			 if(!name.subSequence(0,8).equals("Earnings") && !name.subSequence(0, 8).equals("CANCELED")) {   // removing non events and canceled ones 
				
				 if(date.length()>5 && date.charAt(5)=='-') {
					 newDate += date.substring(0, 5);
					 													// abstracting event starting date			
				 }else if(date.length()>5 && date.charAt(5)!='-') {             
				    newDate += date.substring(0, 6); 
				 
				 }
				 
				 
				 if(!newDate.equals("")) {                                 // avoid empty rows

					 DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				                .appendPattern("MMM d")
				                .parseDefaulting(ChronoField.YEAR, 2020)			 // parse into Date format
				                .toFormatter(Locale.US);

				        
				        LocalDate localDate = LocalDate.parse(newDate, formatter);
				       
				        if(location.isEmpty()) {
							 location="VIRTUAL";         // adding VIRSTUAL to virtual events which have no location mentioned  
						 }
						 
						 if(name.substring(0, 8).equals("VIRTUAL:")) {
							 name= (String) name.subSequence(9, name.length());									// cleaning 		
							 																					// and abstracting 	
						 }else if(name.length()>18 && name.subSequence(0, 18).equals("VIRTUAL, NEW DATE:")) {   // event name
							 name = (String) name.subSequence(19, name.length());
						 
						 }else if(name.subSequence(0, 9).equals("NEW DATE:")) {
							 name = (String) name.subSequence(10, name.length());
						 }
						 
						 if(service.findByName(name)==null) {                    // avoid duplicates 
							 
							 Event event = new Event();
							 
							 event.setDate(localDate);
							 event.setLocation(location);
							 event.setName(name);
					         event.setWebsite(websiteName);
							 
							 eventsList.add(event);
							
							 
						 	}
						 
				 		 }	 				 
				
			  		  }
		
				   }
					
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
             
  
	service.saveEvents(eventsList); 
}

	
}
