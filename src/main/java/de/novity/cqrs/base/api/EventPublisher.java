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

import java.util.List;

/**
 * An event publisher is the central endpoint to publish events to all registered event subscribers on behalf of a
 * client in the role of a publisher. In CQRS this is usually a command handler publishing the domain events
 * created by the affected root aggregates.
 * </p>
 * You must have your infrastructure implement at least one event publisher. Usually you use some kind of messaging
 * system for this.
 */
public interface EventPublisher {
    /**
     * Registers a new event subscriber with this event publisher. After registration the event subscriber is notified
     * by all events published by this event publisher.
     *
     * @param subscriber An event subscriber that wants to receive published events.
     */
    void addSubscriber(EventSubscriber subscriber);

    /**
     * Removes a registered event subscriber from this publisher. After removing the event subscriber is no longer
     * notiefied by events published by this event publisher.
     * @param subscriber
     */
    void removeSubscriber(EventSubscriber subscriber);

    /**
     * Takes an event and publishes it to all registered event receivers.
     *
     * @param event The event to be published.
     * @throws Exception If the publishing of the event failed.
     */
    void publish(Event event) throws Exception;

    /**
     * Takes a list of events and publishes each event to all registered receivers.
     *
     * @param events The list of events to be published.
     * @throws Exception If the publishing of the events failed.
     */
    void publish(List<Event> events) throws Exception;
}
