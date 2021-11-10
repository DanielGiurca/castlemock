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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.DuplicateSoapMockResponsesInput;
import com.castlemock.service.mock.soap.project.output.DuplicateSoapMockResponsesOutput;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@org.springframework.stereotype.Service
public class DuplicateSoapMockResponsesService extends AbstractSoapProjectService {

    public DuplicateSoapMockResponsesOutput process(DuplicateSoapMockResponsesInput input) {
        input
                .getMockResponseIds()
                .stream()
                .map(this.mockResponseRepository::findOne)
                .map(SoapMockResponse::toBuilder)
                .map(builder -> builder.id(null))
                .map(SoapMockResponse.Builder::build)
                .forEach(this.mockResponseRepository::save);

        return DuplicateSoapMockResponsesOutput.builder().build();
    }
}
