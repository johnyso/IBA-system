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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.net.Uri;

@RunWith(RobolectricTestRunner.class)
public class PathPlaceholderMatchingTest {
	private static final String SCHEME = "content://";
	private static final String AUTHORITY = "de.jsauerwein/";
	private AdvancedUriMatcher uriMatcher;
	
	@Before
	public void setUpFixture() {
		this.uriMatcher = new AdvancedUriMatcher();
	}
	
	@Test
	public void shouldMatchPlaceholderPath() {
		String path = "*";
		String matchingPath = "matchingPath";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);
	}

	@Test
	public void shouldIgnorePathsAfterMatchingPlaceholder() {
		String matchingPath = "matchingPath/andmore";
		String path = "*/andmore";
		String unreachablePath = "wrong/andmore"; 
		int code = 0;
		int otherCode = 1;
		int unreachableCode = 2;
		this.uriMatcher.addURI(AUTHORITY, matchingPath, code);
		this.uriMatcher.addURI(AUTHORITY, path, otherCode);
		this.uriMatcher.addURI(AUTHORITY, unreachablePath, unreachableCode);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);

		uri = Uri.parse(SCHEME + AUTHORITY + unreachablePath);
		match = this.uriMatcher.match(uri).getCode();
		assertEquals(otherCode, match);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnMisplacedPlaceholder() {
		String path = "simple*Path";
		int code = 0;

		this.uriMatcher.addURI(AUTHORITY, path, code);
	}
	
	@Test
	public void shouldMatchNumericPlaceholderPath() {
		String path = "#";
		String matchingPath = "12345679";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);
	}
	
	@Test
	public void shouldMatchTrailingNumericPlaceholder() {
		String path = "path-#";
		String matchingPath = "path-12345679";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);
	}	
	
	@Test
	public void shouldMatchLeadingNumericPlaceholder() {
		String path = "#-path";
		String matchingPath = "97654321-path";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);
	}
	
	@Test
	public void shouldMatchEmbeddedNumericPlaceholderWithParenthesis() {
		String path = "path(#)";
		String matchingPath = "path(3)";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		int match = this.uriMatcher.match(uri).getCode();
		assertEquals(code, match);
	}
	
	@Test
	public void shouldContainFullSegment() {
		String path = "*";
		String matchingPath = "simplePath";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		assertEquals(matchingPath, capturings.get(1));
	}
	
	@Test
	public void shouldContainNumber() {
		String path = "simplePath(#)";
		String matchingPath = "simplePath(3)";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		assertEquals("3", capturings.get(1));
	}
	
	@Test
	public void shouldContainCapturingValues() {
		String path = "simplePath(#)/*/and/more/#";
		String matchingPath = "simplePath(3)/something/and/more/666";
		@SuppressWarnings("unchecked")
		List<String> expectedCapturings = Arrays.asList(new String[]{"", "3", "something", "", "", "666"});
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		for(int i = 0; i < expectedCapturings.size(); i++) {
			assertEquals(expectedCapturings.get(i), capturings.get(i));
		}
	}}
