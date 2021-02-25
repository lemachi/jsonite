package com.mictale.jsonite;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class JsonTest {

    @Test
	public void testDate() {
		long largestInteger = 9007199254740992L;
		Date date = new Date(largestInteger);
		String str = date.toString();
		assertNotNull(str);
	}
}
