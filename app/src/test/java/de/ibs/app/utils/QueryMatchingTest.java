/*
 * Copyright (c) 2013, Jan Sauerwein
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the 
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following 
 * disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the 
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote 
 * products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.ibs.app.utils;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import android.net.Uri;

@RunWith(RobolectricTestRunner.class)
public class QueryMatchingTest {
	
	private static final String SCHEME = "content://";
	private static final String AUTHORITY = "de.jsauerwein/";
	private static final String PATH = "simplePath";
	private AdvancedUriMatcher uriMatcher;
	
	@Before
	public void setUpFixture() {
		this.uriMatcher = new AdvancedUriMatcher();
	}
	
	@Test
	public void shouldMatchEvenIfQueryPresent() {
		String query = "?key=value";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);

		assertEquals(code, match.getCode());
	}
	
	@Test
	public void shouldProvideValueForQueryKey() {
		String query = "?key=value eq 5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);

		assertEquals("value eq 5", match.getQuery("key").get(0));
	}
	
	@Test
	public void shouldProvideNoQueryIfThereIsNoMatch() {
		String mismatchPath = "pathNotMatching";
		String query = "?key=value";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + mismatchPath + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);

		assertNull(match.getQuery("key"));
	}
	
	@Test
	public void shouldParseEqIfKeyStartsWithDollar() {
		String query = "?$key=value eq 5&key=value";
		String parsedValue = "value=5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}
	
	@Test
	public void shouldParseGtIfKeyStartsWithDollar() {
		String query = "?$key=value gt 5&key=value";
		String parsedValue = "value>5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}
	
	@Test
	public void shouldParseLtIfKeyStartsWithDollar() {
		String query = "?$key=value lt 5&key=value";
		String parsedValue = "value<5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}
	
	@Test
	public void shouldParseGeIfKeyStartsWithDollar() {
		String query = "?$key=value ge 5&key=value";
		String parsedValue = "value>=5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}
	
	@Test
	public void shouldParseLeIfKeyStartsWithDollar() {
		String query = "?$key=value le 5&key=value";
		String parsedValue = "value<=5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}
	
	@Test
	public void shouldParseNeIfKeyStartsWithDollar() {
		String query = "?$key=value ne 5&key=value";
		String parsedValue = "value<>5";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}

	@Test
	public void shouldParseAndIfKeyStartsWithDollar() {
		String query = "?$key=value ne 5 and other eq 7&key=value";
		String parsedValue = "value<>5 AND other=7";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}

	@Test
	public void shouldParseOrIfKeyStartsWithDollar() {
		String query = "?$key=value ne 5 or other eq 7&key=value";
		String parsedValue = "value<>5 OR other=7";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		assertEquals(parsedValue, match.getQuery("$key").get(0));
	}

	@Test
	public void shouldHandleMultipleKeyOccurances() {
		String query = "?$key=value&$key=otherValue";
		String[] parsedValues = new String[]{"value", "otherValue"};
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		List<String> values = match.getQuery("$key");
		for(int i = 0; i < parsedValues.length; i++) {
			assertEquals(parsedValues[i], values.get(i));
		}
	}

	@Test
	public void shouldAddEmtpyValueForLonleyKey() {
		String query = "?key";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, PATH, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + PATH + query);
		AdvancedUriMatcher.Match match = this.uriMatcher.match(uri);
		
		List<String> values = match.getQuery("key");
		assertEquals("", values.get(0));
	}
}
