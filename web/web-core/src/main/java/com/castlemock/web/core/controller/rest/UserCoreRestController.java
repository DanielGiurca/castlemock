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
import com.castlemock.service.core.user.*;
import com.castlemock.service.core.user.input.CreateUserInput;
import com.castlemock.service.core.user.input.DeleteUserInput;
import com.castlemock.service.core.user.input.ReadUserInput;
import com.castlemock.service.core.user.input.UpdateUserInput;
import com.castlemock.service.core.user.output.CreateUserOutput;
import com.castlemock.service.core.user.output.ReadAllUsersOutput;
import com.castlemock.service.core.user.output.ReadUserOutput;
import com.castlemock.service.core.user.output.UpdateUserOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class UserCoreRestController extends AbstractRestController {

    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final ReadAllUsersService readAllUsersService;
    private final ReadUserService readUserService;
    private final DeleteUserService deleteUserService;

    public UserCoreRestController(CreateUserService createUserService, UpdateUserService updateUserService, ReadAllUsersService readAllUsersService, ReadUserService readUserService, DeleteUserService deleteUserService){

        this.createUserService = createUserService;
        this.updateUserService = updateUserService;
        this.readAllUsersService = readAllUsersService;
        this.readUserService = readUserService;
        this.deleteUserService = deleteUserService;
    }

    @ApiOperation(value = "Create user",response = User.class,
            notes = "Create user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created user")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody final User user) {
        final CreateUserOutput output = createUserService.process(CreateUserInput.builder()
                .user(user)
                .build());
        final User createdUser = output.getSavedUser();
        createdUser.setPassword(EMPTY);
        return ResponseEntity.ok(createdUser);
    }

    @ApiOperation(value = "Update user",response = User.class,
            notes = "Update user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateUser(@PathVariable("userId") final String userId,
                    @RequestBody final User user) {
        final UpdateUserOutput output = updateUserService.process(UpdateUserInput.builder()
                .user(user)
                .userId(userId)
                .build());
        final User updatedUser = output.getUpdatedUser();
        updatedUser.setPassword(EMPTY);
        return ResponseEntity.ok(updatedUser);
    }

    @ApiOperation(value = "Get all users",response = User.class,
            notes = "Get all users. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all users")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<User>> getUsers() {
        final ReadAllUsersOutput output = readAllUsersService.process();
        final List<User> users = output.getUsers();
        users.forEach(user -> user.setPassword(EMPTY));
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "Get user",response = User.class,
            notes = "Get user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getUser(@PathVariable("userId") final String userId) {
        final ReadUserOutput output = readUserService.process(ReadUserInput.builder()
                .userId(userId)
                .build());
        final User user = output.getUser();
        user.setPassword(EMPTY);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Delete user", notes = "Delete user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    void deleteUser(@PathVariable("userId") final String userId) {
        deleteUserService.process(DeleteUserInput.builder()
                .userId(userId)
                .build());
    }

}
