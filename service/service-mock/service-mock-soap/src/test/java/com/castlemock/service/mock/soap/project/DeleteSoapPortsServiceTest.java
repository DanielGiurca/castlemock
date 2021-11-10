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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.service.mock.soap.project.input.DeleteSoapPortsInput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapPortsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteSoapPortsService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        Mockito.when(operationRepository.findWithPortId(soapPort.getId())).thenReturn(Arrays.asList(soapOperation));
        Mockito.when(mockResponseRepository.findWithOperationId(soapOperation.getId())).thenReturn(Arrays.asList(soapMockResponse));

        final DeleteSoapPortsInput input = DeleteSoapPortsInput.builder()
                .projectId(soapProject.getId())
                .ports(List.of(soapPort))
                .build();
        service.process(input);

        Mockito.verify(portRepository, Mockito.times(1)).delete(soapPort.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).delete(soapOperation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(soapMockResponse.getId());
    }
}
