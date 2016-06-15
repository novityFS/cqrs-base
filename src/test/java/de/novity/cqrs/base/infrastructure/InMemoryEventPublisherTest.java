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
import de.novity.cqrs.base.api.EventSubscriber;
import mockit.Mocked;
import mockit.Verifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InMemoryEventPublisherTest {
    private InMemoryEventPublisher publisher;

    @BeforeMethod
    public void setUp() throws Exception {
        publisher = new InMemoryEventPublisher();
    }

    @AfterMethod
    public void tearDown() throws Exception{
        if (publisher.isRunning()) {
            publisher.stop();
        }
    }

    @Test
    public void StartingEventPublisherStartsANewThread() throws Exception {
        final int baseThreadCount = Thread.activeCount();
        publisher.start();
        assertEquals(Thread.activeCount(), baseThreadCount + 1);
    }

    @Test
    public void StartedEventPublisherReportsThatItselfIsRunning() throws Exception {
        publisher.start();
        assertTrue(publisher.isRunning());
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void EventPublisherCannotBeStartedTwice() throws Exception {
        publisher.start();
        publisher.start();
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void NewEventPublisherCannotBeStopped() throws Exception {
        publisher.stop();
    }

    @Test()
    public void NewEventPublisherReportsThatItselfIsNotRunning() throws Exception {
        assertFalse(publisher.isRunning());
    }

    @Test()
    public void StoppedEventPublisherReportsThatItselfIsNotRunning() throws Exception {
        publisher.start();
        publisher.stop();
        assertFalse(publisher.isRunning());
    }

    @Test(
            expectedExceptions = {
                    IllegalStateException.class
            }
    )
    public void RunningEventPublisherCannotBeStoppedTwice() throws Exception {
        publisher.start();
        publisher.stop();
        publisher.stop();
    }

    @Test
    public void WhenEventIsPublishedViaEventPublisherTheEventSubscriberReceivesThisEvent(final @Mocked EventSubscriber subscriber, final @Mocked Event event) throws Exception {
        publisher.addSubscriber(subscriber);
        publisher.start();

        publisher.publish(event);

        new Verifications() {{
            subscriber.onEvent(event);
        }};
    }
}