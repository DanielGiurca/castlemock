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

package com.castlemock.service.mock.soap.event;

import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.repository.soap.event.SoapEventRepository;
import com.castlemock.service.mock.soap.event.input.CreateSoapEventInput;
import com.castlemock.service.mock.soap.event.output.CreateSoapEventOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private CreateSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "soapMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        Mockito.when(repository.save(Mockito.any(SoapEvent.class))).thenReturn(soapEvent);
        Mockito.when(repository.count()).thenReturn(0);

        final CreateSoapEventInput input = CreateSoapEventInput.builder().soapEvent(soapEvent).build();
        CreateSoapEventOutput createRestApplicationOutput = service.process(input);
        final SoapEvent returnedSoapEvent = createRestApplicationOutput.getCreatedSoapEvent();

        Mockito.verify(repository, Mockito.times(0)).deleteOldestEvent();
        Mockito.verify(repository, Mockito.times(1)).save(soapEvent);

        Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
        Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
        Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }

    @Test
    public void testMaxCountReached(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        Mockito.when(repository.save(Mockito.any(SoapEvent.class))).thenReturn(soapEvent);
        Mockito.when(repository.count()).thenReturn(6);

        final CreateSoapEventInput input = CreateSoapEventInput.builder().soapEvent(soapEvent).build();
        final CreateSoapEventOutput createRestApplicationOutput = service.process(input);
        final SoapEvent returnedSoapEvent = createRestApplicationOutput.getCreatedSoapEvent();

        Mockito.verify(repository, Mockito.times(1)).deleteOldestEvent();
        Mockito.verify(repository, Mockito.times(1)).save(soapEvent);

        Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
        Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
        Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }

}
