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

package com.flipkart.lyrics.styles.objectmethods;

import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.model.MetaInfo;

import java.util.List;

/**
 * Created by shrey.garg on 25/01/17.
 */
public abstract class ObjectMethodsStyle {

    public abstract void processToString(MethodSpec.Builder toStringBuilder, List<String> nonStaticFields, MetaInfo metaInfo);

    public abstract void processEqualsAndHashCode(MethodSpec.Builder equalsBuilder, MethodSpec.Builder hashCodeBuilder, List<String> nonStaticFields, MetaInfo metaInfo, boolean testSuperEquality);
}
