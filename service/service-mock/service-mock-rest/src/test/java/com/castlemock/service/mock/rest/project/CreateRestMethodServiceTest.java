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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.service.mock.rest.project.input.CreateRestMethodInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMethodOutput;
import org.junit.Assert;
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
public class CreateRestMethodServiceTest {

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private CreateRestMethodService service;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject restProject = RestProjectTestBuilder.builder().build();
        final RestApplication restApplication = RestApplicationTestBuilder.builder().build();
        final  RestResource restResource = RestResourceTestBuilder.builder().build();
        final RestMethod restMethod = RestMethodTestBuilder.builder().build();
        Mockito.when(methodRepository.save(Mockito.any(RestMethod.class))).thenReturn(restMethod);

        final CreateRestMethodInput input = CreateRestMethodInput.builder()
                .projectId(restProject.getId())
                .applicationId(restApplication.getId())
                .resourceId(restResource.getId())
                .name(restMethod.getName())
                .httpMethod(restMethod.getHttpMethod())
                .build();
        CreateRestMethodOutput createRestApplicationOutput = service.process(input);
        final RestMethod returnedRestMethod = createRestApplicationOutput.getCreatedRestMethod();

        Assert.assertEquals(restMethod.getName(), returnedRestMethod.getName());
        Assert.assertEquals(restMethod.getHttpMethod(), returnedRestMethod.getHttpMethod());
        Assert.assertEquals(restMethod.getDefaultBody(), returnedRestMethod.getDefaultBody());
        Assert.assertEquals(restMethod.getForwardedEndpoint(), returnedRestMethod.getForwardedEndpoint());
        Assert.assertEquals(restMethod.getStatus(), returnedRestMethod.getStatus());
        Assert.assertEquals(restMethod.getMockResponses(), returnedRestMethod.getMockResponses());
        Assert.assertEquals(restMethod.getResponseStrategy(), returnedRestMethod.getResponseStrategy());
    }

}
