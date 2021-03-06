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

package com.flipkart.lyrics.annotators.validations;

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.ParameterSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by shrey.garg on 07/06/17.
 */
public class Jsr303StyleTest {

    @Test
    public void testRequiredRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processRequiredRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotations.size());
    }

    @Test
    public void processRequiredRuleForGetters() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processRequiredRuleForGetters(builder, model);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(1, methodSpec.annotations.size());
    }

    @Test
    public void processRequiredRuleForSetters() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processRequiredRuleForSetters(builder, model, parameterBuilder);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(0, methodSpec.annotations.size());

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.annotations.size());
    }

    @Test
    public void processNotRequiredRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processNotRequiredRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(0, fieldSpec.annotations.size());
    }

    @Test
    public void processNotRequiredRuleForGetters() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processNotRequiredRuleForGetters(builder, model);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(0, methodSpec.annotations.size());
    }

    @Test
    public void processNotRequiredRuleForSetters() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");
        FieldModel model = mock(FieldModel.class);

        new Jsr303Style().processNotRequiredRuleForSetters(builder, model, parameterBuilder);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(0, methodSpec.annotations.size());

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(0, parameterSpec.annotations.size());
    }

    @Test
    public void processRequiredRuleForConstructor() {
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");

        new Jsr303Style().processRequiredRuleForConstructor(parameterBuilder);

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.annotations.size());
    }

    @Test
    public void processNotRequiredRuleForConstructor() {
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");

        new Jsr303Style().processNotRequiredRuleForConstructor(parameterBuilder);

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(0, parameterSpec.annotations.size());
    }
}