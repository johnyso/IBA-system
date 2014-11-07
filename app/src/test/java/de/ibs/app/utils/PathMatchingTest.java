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
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.net.Uri;

@RunWith(RobolectricTestRunner.class)
public class PathMatchingTest {
	private static final String SCHEME = "content://";
	private static final String AUTHORITY = "de.jsauerwein/";
	private AdvancedUriMatcher uriMatcher;
	
	@Before
	public void setUpFixture() {
		uriMatcher = new AdvancedUriMatcher();
	}
	
	@Test
	public void shouldNotMatchAnythingWithoutAnAddedUri() {
		Uri uri = Uri.parse(SCHEME + AUTHORITY);
		int match = this.uriMatcher.match(uri).getCode();
		
		assertEquals(AdvancedUriMatcher.Match.NO_MATCH, match);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnNegativeCode() {
		String path = "simplePath";
		int code = -1;

		this.uriMatcher.addURI(AUTHORITY, path, code);
	}
	
	@Test
	public void shouldMatchAddedSimplePath() {
		String path = "simplePath";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}
	
	@Test
	public void shouldNotMatchWrongAuthority() {
		String path = "simplePath";
		String otherAuthority = "de.netsss/";
		int code = 0;
		this.uriMatcher.addURI(otherAuthority, path, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(AdvancedUriMatcher.Match.NO_MATCH, match);
	}
	
	@Test
	public void shouldMatchTheRightPathByAddingTwo() {
		String path = "simplePath";
		String otherPath = "otherPath";
		int code = 0;
		int otherCode = 1;
		this.uriMatcher.addURI(AUTHORITY, path, code);
		this.uriMatcher.addURI(AUTHORITY, otherPath, otherCode);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}
	
	@Test
	public void shouldMatchTheRightPathByAddingTwoInReverseOrder() {
		String path = "simplePath";
		String otherPath = "otherPath";
		int code = 0;
		int otherCode = 1;
		this.uriMatcher.addURI(AUTHORITY, otherPath, otherCode);
		this.uriMatcher.addURI(AUTHORITY, path, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}
	
	@Test
	public void simplePathShouldNotMatchPathWithTwoSegments() {
		String path = "simplePath";
		String longerPath = "simplePath/andmore";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, longerPath, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(AdvancedUriMatcher.Match.NO_MATCH, match);
	}
	
	@Test
	public void shouldNotConfuseTrailingSlashes() {
		String path = "simplePath";
		String longerPath = "simplePath/";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, longerPath, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}

	
	@Test
	public void shouldNotConfuseMissingSlashes() {
		String path = "simplePath";
		String authority = "de.jsauerwein";
		int code = 0;
		this.uriMatcher.addURI(authority, path, code);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}
	
	@Test
	public void shouldMatchWithDifferentAuthorities() {
		String path = "simplePath";
		String otherAuthority = "de.netsss/";
		int code = 0;
		int otherCode = 1;
		this.uriMatcher.addURI(AUTHORITY, path, code);
		this.uriMatcher.addURI(otherAuthority, path, otherCode);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(code, match);
	}
	
	@Test
	public void shouldMatchIntermediatePathAddedLaterOn() {
		String path = "simplePath";
		String longerPath = "simplePath/andmore";
		int code = 0;
		int otherCode = 1;
		this.uriMatcher.addURI(AUTHORITY, longerPath, code);
		this.uriMatcher.addURI(AUTHORITY, path, otherCode);
		
		Uri uri = Uri.parse(SCHEME + AUTHORITY + path);
		int match = this.uriMatcher.match(uri).getCode();

		assertEquals(otherCode, match);
	}
	
	@Test
	public void shouldContainCapturings() {
		String path = "simplePath";
		String matchingPath = "simplePath";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		assertEquals(2, capturings.size());
	}

	@Test
	public void shouldContainMoreCapturings() {
		String path = "simplePath/andmore";
		String matchingPath = "simplePath/andmore";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		assertEquals(3, capturings.size());
	}

	@Test
	public void shouldContainMoreEmptyCapturings() {
		String path = "simplePath/and/more";
		String matchingPath = "simplePath/and/more";
		int code = 0;
		this.uriMatcher.addURI(AUTHORITY, path, code);

		Uri uri = Uri.parse(SCHEME + AUTHORITY + matchingPath);
		List<String> capturings = this.uriMatcher.match(uri).getCapturings();
		for(String capturing : capturings) {
			assertEquals("", capturing);
		}
	}
}
