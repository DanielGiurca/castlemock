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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.CreateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapMockResponseOutput;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapMockResponseService extends AbstractSoapProjectService {

    public CreateSoapMockResponseOutput process(CreateSoapMockResponseInput input) {
        final SoapMockResponse mockResponse = SoapMockResponse.builder()
                .name(input.getName())
                .body(input.getBody().orElse(""))
                .httpStatusCode(input.getHttpStatusCode().orElse(200))
                .usingExpressions(input.getUsingExpressions().orElse(false))
                .status(input.getStatus())
                .operationId(input.getOperationId())
                .httpHeaders(input.getHttpHeaders().orElseGet(CopyOnWriteArrayList::new))
                .contentEncodings(new CopyOnWriteArrayList<>())
                .xpathExpressions(input.getXpathExpressions().orElseGet(CopyOnWriteArrayList::new))
                .build();
        final SoapMockResponse createdSoapMockResponse = this.mockResponseRepository.save(mockResponse);
        return CreateSoapMockResponseOutput.builder()
                .mockResponse(createdSoapMockResponse)
                .build();
    }
}
