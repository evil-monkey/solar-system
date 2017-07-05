package com.solarsystem.wheaterpredictor.core.events;

import java.util.Collection;

public interface EventType {

	String getName();
	Collection<EventType> occurs(Integer day);

}
