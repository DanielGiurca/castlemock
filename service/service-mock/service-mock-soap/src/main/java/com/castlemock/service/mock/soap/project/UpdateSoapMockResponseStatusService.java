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
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseStatusInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@org.springframework.stereotype.Service
public class UpdateSoapMockResponseStatusService extends AbstractSoapProjectService {

    public UpdateSoapMockResponseStatusOutput process(UpdateSoapMockResponseStatusInput input) {
        final SoapMockResponse mockResponse = this.mockResponseRepository.findOne(input.getMockResponseId());
        mockResponse.setStatus(input.getMockResponseStatus());
        this.mockResponseRepository.update(input.getMockResponseId(), mockResponse);
        return UpdateSoapMockResponseStatusOutput.builder().build();
    }
}
