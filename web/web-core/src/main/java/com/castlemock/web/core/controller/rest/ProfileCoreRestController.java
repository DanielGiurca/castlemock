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

import com.castlemock.model.core.user.User;
import com.castlemock.service.core.user.ReadUserByUsernameService;
import com.castlemock.service.core.user.UpdateCurrentUserService;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import com.castlemock.service.core.user.output.UpdateCurrentUserOutput;
import com.castlemock.web.core.model.UpdateProfileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class ProfileCoreRestController extends AbstractRestController {

    private final ReadUserByUsernameService readUserByUsernameService;
    private final UpdateCurrentUserService updateCurrentUserService;

    public ProfileCoreRestController(ReadUserByUsernameService readUserByUsernameService, UpdateCurrentUserService updateCurrentUserService) {
        this.readUserByUsernameService = readUserByUsernameService;
        this.updateCurrentUserService = updateCurrentUserService;
    }

    @ApiOperation(value = "Get profile",response = User.class,
            notes = "Get current profile. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved current profile")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final ReadUserByUsernameOutput output = readUserByUsernameService.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = output.getUser();

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        user.setPassword(EMPTY);
        return ResponseEntity.ok(user);
    }


    @ApiOperation(value = "Update profile",response = User.class,
            notes = "Get current profile. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved current profile")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateProfile(@RequestBody final UpdateProfileRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final ReadUserByUsernameOutput readUserByUsernameOutput = readUserByUsernameService.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = readUserByUsernameOutput.getUser();

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final UpdateCurrentUserOutput updateCurrentUserOutput = updateCurrentUserService.process(UpdateCurrentUserInput.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build());

        final User updatedUser = updateCurrentUserOutput.getUpdatedUser();

        updatedUser.setPassword(EMPTY);
        return ResponseEntity.ok(updatedUser);
    }

}
