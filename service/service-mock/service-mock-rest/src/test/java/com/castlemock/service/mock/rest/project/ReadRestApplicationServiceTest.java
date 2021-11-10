package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.ReadRestApplicationInput;
import com.castlemock.service.mock.rest.project.output.ReadRestApplicationOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadRestApplicationServiceTest {

    @Mock
    private RestProjectRepository repository;

    @Mock
    private RestApplicationRepository applicationRepository;

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @InjectMocks
    private ReadRestApplicationService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final RestApplication application = RestApplicationTestBuilder.builder().build();
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();

        final ReadRestApplicationInput input = ReadRestApplicationInput.builder()
                .restProjectId(projectId)
                .restApplicationId(application.getId())
                .build();

        Mockito.when(applicationRepository.findOne(application.getId())).thenReturn(application);
        Mockito.when(resourceRepository.findWithApplicationId(application.getId())).thenReturn(Arrays.asList(resource));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(Arrays.asList(method));
        ReadRestApplicationOutput result = service.process(input);

        Mockito.verify(applicationRepository, Mockito.times(1)).findOne(application.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithApplicationId(application.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(application, result.getRestApplication());
    }

}
