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
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.input.UpdateRestApplicationsForwardedEndpointInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestApplicationsForwardedEndpointOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestApplicationsForwardedEndpointService extends AbstractRestProjectService {

    public UpdateRestApplicationsForwardedEndpointOutput process(UpdateRestApplicationsForwardedEndpointInput input) {
        for(String applicationIds : input.getApplicationIds()){
            List<RestResource> resources = this.resourceRepository.findWithApplicationId(applicationIds);
            for(RestResource restResource : resources){
                List<RestMethod> methods = this.methodRepository.findWithResourceId(restResource.getId());
                for(RestMethod restMethod : methods){
                    restMethod.setForwardedEndpoint(input.getForwardedEndpoint());
                    this.methodRepository.update(restMethod.getId(), restMethod);
                }
            }
        }
        return UpdateRestApplicationsForwardedEndpointOutput.builder().build();
    }
}
