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
 * A command dispatcher is a central endpoint that receives commands to be executed by your domain. Upon requesting
 * a command to be executed by your domain, the command dispatcher looks up a <code>CommandHandler</code> that can
 * fulfil the request.
 * <p/>
 * You must have you infrastructure implement at least one command dispatcher and publish it to your clients.
 */
public interface CommandDispatcher {
    /**
     * @param command The command to be executed by the domain
     * @throws Exception If the execution of the command failed
     */
    void execute(Command command) throws Exception;
}
