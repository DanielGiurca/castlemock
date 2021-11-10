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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMockResponseOutput;

/**
 * The service provides the functionality to update an already existing REST mock response.
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestMockResponseService extends AbstractRestProjectService {

    public UpdateRestMockResponseOutput process(UpdateRestMockResponseInput input) {
        final RestMockResponse existing = this.mockResponseRepository.findOne(input.getRestMockResponseId());

        existing.setName(input.getName());
        existing.setBody(input.getBody());
        existing.setHttpStatusCode(input.getHttpStatusCode());
        existing.setHttpHeaders(input.getHttpHeaders());
        existing.setStatus(input.getStatus());
        existing.setUsingExpressions(input.isUsingExpressions());
        existing.setParameterQueries(input.getParameterQueries());
        existing.setXpathExpressions(input.getXpathExpressions());
        existing.setJsonPathExpressions(input.getJsonPathExpressions());
        existing.setHeaderQueries(input.getHeaderQueries());

        final RestMockResponse updated = this.mockResponseRepository.update(input.getRestMockResponseId(), existing);
        return UpdateRestMockResponseOutput.builder()
                .updatedRestMockResponse(updated)
                .build();
    }
}
