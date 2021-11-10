/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.service.mock.rest.event;

import com.castlemock.model.core.event.EventStartDateComparator;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.service.mock.rest.event.input.ReadRestEventWithMethodIdInput;
import com.castlemock.service.mock.rest.event.output.ReadRestEventWithMethodIdOutput;

import java.util.Collections;
import java.util.List;

/**
 * The service provides the functionality to retrieve a list of events that belongs to a specific REST method.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestMethod
 */
@org.springframework.stereotype.Service
public class ReadRestEventWithMethodIdService extends AbstractRestEventService {

    public ReadRestEventWithMethodIdOutput process(ReadRestEventWithMethodIdInput input) {
        final List<RestEvent> events = repository.findEventsByMethodId(input.getRestMethodId());
        Collections.sort(events, new EventStartDateComparator());
        return ReadRestEventWithMethodIdOutput.builder()
                .restEvents(events)
                .build();
    }
}
