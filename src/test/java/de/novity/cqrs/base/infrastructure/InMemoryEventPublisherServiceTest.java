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
import de.novity.cqrs.base.api.EventSubscriber;
import mockit.Mocked;
import mockit.Verifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InMemoryEventPublisherServiceTest {
    private InMemoryEventPublisherService service;

    @BeforeMethod
    public void setUp() throws Exception {
        service = new InMemoryEventPublisherService();
    }

    @AfterMethod
    public void tearDown() throws Exception{
        if (service.isRunning()) {
            service.stop();
        }
    }

    @Test
    public void StartingEventPublisherServiceStartsANewThread() throws Exception {
        final int baseThreadCount = Thread.activeCount();
        service.start();
        assertEquals(Thread.activeCount(), baseThreadCount + 1);
    }

    @Test
    public void StartedEventPublisherServiceReportsThatItselfIsRunning() throws Exception {
        service.start();
        assertTrue(service.isRunning());
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void EventPublisherServiceCannotBeStartedTwice() throws Exception {
        service.start();
        service.start();
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void NewEventPublisherServiceCannotBeStopped() throws Exception {
        service.stop();
    }

    @Test()
    public void NewEventPublisherServiceReportsThatItselfIsNotRunning() throws Exception {
        assertFalse(service.isRunning());
    }

    @Test()
    public void StoppedEventPublisherServiceReportsThatItselfIsNotRunning() throws Exception {
        service.start();
        service.stop();
        assertFalse(service.isRunning());
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void RunningEventPublisherServiceCannotBeStoppedTwice() throws Exception {
        service.start();
        service.stop();
        service.stop();
    }

    @Test
    public void WhenEventIsPublishedViaEventPublisherTheEventSubscriberReceivesThisEvent(final @Mocked EventSubscriber subscriber, final @Mocked Event event) throws Exception {
        EventPublisher publisher = service.getPublisher();
        publisher.addSubscriber(subscriber);
        service.start();

        publisher.publish(event);

        new Verifications() {{
            subscriber.onEvent(event);
        }};
    }
}