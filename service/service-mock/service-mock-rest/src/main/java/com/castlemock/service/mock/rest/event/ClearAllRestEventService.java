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

package com.castlemock.service.mock.rest.event;

import com.castlemock.service.mock.rest.event.input.ClearAllRestEventInput;
import com.castlemock.service.mock.rest.event.output.ClearAllRestEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ClearAllRestEventService extends AbstractRestEventService {

    public ClearAllRestEventOutput process(ClearAllRestEventInput input) {
        repository.clearAll();
        return ClearAllRestEventOutput.builder().build();
    }


}
