/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.dm.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.dm.context.Event;

/* in real life, this event might contain a service reference and service instance
 * or something similar
 */
public class EventImpl implements Event {
	private final int m_id;
	private final Object m_event;    // the actual event object (a Service, a Bundle, a Configuration, etc ...)
	private final static Dictionary m_emptyProperties = new Hashtable();

	public EventImpl() {
		this(1);
	}
	/** By constructing events with different IDs, we can simulate different unique instances. */
	public EventImpl(int id) {
		this (id, null);
	}
	
	public EventImpl(int id, Object event) {
	    m_id = id;
	    m_event = event;
	}
	

	@Override
	public int hashCode() {
		return m_id;
	}

	@Override
	public boolean equals(Object obj) {
		// an instanceof check here is not "strong" enough with subclasses overriding the
		// equals: we need to be sure that a.equals(b) == b.equals(a) at all times
		if (obj != null && obj.getClass().equals(EventImpl.class)) {
			return ((EventImpl) obj).m_id == m_id;
		}
		return false;
	}
	
    @Override
    public int compareTo(Object o) {
        EventImpl a = this, b = (EventImpl) o;
        if (a.m_id < b.m_id) {
            return -1;
        } else if (a.m_id == b.m_id){
            return 0;
        } else {
            return 1;
        }
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public Object getEvent() {
        return m_event;
    }
    
    @Override
    public Dictionary getProperties() {
        return m_emptyProperties;
    }
}
