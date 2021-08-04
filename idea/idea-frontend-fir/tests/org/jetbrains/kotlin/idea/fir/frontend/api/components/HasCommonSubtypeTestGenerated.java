/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.fir.frontend.api.components;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link GenerateNewCompilerTests.kt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/idea-frontend-fir/testData/components/hasCommonSubtype")
@TestDataPath("$PROJECT_ROOT")
public class HasCommonSubtypeTestGenerated extends AbstractHasCommonSubtypeTest {
    @Test
    public void testAllFilesPresentInHasCommonSubtype() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/idea-frontend-fir/testData/components/hasCommonSubtype"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("collections.kt")
    public void testCollections() throws Exception {
        runTest("idea/idea-frontend-fir/testData/components/hasCommonSubtype/collections.kt");
    }

    @Test
    @TestMetadata("dataClasses.kt")
    public void testDataClasses() throws Exception {
        runTest("idea/idea-frontend-fir/testData/components/hasCommonSubtype/dataClasses.kt");
    }

    @Test
    @TestMetadata("enums.kt")
    public void testEnums() throws Exception {
        runTest("idea/idea-frontend-fir/testData/components/hasCommonSubtype/enums.kt");
    }

    @Test
    @TestMetadata("simple.kt")
    public void testSimple() throws Exception {
        runTest("idea/idea-frontend-fir/testData/components/hasCommonSubtype/simple.kt");
    }
}
