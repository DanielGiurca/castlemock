package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.repository.rest.project.RestProjectRepository;
import com.castlemock.service.mock.rest.project.input.ReadAllRestProjectsInput;
import com.castlemock.service.mock.rest.project.output.ReadAllRestProjectsOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ReadAllRestProjectsServiceTest {

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private ReadAllRestProjectsService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final List<RestProject> projects = Arrays.asList(project);

        final ReadAllRestProjectsInput input = ReadAllRestProjectsInput.builder().build();

        Mockito.when(repository.findAll()).thenReturn(projects);
        ReadAllRestProjectsOutput result = service.process(input);

        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assert.assertNotNull(result);
        Assert.assertEquals(projects, result.getRestProjects());
    }

}
