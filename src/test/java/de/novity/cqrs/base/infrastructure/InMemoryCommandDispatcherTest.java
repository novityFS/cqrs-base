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

import de.novity.cqrs.base.api.Command;
import de.novity.cqrs.base.api.CommandHandler;
import mockit.Mocked;
import mockit.Verifications;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InMemoryCommandDispatcherTest {
    private InMemoryCommandDispatcher dispatcher;

    @BeforeMethod
    public void setUp() throws Exception {
        dispatcher = new InMemoryCommandDispatcher();
    }

    @Test
    // Given I have a command
    // And a suitable command handler
    public void ExecutingCommandWithRegisteredHandlerSucceeds(@Mocked final Command command, @Mocked final CommandHandler<Command> handler) throws Exception {
        // And I registered this handler for the command
        dispatcher.registerHandler(command.getClass(), handler);

        // When I execute the command
        dispatcher.execute(command);

        // Then the handler is executed with the command
        new Verifications() {{
            handler.execute(command);
        }};
    }

    @Test(
            expectedExceptions = {
                    NullPointerException.class
            }
    )
    // Given I have a command
    public void ExecutingCommandWithUnregisteredHandlerFails(@Mocked final Command command) throws Exception {
        // When I execute the command
        dispatcher.execute(command);

        // Then the dispatcher fails
    }
}