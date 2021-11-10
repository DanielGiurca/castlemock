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

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.service.mock.rest.event.input.CreateRestEventInput;
import com.castlemock.service.mock.rest.event.output.CreateRestEventOutput;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestEventService extends AbstractRestEventService {

    @Value("${rest.event.max}")
    private Integer restMaxEventCount;

    public CreateRestEventOutput process(CreateRestEventInput input) {
        if(count() >= restMaxEventCount){
            repository.deleteOldestEvent();
        }
        final RestEvent createdRestEvent = save(input.getRestEvent());
        return CreateRestEventOutput.builder()
                .createdRestEvent(createdRestEvent)
                .build();
    }
}
