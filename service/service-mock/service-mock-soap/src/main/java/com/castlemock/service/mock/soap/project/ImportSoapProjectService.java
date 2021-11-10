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

import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.soap.SoapExportContainer;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.service.mock.soap.project.input.ImportSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ImportSoapProjectOutput;
import com.google.common.base.Strings;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportSoapProjectService extends AbstractSoapProjectService {

    @SuppressWarnings("deprecation")
    public ImportSoapProjectOutput process(ImportSoapProjectInput input) {

        final SoapExportContainer exportContainer = ExportContainerSerializer.deserialize(input.getProjectRaw(), SoapExportContainer.class);
        final SoapProject project = exportContainer.getProject();

        if(this.repository.exists(project.getId())){
            throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
        }

        this.repository.save(project);

        for(SoapPort port : exportContainer.getPorts()){
            if(this.portRepository.exists(port.getId())){
                throw new IllegalArgumentException("A port with the following key already exists: " + port.getId());
            }

            this.portRepository.save(port);
        }

        for(SoapResource resource : exportContainer.getResources()){
            if(this.resourceRepository.exists(resource.getId())){
                throw new IllegalArgumentException("A resource with the following key already exists: " + resource.getId());
            }

            this.resourceRepository.saveSoapResource(resource, resource.getContent());
        }

        for(SoapOperation operation : exportContainer.getOperations()){
            if(this.operationRepository.exists(operation.getId())){
                throw new IllegalArgumentException("An operation with the following key already exists: " + operation.getId());
            }

            if(operation.getOperationIdentifier() == null){
                SoapOperationIdentifier operationIdentifier =
                        new SoapOperationIdentifier();
                operationIdentifier.setName(operation.getIdentifier());
                operation.setOperationIdentifier(operationIdentifier);
                operation.setIdentifier(null);
            }
            if(operation.getIdentifyStrategy() == null){
                operation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);
            }
            if(operation.getCurrentResponseSequenceIndex() == null){
                operation.setCurrentResponseSequenceIndex(0);
            }
            if(!Strings.isNullOrEmpty(operation.getDefaultXPathMockResponseId())){
                operation.setDefaultMockResponseId(operation.getDefaultXPathMockResponseId());
                operation.setDefaultXPathMockResponseId(null);
            }

            operation.setCurrentResponseSequenceIndex(0);
            this.operationRepository.save(operation);
        }

        for(SoapMockResponse mockResponse : exportContainer.getMockResponses()){
            if(this.mockResponseRepository.exists(mockResponse.getId())){
                throw new IllegalArgumentException("A mocked response with the following key already exists: " + mockResponse.getId());
            }

            if(!Strings.isNullOrEmpty(mockResponse.getXpathExpression())){
                final SoapXPathExpression xPathExpression = new SoapXPathExpression();
                xPathExpression.setExpression(mockResponse.getXpathExpression());
                mockResponse.getXpathExpressions().add(xPathExpression);
                mockResponse.setXpathExpression(null);
            }

            this.mockResponseRepository.save(mockResponse);
        }

        return ImportSoapProjectOutput.builder()
                .project(project)
                .build();
    }
}
