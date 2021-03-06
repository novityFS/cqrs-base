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

/**
 * A command handler is capable of executing the command specified by the command type <code>TCommand</code>
 * <p/>
 * See the following example for a command handler implementation:
 * <p/>
 * <pre>{@code
 * public class MyCommandCommandHandler implements CommandHandler<MyCommand> {
 *  private final EventPublisher publisher;
 *  private final MyCommandRepository repository;
 *
 *  public MyCommandCommandHandler(EventPublisher publisher, MyCommandRepository repository) {
 *    this.publisher = publisher;
 *    this.repository = repository;
 *  }
 *
 *  public void execute(MyCommand command) throws Exception {
 *    MyRootAggregate aggregate = repository.findById(command.id);
 *    aggregate.doSomething();
 *
 *    publisher.publish(aggregate.getEvents());
 *    aggregate.commitEvents();
 *  }
 * }
 * }</pre>
 *
 * @param <TCommand> The type of the command that this handler can execute
 */
public interface CommandHandler<TCommand> {
    /**
     * Executes the command
     *
     * @param command The command to be executed
     * @throws Exception If the command execution fails
     */
    void execute(TCommand command) throws Exception;
}
