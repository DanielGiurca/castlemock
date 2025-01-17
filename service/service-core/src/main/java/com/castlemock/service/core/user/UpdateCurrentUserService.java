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

import com.castlemock.model.core.user.User;
import com.castlemock.service.core.system.LoggedInUserProvider;
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;
import com.castlemock.service.core.user.output.UpdateCurrentUserOutput;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateCurrentUserService extends AbstractUserService {

    private final LoggedInUserProvider loggedInUserProvider;

    @Autowired
    public UpdateCurrentUserService(LoggedInUserProvider loggedInUserProvider) {
        this.loggedInUserProvider = loggedInUserProvider;
    }

    public UpdateCurrentUserOutput process(UpdateCurrentUserInput input) {
        final String loggedInUsername = loggedInUserProvider.getLoggedInUsername();

        if(!input.getUsername().equalsIgnoreCase(loggedInUsername)){
            final User existingUser = findByUsername(input.getUsername());
            Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already used");
        }


        final User loggedInUser = findByUsername(loggedInUsername);
        loggedInUser.setUsername(input.getUsername());
        loggedInUser.setEmail(input.getEmail());
        loggedInUser.setPassword(input.getPassword());
        loggedInUser.setFullName(input.getFullName());
        loggedInUser.setUpdated(new Date());


        update(loggedInUser.getId(), loggedInUser);
        return new UpdateCurrentUserOutput(loggedInUser);
    }
}
