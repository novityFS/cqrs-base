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
import de.novity.cqrs.base.api.CommandDispatcher;
import de.novity.cqrs.base.api.CommandHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * An in memory implementation of a command dispatcher that can be used in standalone applications. The registered
 * command handlers are backed by a <code>Map</code> implementation.
 */
public class InMemoryCommandDispatcher implements CommandDispatcher {
    private final Map<Class<Command>, CommandHandler> handlerMap;

    public InMemoryCommandDispatcher() {
        this.handlerMap = new HashMap<Class<Command>, CommandHandler>();
    }

    @SuppressWarnings("unchecked")
    public void registerHandler(Class commandType, CommandHandler handler) {
        handlerMap.put(commandType, handler);
    }


    @SuppressWarnings("unchecked")
    public void execute(Command command) throws Exception {
        if (command == null) {
            throw new NullPointerException("You tried to execute a null command");
        }

        CommandHandler handler = handlerMap.get((Class<Command>) command.getClass());

        if (handler == null) {
            throw new NullPointerException("You didn't register a command handler for command " + command.getClass().getSimpleName());
        }

        handler.execute(command);
    }
}
