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

import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.mock.rest.project.input.CreateRestProjectInput;
import com.castlemock.service.mock.rest.project.output.CreateRestProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestProjectService extends AbstractRestProjectService {

    public CreateRestProjectOutput process(CreateRestProjectInput input) {
        final RestProject restProject = input.getRestProject();
        final RestProject savedRestProject = save(restProject);
        return CreateRestProjectOutput.builder()
                .savedRestApplication(savedRestProject)
                .build();
    }
}
