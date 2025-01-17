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

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.input.ReadRestMethodInput;
import com.castlemock.service.mock.rest.project.output.ReadRestMethodOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestMethodService extends AbstractRestProjectService{

    public ReadRestMethodOutput process(ReadRestMethodInput input) {
        final RestResource restResource = this.resourceRepository.findOne(input.getRestResourceId());
        final RestMethod restMethod = this.methodRepository.findOne(input.getRestMethodId());
        final List<RestMockResponse> mockResponses = this.mockResponseRepository.findWithMethodId(input.getRestMethodId());

        restMethod.setUri(restResource.getUri());
        restMethod.setMockResponses(mockResponses);

        if(restMethod.getDefaultMockResponseId() != null){
            // Iterate through all the mocked responses to identify
            // which has been set to be the default XPath mock response.
            mockResponses
                    .stream()
                    .filter(mockResponse -> mockResponse.getId().equals(restMethod.getDefaultMockResponseId()))
                    .findFirst()
                    .ifPresent(mockResponse -> restMethod.setDefaultResponseName(mockResponse.getName()));
        }

        return ReadRestMethodOutput.builder()
                .restMethod(restMethod)
                .build();
    }
}
