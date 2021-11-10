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

import com.castlemock.repository.soap.event.SoapEventRepository;
import com.castlemock.service.mock.soap.event.output.ClearAllSoapEventOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ClearAllSoapEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private ClearAllSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final ClearAllSoapEventOutput output  = service.process();

        Mockito.verify(repository, Mockito.times(1)).clearAll();
    }


}
