package com.hha.demo.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

	private static Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);
	
	@Async
	@EventListener
	public void handle(AccessEvent event) {
		logger.info("This user tried to access :: {}", event);
	}
}
