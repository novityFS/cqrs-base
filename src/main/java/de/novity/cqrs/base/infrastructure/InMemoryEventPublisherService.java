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

package de.novity.cqrs.base.infrastructure;

import de.novity.cqrs.base.api.Event;
import de.novity.cqrs.base.api.EventPublisher;
import de.novity.cqrs.base.api.EventPublisherService;
import de.novity.cqrs.base.api.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * An in memory implementation of an event publisher that can be used in standalone applications. The registered
 * event subscribers are backed by a simple list of subscribers. The <code>addSubscriber</code> and
 * <code>removeSubscriber</code> methods are synchronized to support multi threaded applications.
 * <p/>
 * The events to be published by the <code>publish</code> methods are backed by a blocking queue and a spawned
 * thread publishing the queued events for the same reason.
 */
public class InMemoryEventPublisherService implements EventPublisher, EventPublisherService {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryEventPublisherService.class);

    private final List<EventSubscriber> subscribers;
    private final BlockingQueue<Event> eventQueue;

    private AsyncPublisher asyncPublisher;

    public InMemoryEventPublisherService() {
        this.subscribers = new ArrayList<EventSubscriber>();
        this.eventQueue = new ArrayBlockingQueue<Event>(1024);
        this.asyncPublisher = null;

        logger.info("InMemoryPublisher created");
    }

    public void addSubscriber(EventSubscriber subscriber) {
        synchronized (subscribers) {
            subscribers.add(subscriber);
            logger.debug("Added subscriber " + subscriber);
        }
    }

    public void removeSubscriber(EventSubscriber subscriber) {
        synchronized (subscribers) {
            subscribers.remove(subscriber);
            logger.debug("Removed subscriber " + subscriber);
        }
    }

    public void start() {
        if (asyncPublisher != null) {
            throw new IllegalStateException("Publisher is already started");
        }

        asyncPublisher = new AsyncPublisher();
        asyncPublisher.start();
        logger.info("Publisher started");
    }

    public void stop() {
        if (asyncPublisher == null) {
            throw new IllegalStateException("Publisher is not started");
        }

        try {
            asyncPublisher.interrupt();
            asyncPublisher.join(1000);
            logger.info("Publisher stopped");
        } catch (InterruptedException e) {
           logger.error("Failed to stop publisher", e);
        } finally {
            asyncPublisher = null;
        }
    }

    public boolean isRunning() {
        return asyncPublisher != null;
    }

    public void publish(Event event) throws Exception {
        eventQueue.put(event);
    }

    public void publish(List<Event> events) throws Exception {
        for (Event event : events) {
            publish(event);
        }
    }

    private class AsyncPublisher extends Thread {
        public AsyncPublisher() {
            setName("publisher");
        }

        @Override
        public void run() {
            do {
                try {
                    Event event = eventQueue.take();

                    synchronized (subscribers) {
                        for (EventSubscriber subscriber : subscribers) {
                            subscriber.onEvent(event);
                        }
                    }
                } catch (InterruptedException e) {
                    interrupt();
                }
            } while (!isInterrupted());
        }
    }
}
