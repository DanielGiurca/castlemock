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
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;

import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestProjectService extends AbstractRestProjectService {

    public ReadRestProjectOutput process(ReadRestProjectInput input) {
        final RestProject restProject = find(input.getRestProjectId());
        final List<RestApplication> applications = this.applicationRepository.findWithProjectId(input.getRestProjectId());
        restProject.setApplications(applications);
        for(final RestApplication restApplication : restProject.getApplications()){
            final Map<RestMethodStatus, Integer> soapOperationStatusCount = getRestMethodStatusCount(restApplication);
            restApplication.setStatusCount(soapOperationStatusCount);
        }
        return ReadRestProjectOutput.builder()
                .restProject(restProject)
                .build();
    }
}
