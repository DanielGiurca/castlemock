package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReadSoapMockResponseServiceTest {

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private ReadSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";
        final String operationId = "OPERATION ID";

        final ReadSoapMockResponseInput input = ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(mockResponse.getId())
                .build();

        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        ReadSoapMockResponseOutput result = service.process(input);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(mockResponse, result.getMockResponse());
    }

}
