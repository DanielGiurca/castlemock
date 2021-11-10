package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class LoadSoapResourceServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private LoadSoapResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapResource soapResource = SoapResourceTestBuilder.builder().build();
        final String resourceContent = "Resource content";
        Mockito.when(resourceRepository.loadSoapResource(soapResource.getId())).thenReturn(resourceContent);


        final LoadSoapResourceInput input = LoadSoapResourceInput.builder()
                .projectId("Project id")
                .resourceId(soapResource.getId())
                .build();
        LoadSoapResourceOutput serviceResult = service.process(input);

        Assert.assertNotNull(serviceResult);
        Assert.assertEquals(resourceContent, serviceResult.getResource());

        Mockito.verify(resourceRepository, Mockito.times(1)).loadSoapResource(soapResource.getId());
    }
}
