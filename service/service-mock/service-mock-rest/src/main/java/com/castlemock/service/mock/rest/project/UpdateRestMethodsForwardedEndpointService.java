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

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestMethodsForwardedEndpointOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestMethodsForwardedEndpointService extends AbstractRestProjectService {

    public UpdateRestMethodsForwardedEndpointOutput process(UpdateRestMethodsForwardedEndpointInput input) {
        for(String methodId : input.getMethodIds()){
            final RestMethod restMethod = this.methodRepository.findOne(methodId);
            restMethod.setForwardedEndpoint(input.getForwardedEndpoint());
            this.methodRepository.update(restMethod.getId(), restMethod);
        }
        return UpdateRestMethodsForwardedEndpointOutput.builder().build();
    }
}
