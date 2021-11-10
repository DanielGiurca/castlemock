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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.service.mock.soap.project.input.DeleteSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class DeleteSoapProjectService extends AbstractSoapProjectService {

    public DeleteSoapProjectOutput process(DeleteSoapProjectInput input) {
        final String soapProjectId = input.getProjectId();
        final SoapProject project = this.deleteProject(soapProjectId);
        return DeleteSoapProjectOutput.builder()
                .project(project)
                .build();
    }
}
