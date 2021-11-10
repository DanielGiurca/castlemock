package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class ReadSoapProjectServiceTest {

    @Mock
    private SoapProjectRepository repository;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapResourceRepository resourceRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private ReadSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final SoapResource resource = SoapResourceTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();

        final ReadSoapProjectInput input = ReadSoapProjectInput.builder()
                .projectId(project.getId())
                .build();

        Mockito.when(repository.findOne(project.getId())).thenReturn(project);
        Mockito.when(portRepository.findWithProjectId(project.getId())).thenReturn(Arrays.asList(port));
        Mockito.when(resourceRepository.findWithProjectId(project.getId())).thenReturn(Arrays.asList(resource));
        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(Arrays.asList(operation));
        ReadSoapProjectOutput result = service.process(input);

        Mockito.verify(repository, Mockito.times(1)).findOne(project.getId());
        Mockito.verify(portRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithProjectId(project.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(project, result.getProject());
    }

}
