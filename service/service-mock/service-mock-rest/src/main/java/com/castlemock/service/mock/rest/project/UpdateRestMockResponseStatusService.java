/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseStatusInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMockResponseStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@org.springframework.stereotype.Service
public class UpdateRestMockResponseStatusService extends AbstractRestProjectService {

    public UpdateRestMockResponseStatusOutput process(UpdateRestMockResponseStatusInput input) {
        final RestMockResponse mockResponse = this.mockResponseRepository.findOne(input.getMockResponseId());
        mockResponse.setStatus(input.getStatus());
        this.mockResponseRepository.update(input.getMockResponseId(), mockResponse);
        return UpdateRestMockResponseStatusOutput.builder().build();
    }
}
