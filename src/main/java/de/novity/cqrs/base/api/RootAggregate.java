/*
 * Copyright 2016 novity Software-Consulting
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.novity.cqrs.base.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the base class of all root aggregates.
 * <p/>
 * An root aggregate is a special case of an entity and therefore has an unique identity. A root aggregate spans an
 * object tree of other entities and value objects building a single unit, the aggregate. The root aggregate is build
 * in such a way that it takes responsibility for the invariants of the whole unit. In technical terms the root
 * aggregate is also responsible for maintaining the transactional border of the whole unit.
 * <p/>
 * If an executed command results in a state change of a root aggregate, the root aggregate creates the domain events,
 * as the observable result of the state change. Usually your command handlers are responsible to publish these events
 * to interested event receivers.
 * <p/>
 * Your root aggregate should extend this base class to get a functionality for managing the domain events created
 * by your root aggregate.
 *
 * @See RootAggregate for an example of the cooperation between comand hanlder and root aggregate.
 */
public abstract class RootAggregate {
    /**
     * The list of events representing state changes of this root aggregate since the last commit.
     */
    private final List<Event> events;

    protected RootAggregate() {
        this.events = new ArrayList<Event>();
    }

    /**
     * Add an event to the list of uncommited state changes of this root aggregate.
     *
     * @param event The domain event representing a change of state of this root aggregate.
     */
    protected void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Returns the list of uncommmited state changes of this root aggregate since the last commit.
     * <p/>
     * Usually this method is called by a command handler after the command handler has successfully called business
     * methods of affected root aggregates to get their resulting state changes in form of events.
     *
     * @return List of uncommited state changes.
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Commits the uncommited state changes of this root aggregate.
     * <p/>
     * Usually this method is called by a command handler after successfully publishing the events of affected root
     * aggregates to interested event subscribers.
     */
    public void commitEvents() {
        events.clear();
    }
}
