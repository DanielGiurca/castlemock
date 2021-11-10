/*
 * Copyright 2021 Karl Dahlgren
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

package com.castlemock.service.core.expression;

import com.castlemock.model.core.utility.parser.ExternalInputBuilder;
import com.castlemock.model.core.utility.parser.TextParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.service.core.configuration.AbstractConfigurationGroupService;
import com.castlemock.service.core.expression.input.ValidateExpressionInput;
import com.castlemock.service.core.expression.output.ValidateExpressionOutput;

import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.55
 */
@org.springframework.stereotype.Service
public class ValidateExpressionService extends AbstractConfigurationGroupService {

    public ValidateExpressionOutput process(final ValidateExpressionInput input) {
        final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder()
                .requestBody(input.getRequestBody())
                .build();
        final String output = new TextParser().parse(input.getResponseBody(), externalInput);
        return ValidateExpressionOutput.builder()
                .output(output)
                .build();
    }

}
