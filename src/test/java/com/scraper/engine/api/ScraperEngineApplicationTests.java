package com.scraper.engine.api;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;




import com.scraper.engine.api.entity.Event;
import com.scraper.engine.api.repository.EventRepository;
import com.scraper.engine.api.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ScraperEngineApplicationTests {
	

	@Autowired
	EventService service;
	
	@MockBean
	EventRepository repositoy;
	
	@Test
	public void findAllEvents() throws Exception {
		
		when(repositoy.findAll()).
		thenReturn(Stream.of(new Event("name1", "website1", LocalDate.now(), "location1"), 
				new Event("name2", "website2", LocalDate.now(), "location2"), 
				new Event("name3", "website3", LocalDate.now(), "location3"))
				.collect(Collectors.toList()));
		
		assertEquals(3, service.testGetEvents().size());	
	}
	
	
	@Test
	public void getEventByName() {
		
		String name = "eventName";
		
		when(repositoy.findByName(name)).
		thenReturn(new Event("eventName", "website", LocalDate.now(), "location"));
		assertEquals("eventName" , service.findByName(name).getName());
	}
	
	
	@Test
	public void saveEvent() {
		Event event= new Event("name1", "website1", LocalDate.now(), "location1");
		when(repositoy.save(event)).thenReturn(event);
	}

}
