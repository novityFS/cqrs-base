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
 * This is the base class for all commands.
 * <p/>
 * A command is a message to the domain to get something changed. The command is named with a verb in the imperative
 * mood and may include the aggregate type. For example <code>ConfirmOrder</code> would be a good name for
 * requesting the domain to confirm an order.
 * <p/>
 * Whereas the name (type) of the command carries the intention, the fields of the command carry the data needed to
 * fulfill the request.
 * <p/>
 * As a good practise, make your own commands immutable having only final fields. See the following example:
 * <p/>
 * <pre>{@code
 * public class ConfirmOrder extends Command {
 *   public final String id;
 *
 *   public ConfirmOrder(String id) {
 *      this.id = id
 *   }
 * }
 * }</pre>
 */
public abstract class Command {
}
