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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.service.mock.soap.project.input.IdentifySoapOperationInput;
import com.castlemock.service.mock.soap.project.output.IdentifySoapOperationOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class IdentifySoapOperationService extends AbstractSoapProjectService  {

    public IdentifySoapOperationOutput process(IdentifySoapOperationInput input) {
        final SoapPort port = this.portRepository.findWithUri(input.getProjectId(), input.getUri());
        final SoapOperation operation =
                this.operationRepository.findWithMethodAndVersionAndIdentifier(
                        port.getId(), input.getHttpMethod(),
                        input.getType(), input.getOperationIdentifier());
        if(operation == null){
            throw new IllegalArgumentException("Unable to identify SOAP operation: " + input.getUri());
        }

        final List<SoapMockResponse> mockResponses = this.mockResponseRepository.findWithOperationId(operation.getId());
        operation.setMockResponses(mockResponses);

        return IdentifySoapOperationOutput.builder()
                .projectId(input.getProjectId())
                .portId(port.getId())
                .operationId(operation.getId())
                .operation(operation)
                .build();
    }


}
