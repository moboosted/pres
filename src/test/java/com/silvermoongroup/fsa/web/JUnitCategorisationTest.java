/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web;

import com.silvermoongroup.base.junit.categories.CategorisationChecker;
import com.silvermoongroup.base.junit.categories.UnitTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTests.class)
public class JUnitCategorisationTest {

    @Test
    public void ensureAllTestClassesAreCategorised() {
        Iterable<String> uncatergorisedCLasses = CategorisationChecker
                .checkTestAnnotatatedWithCategories("com.silvermoongroup.fsa.web");

        if (uncatergorisedCLasses != null && uncatergorisedCLasses.iterator().hasNext()) {
            Assert.fail("The following classes are missing the @Category annotation " + uncatergorisedCLasses);
        }

    }

}
