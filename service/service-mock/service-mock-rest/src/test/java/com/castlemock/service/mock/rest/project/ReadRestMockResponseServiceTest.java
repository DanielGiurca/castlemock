package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.ReadRestMockResponseOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadRestMockResponseServiceTest {

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadRestMockResponseService service;

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
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.builder().build();

        final ReadRestMockResponseInput input = ReadRestMockResponseInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .restMockResponse(mockResponse.getId())
                .build();

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        ReadRestMockResponseOutput result = service.process(input);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(mockResponse, result.getRestMockResponse());
    }

}
