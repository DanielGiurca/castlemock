/*
 * Copyright 2019 Karl Dahlgren
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

package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.system.SystemInformation;
import com.castlemock.model.core.user.User;
import com.castlemock.service.core.system.GetSystemInformationService;
import com.castlemock.service.core.system.input.GetSystemInformationInput;
import com.castlemock.service.core.system.output.GetSystemInformationOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class SystemCoreRestController extends AbstractRestController {

    private final GetSystemInformationService getSystemInformationService;

    public SystemCoreRestController(GetSystemInformationService getSystemInformationService) {
        this.getSystemInformationService = getSystemInformationService;
    }

    @ApiOperation(value = "Get system information",response = User.class,
            notes = "Get system information. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved system information")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/system")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<SystemInformation> getSystemInformation() {
        final GetSystemInformationOutput output = getSystemInformationService.process(new GetSystemInformationInput());
        return ResponseEntity.ok(output.getSystemInformation());
    }

}
