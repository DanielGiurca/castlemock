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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.service.mock.rest.project.input.CreateRestApplicationInput;
import com.castlemock.service.mock.rest.project.output.CreateRestApplicationOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestApplicationService extends AbstractRestProjectService {

    public CreateRestApplicationOutput process(CreateRestApplicationInput input) {
        final RestApplication application = new RestApplication();
        application.setProjectId(input.getProjectId());
        application.setName(input.getName());
        final RestApplication createdRestApplication = this.applicationRepository.save(application);
        return CreateRestApplicationOutput.builder()
                .savedRestApplication(createdRestApplication)
                .build();
    }
}
