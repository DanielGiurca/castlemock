package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapMockResponseService extends AbstractSoapProjectService {

    public ReadSoapMockResponseOutput process(ReadSoapMockResponseInput input) {
        final SoapMockResponse soapMockResponse = this.mockResponseRepository.findOne(input.getSoapMockResponseId());
        return ReadSoapMockResponseOutput.builder()
                .mockResponse(soapMockResponse)
                .build();
    }
}
