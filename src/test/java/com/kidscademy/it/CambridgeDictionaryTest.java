package com.kidscademy.it;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.kidscademy.www.CambridgeDictionary;

import js.core.AppContext;
import js.unit.TestContext;

public class CambridgeDictionaryTest {
    private static final String DESCRIPTOR = "" + //
	    "<app-descriptor>" + //
	    "	<managed-classes>" + //
	    "		<cambridge-dictionary interface='com.kidscademy.www.CambridgeDictionary' type='REMOTE' url='https:xpath://dictionary.cambridge.org/' />"
	    + //
	    "	</managed-classes>" + //
	    "</app-descriptor>";

    private CambridgeDictionary dictionary;

    @Before
    public void beforeTest() throws Exception {
	AppContext context = TestContext.start(DESCRIPTOR);
	dictionary = context.getInstance(CambridgeDictionary.class);
    }

    @Test
    public void getDefinition() {
	String definition = dictionary.getDefinition("accordion");
	assertThat(definition, notNullValue());
	assertThat(definition, startsWith("a box-shaped musical instrument consisting of a folded central part"));
    }
}
