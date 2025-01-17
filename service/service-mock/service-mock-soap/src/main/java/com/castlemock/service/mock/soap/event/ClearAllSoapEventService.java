/*
 * Copyright 2016 Karl Dahlgren
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

import com.castlemock.service.mock.soap.event.input.ClearAllSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ClearAllSoapEventOutput;

/**
 * The service provides the functionality to retrieve all stored SOAP events in the SOAP event repository.
 * @author Karl Dahlgren
 * @since 1.7
 */
@org.springframework.stereotype.Service
public class ClearAllSoapEventService extends AbstractSoapEventService {


    public ClearAllSoapEventOutput process() {
        repository.clearAll();
        return ClearAllSoapEventOutput.builder().build();
    }
}
