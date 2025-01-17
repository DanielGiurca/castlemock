package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapResourceOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadSoapResourceServiceTest {

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private ReadSoapResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapResource resource = SoapResourceTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final ReadSoapResourceInput input = ReadSoapResourceInput.builder()
                .projectId(projectId)
                .resourceId(resource.getId())
                .build();

        Mockito.when(resourceRepository.findOne(resource.getId())).thenReturn(resource);
        ReadSoapResourceOutput result = service.process(input);

        Mockito.verify(resourceRepository, Mockito.times(1)).findOne(resource.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(resource, result.getResource());
    }

}
