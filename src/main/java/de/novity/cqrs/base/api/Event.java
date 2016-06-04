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
 * This is the base class for all events.
 * <p/>
 * An event is the response of the domain that something has changed. The event is always named with the past participle
 * verb and may contain the aggregate that changed. For example <code>OrderConfirmed</code> would be a good name for
 * indicating that an order was confirmed by the domain.
 * <p/>
 * Whereas the name (type) of the event indicates the nature of the change, the fields of the event carry the data
 * needed to further qualify the change.
 * <p/>
 * As a good practise, make your own events immutable having only final fields. See the following example:
 * <p/>
 * <pre>{@code
 * public class OrderConfirmed extends Event {
 *   public final String id;
 *
 *   public OrderConfirmed(String id) {
 *      this.id = id
 *   }
 * }
 * }</pre>
 */

public abstract class Event {
}
