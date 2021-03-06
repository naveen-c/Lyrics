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

package com.flipkart.lyrics.annotators;

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.TypeSpec;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 07/06/17.
 */
public class GsonStyleTest {

    @Test
    public void testNamedAsRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getNamedAs()).thenReturn("TEST");

        new GsonStyle().processNamedAsRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(2, fieldSpec.annotations.size());

        AnnotationSpec namedAsAnnotation = fieldSpec.annotations.get(0);
        assertEquals(1, namedAsAnnotation.members.size());
    }

    @Test
    public void testInclusionRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new GsonStyle().processInclusionRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(0, fieldSpec.annotations.size());
    }

    @Test
    public void testGlobalInclusionRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new GsonStyle().processGlobalInclusionRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(0, typeSpec.annotations.size());
    }

    @Test
    public void testSubTypeRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);

        Map<String, String> subTypes = new HashMap<>();
        subTypes.put("A", "com.flipkart.lyrics.A");
        subTypes.put("B", "com.flipkart.lyrics.B");
        SubTypeModel subTypeModel = new SubTypeModel("one", subTypes);
        when(model.getSubTypes()).thenReturn(subTypeModel);

        new GsonStyle().processSubTypeRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(0, typeSpec.annotations.size());
    }

    @Test
    public void testPropertyOrderRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getFieldOrder()).thenReturn(Arrays.asList("one", "two"));

        new GsonStyle().processPropertyOrderRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(0, typeSpec.annotations.size());
    }
}