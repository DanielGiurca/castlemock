/*
 * Copyright 2020 Karl Dahlgren
 *
 * Licensed under the Apache License, System 2.0 (the "License");
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
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.service.core.user.*;
import com.castlemock.service.core.user.input.DeleteUserInput;
import com.castlemock.service.core.user.input.ReadAllUsersInput;
import com.castlemock.service.core.user.input.ReadUserInput;
import com.castlemock.service.core.user.output.CreateUserOutput;
import com.castlemock.service.core.user.output.DeleteUserOutput;
import com.castlemock.service.core.user.output.ReadAllUsersOutput;
import com.castlemock.service.core.user.output.ReadUserOutput;
import com.castlemock.service.core.user.output.UpdateUserOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserCoreRestControllerTest {
    private CreateUserService createUserService;
    private UpdateUserService updateUserService;
    private ReadAllUsersService readAllUsersService;
    private ReadUserService readUserService;
    private DeleteUserService deleteUserService;
    private UserCoreRestController userCoreRestController;

    @BeforeEach
    void setup(){
        this.createUserService = mock(CreateUserService.class);
        this.updateUserService = mock(UpdateUserService.class);
        this.readAllUsersService = mock(ReadAllUsersService.class);
        this.readUserService = mock(ReadUserService.class);
        this.deleteUserService = mock(DeleteUserService.class);
        this.userCoreRestController = new UserCoreRestController(createUserService,updateUserService,readAllUsersService,readUserService,deleteUserService);
    }

    @Test
    @DisplayName("Create user")
    void testCreateUser(){
        final User user = UserTestBuilder.builder().build();
        when(createUserService.process(any())).thenReturn(CreateUserOutput.builder()
                .savedUser(user)
                .build());
        final ResponseEntity<User> responseEntity = this.userCoreRestController.createUser(user);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(user, responseEntity.getBody());

        verify(createUserService, times(1)).process(any());
    }

    @Test
    @DisplayName("Update user")
    void testUpdateUser(){
        final User user = UserTestBuilder.builder().build();
        when(updateUserService.process(any())).thenReturn(UpdateUserOutput.builder()
                .updatedUser(user)
                .build());
        final ResponseEntity<User> responseEntity = this.userCoreRestController.updateUser(user.getId(), user);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(user, responseEntity.getBody());

        verify(updateUserService, times(1)).process(any());
    }

    @Test
    @DisplayName("Get users")
    void testGetUsers(){
        final User user = UserTestBuilder.builder().build();
        final List<User> users = List.of(user);

        when(readAllUsersService.process()).thenReturn(ReadAllUsersOutput.builder()
                .users(users)
                .build());

        final ResponseEntity<List<User>> responseEntity = this.userCoreRestController.getUsers();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(users, responseEntity.getBody());

        verify(readAllUsersService, times(1)).process();
    }

    @Test
    @DisplayName("Get user")
    void testGetUser(){
        final User user = UserTestBuilder.builder().build();

        when(readUserService.process(any())).thenReturn(ReadUserOutput.builder()
                .user(user)
                .build());

        final ResponseEntity<User> responseEntity = this.userCoreRestController.getUser(user.getId());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(user, responseEntity.getBody());

        verify(readUserService, times(1)).process(any(ReadUserInput.class));
    }

    @Test
    @DisplayName("Delete user")
    void testUser(){
        when(deleteUserService.process(any())).thenReturn(new DeleteUserOutput());

        this.userCoreRestController.deleteUser("userid");

        verify(deleteUserService, times(1)).process(any(DeleteUserInput.class));
    }

}
