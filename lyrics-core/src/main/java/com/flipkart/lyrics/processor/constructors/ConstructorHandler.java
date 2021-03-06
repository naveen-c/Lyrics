/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.lyrics.processor.constructors;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.*;

import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getParameterTypeHandler;
import static com.flipkart.lyrics.helper.Helper.requiredParameterConstructorParadigm;

/**
 * Created by shrey.garg on 06/02/17.
 */
public abstract class ConstructorHandler extends Handler {

    public ConstructorHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        Map<String, FieldModel> fields = typeModel.getFields();

        List<String> constructorFields = getConstructorFields(typeModel);
        if (constructorFields.isEmpty()) {
            return;
        }

        MethodSpec.Builder constructor = MethodSpec.constructorBuilder();
        if (getModifier() != null) {
            constructor = constructor.addModifiers(getModifier());
        }

        CodeBlock superArgs = superArgs();
        if (superArgs != null) {
            Object[] args = new Object[superArgs.arguments.size()];
            args = superArgs.arguments.toArray(args);
            constructor.addCode(superIdentifier() + "." + String.join("", superArgs.formats), args);
        }

        for (String field : constructorFields) {
            ParameterSpec.Builder parameterSpec = getParameterTypeHandler(fields.get(field).getFieldType(), tune.getParameterTypeHandlerSet())
                    .process(typeSpec, field, fields.get(field));

            if (!fields.get(field).isPrimitive()) {
                tune.getValidationAnnotatorStyles().forEach(style -> requiredParameterConstructorParadigm.accept(style, fields.get(field), parameterSpec));
            }

            parameterSpec.required(fields.get(field).isRequired());
            constructor.addParameter(parameterSpec.build());
            constructor.addStatement("$N.$L = $L", selfReference(), field, field);
        }

        final MethodSpec.Builder finalConstructor = constructor;
        tune.getAnnotatorStyles().forEach(style -> style.processConstructorOrderRule(finalConstructor, typeModel, constructorFields));
        typeSpec.addMethod(finalConstructor.build());
    }


    protected abstract List<String> getConstructorFields(TypeModel typeModel);

    protected abstract Modifier getModifier();

    protected String selfReference() {
        return "this";
    }

    protected String superIdentifier() {
        return "super";
    }

    protected CodeBlock superArgs() {
        return null;
    }
}
