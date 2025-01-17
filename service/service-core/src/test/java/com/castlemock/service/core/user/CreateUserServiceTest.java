/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.core.user;

import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.repository.user.UserRepository;
import com.castlemock.service.core.user.input.CreateUserInput;
import com.castlemock.service.core.user.output.CreateUserOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateUserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private CreateUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        User user = new User();
        user.setUsername("Username");
        user.setPassword("Password");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");

        User createdUser = new User();
        createdUser.setId(new String());
        createdUser.setPassword("Password");
        createdUser.setUsername("Username");
        createdUser.setStatus(Status.ACTIVE);
        createdUser.setRole(Role.ADMIN);
        createdUser.setEmail("email@email.com");

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(createdUser);
        final CreateUserInput input = CreateUserInput.builder().user(user).build();
        final CreateUserOutput output = service.process(input);

        final User returnedUser = output.getSavedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(returnedUser.getId(), createdUser.getId());
        Assert.assertNotEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
