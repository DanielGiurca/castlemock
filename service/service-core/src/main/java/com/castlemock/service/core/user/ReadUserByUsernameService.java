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
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadUserByUsernameService extends AbstractUserService{


    public ReadUserByUsernameOutput process(ReadUserByUsernameInput input) {
        final String username = input.getUsername();
        final User user = findByUsername(username);
        return ReadUserByUsernameOutput.builder()
                .user(user)
                .build();
    }
}
