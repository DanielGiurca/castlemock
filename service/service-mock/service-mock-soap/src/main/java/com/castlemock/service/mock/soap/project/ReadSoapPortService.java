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

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.service.mock.soap.project.input.ReadSoapPortInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapPortOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapPortService extends AbstractSoapProjectService {

    public ReadSoapPortOutput process(ReadSoapPortInput input) {
        final SoapPort soapPort = this.portRepository.findOne(input.getPortId());
        final List<SoapOperation> operations = this.operationRepository.findWithPortId(input.getPortId());
        soapPort.setOperations(operations);
        return ReadSoapPortOutput.builder()
                .port(soapPort)
                .build();
    }
}
