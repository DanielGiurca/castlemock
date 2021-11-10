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

package com.castlemock.service.mock.soap.event;

import com.castlemock.model.core.event.EventStartDateComparator;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.service.mock.soap.event.input.ReadSoapEventsByOperationIdInput;
import com.castlemock.service.mock.soap.event.output.ReadSoapEventsByOperationIdOutput;

import java.util.List;

/**
 * The service provides the functionality to retrieve all SOAP events for a specific SOAP operation.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapOperation
 */
@org.springframework.stereotype.Service
public class ReadSoapEventsByOperationIdService extends AbstractSoapEventService {

    public ReadSoapEventsByOperationIdOutput process(ReadSoapEventsByOperationIdInput input) {
        final List<SoapEvent> events = repository.findEventsByOperationId(input.getOperationId());
        events.sort(new EventStartDateComparator());
        return ReadSoapEventsByOperationIdOutput.builder()
                .soapEvents(events)
                .build();
    }
}
