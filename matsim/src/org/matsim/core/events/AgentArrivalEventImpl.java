/* *********************************************************************** *
 * project: org.matsim.*
 * AgentArrivalEvent.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007, 2008 by the members listed in the COPYING,  *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.events;

import org.matsim.api.basic.v01.Id;
import org.matsim.api.basic.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.AgentArrivalEvent;

public class AgentArrivalEventImpl extends AgentEventImpl implements AgentArrivalEvent {

	public static final String EVENT_TYPE = "arrival";

	public AgentArrivalEventImpl(final double time, final Person agent, final Link link, final Leg leg) {
		super(time, agent, link, leg);
	}

	public AgentArrivalEventImpl(final double time, final Id agentId, final Id linkId, final TransportMode legMode) {
		super(time, agentId, linkId, legMode);
	}

	@Override
	public String getEventType() {
		return EVENT_TYPE;
	}

}
