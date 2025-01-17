/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.controller.resource;

import com.castlemock.service.mock.soap.project.LoadSoapResourceService;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import com.castlemock.web.core.controller.rest.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource/soap/project")
public class ResourceController extends AbstractRestController {

    private final LoadSoapResourceService loadSoapResourceService;

    @Autowired
    public ResourceController(LoadSoapResourceService loadSoapResourceService){

        this.loadSoapResourceService = loadSoapResourceService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}/resource/{resourceId}")
    public String getResource(@PathVariable final String projectId,
                              @PathVariable final String resourceId) {
        final LoadSoapResourceOutput output =
                this.loadSoapResourceService.process(LoadSoapResourceInput.builder()
                        .projectId(projectId)
                        .resourceId(resourceId)
                        .build());
        return output.getResource();
    }

}
