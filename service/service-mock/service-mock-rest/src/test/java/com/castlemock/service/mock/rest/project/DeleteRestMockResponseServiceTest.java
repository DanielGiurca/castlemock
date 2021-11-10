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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.service.mock.rest.project.input.DeleteRestMockResponseInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestMockResponseServiceTest {

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteRestMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final String methodId = "MethodId";
        final String mockResponseId = "MockResponseId";
        final RestMockResponse restMockResponse = RestMockResponseTestBuilder.builder().build();

        Mockito.when(mockResponseRepository.delete(Mockito.any())).thenReturn(restMockResponse);

        final DeleteRestMockResponseInput input =
                DeleteRestMockResponseInput.builder()
                        .restProjectId(projectId)
                        .restApplicationId(applicationId)
                        .restResourceId(resourceId)
                        .restMethodId(methodId)
                        .restMockResponseId(mockResponseId)
                        .build();
        service.process(input);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(mockResponseId);
    }
}
