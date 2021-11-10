/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.controller.rest;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.service.mock.soap.project.*;
import com.castlemock.service.mock.soap.project.input.*;
import com.castlemock.service.mock.soap.project.output.CreateSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import com.castlemock.web.mock.soap.model.CreateSoapMockResponseRequest;
import com.castlemock.web.mock.soap.model.DuplicateSoapMockOperationsRequest;
import com.castlemock.web.mock.soap.model.UpdateSoapMockResponseRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/rest/soap")
@Api(value="SOAP - Mocked response", description="REST Operations for Castle Mock SOAP mocked response",
        tags = {"SOAP - Mocked response"})
public class SoapMockResponseRestController extends AbstractRestController {

    @Autowired
    private ReadSoapMockResponseService readSoapMockResponseService;
    @Autowired
    private CreateSoapMockResponseService createSoapMockResponseService;
    @Autowired
    private UpdateSoapMockResponseService updateSoapMockResponseService;
    @Autowired
    private DeleteSoapMockResponseService deleteSoapMockResponseService;
    @Autowired
    private DuplicateSoapMockResponsesService duplicateSoapMockResponsesService;

    @Autowired
    public SoapMockResponseRestController(){

    }

    @ApiOperation(value = "Get mocked response", response = SoapMockResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved mocked response")})
    @RequestMapping(method = RequestMethod.GET,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SoapMockResponse> getMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final ReadSoapMockResponseOutput output = readSoapMockResponseService.process(ReadSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @ApiOperation(value = "Create mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created mocked response")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> createMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody final CreateSoapMockResponseRequest request) {
        final CreateSoapMockResponseOutput output = createSoapMockResponseService.process(CreateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .body(request.getBody())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .name(request.getName())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions())
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @ApiOperation(value = "Update mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated mocked response")})
    @RequestMapping(method = RequestMethod.PUT,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> updateMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId,
            @RequestBody final UpdateSoapMockResponseRequest request) {
        final UpdateSoapMockResponseOutput output = updateSoapMockResponseService.process(UpdateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .body(request.getBody())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .name(request.getName())
                .status(request.getStatus())
                .usingExpressions(request.isUsingExpressions())
                .xpathExpressions(request.getXpathExpressions())
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @ApiOperation(value = "Delete mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete mocked response")})
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/{responseId}")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> deleteMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @ApiParam(name = "responseId", value = "The id of the response")
            @PathVariable(value = "responseId") final String responseId) {
        final DeleteSoapMockResponseOutput output = deleteSoapMockResponseService.process(DeleteSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseId(responseId)
                .build());
        return ResponseEntity.ok(output.getMockResponse());
    }

    @ApiOperation(value = "Duplicate mocked response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully duplicate mocked responses")})
    @RequestMapping(method = RequestMethod.POST,
            value = "/project/{projectId}/port/{portId}/operation/{operationId}/mockresponse/duplicate")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public ResponseEntity<SoapMockResponse> duplicateMockResponse(
            @ApiParam(name = "projectId", value = "The id of the project")
            @PathVariable(value = "projectId") final String projectId,
            @ApiParam(name = "portId", value = "The id of the port")
            @PathVariable(value = "portId") final String portId,
            @ApiParam(name = "operationId", value = "The id of the operation")
            @PathVariable(value = "operationId") final String operationId,
            @RequestBody DuplicateSoapMockOperationsRequest request) {
        duplicateSoapMockResponsesService.process(DuplicateSoapMockResponsesInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operationId)
                .mockResponseIds(request.getMockResponseIds())
                .build());
        return ResponseEntity.ok().build();
    }

}
