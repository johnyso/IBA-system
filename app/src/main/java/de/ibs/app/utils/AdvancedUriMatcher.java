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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;

/**
 * This class is a more advanced version of the android.content.UriMatcher.
 * Besides supporting nested numeric placeholders it also supports some basic
 * parsing of OData queries.
 * 
 * Instead of returning a simple integer the nested class Match is returned.
 * This class contains all the results collected during the match.
 * 
 * @author Jan Sauerwein
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public final class AdvancedUriMatcher {
	
	/**
	 * This class provides the code of the matching path, the captured numbers
	 * and strings of the placeholders within every path segment and the
	 * key-value-pairs of the query. If the key has been started with a $ sign
	 * the values are converted from ODATA notation to SQL.
	 * 
	 * Keep in mind that the first segment is always the authority!
	 */
	public static class Match {
		public static final int NO_MATCH = -1;

		private final int code;
		private final List<String> capturings;
		private final HashMap<String, List<String>> queries;

		public Match(final int code) {
			this.code = code;
			this.capturings = new LinkedList<String>();
			this.queries = new HashMap<String, List<String>>();
		}

		public int getCode() {
			return this.code;
		}

		public List<String> getCapturings() {
			return this.capturings;
		}

		public void addCapturedSegment(String match) {
			this.capturings.add(0, match);
		}

		public List<String> getQuery(String key) {
			return this.queries.get(key);
		}

		public void addQuery(HashMap<String, List<String>> parsedQuery) {
			this.queries.putAll(parsedQuery);
		}
	}

	private final LinkedList<PathMatcher> pathHierarchy;
	
	public AdvancedUriMatcher() {
		this.pathHierarchy = new LinkedList<PathMatcher>();
	}
	
	/**
	 * Adds a new path to the matcher. If you add a path that is already covered
	 * by a path with placeholders this path will never be reached.
	 * 
	 * @param authority
	 *            the authority to match (will be the first segment to match)
	 * @param path
	 *            the path to match. * may be used as a wild card for any text,
	 *            and # may be used as a wild card for numbers even within
	 *            segments.
	 * @param code
	 *            the code that is returned when a URI is matched against the
	 *            given components. Must be positive.
	 */
	public void addURI(final String authority, final String path, final int code) {
		if(code < 0) {
			throw new IllegalArgumentException("code " + code + " is invalid: it must be positive");
		}
		
		List<String> authorityAndPath = composeAuthorityAndPath(authority, path);
		
		if(this.pathHierarchy.isEmpty()) {
			this.pathHierarchy.add(new PathMatcher(authorityAndPath, code));
		} else {
			boolean added = false;
			Iterator<PathMatcher> iMatcher = this.pathHierarchy.iterator();
			while(!added && iMatcher.hasNext()) {
				PathMatcher matcher = iMatcher.next();
				added = matcher.addUri(authorityAndPath, code);
			}
			
			if(!added) {
				this.pathHierarchy.add(new PathMatcher(authorityAndPath, code));
			}
		}
	}

	private List<String> composeAuthorityAndPath(final String authority,
			final String path) {
		StringBuffer authorityAndPath = new StringBuffer(authority);
		if(!authority.endsWith("/")) {
			authorityAndPath.append("/");
		}
		authorityAndPath.append(path.endsWith("/") ? path.substring(0, path.length() - 1) : path);
		
		return new LinkedList<String>(Arrays.asList(authorityAndPath.toString().split("/")));
	}

	/**
	 * Try to match against the path in a url.
	 * 
	 * @param uri
	 *            The url whose path we will match against.
	 * @return A Match object with all collected results during the match or a
	 *         Match object with code -1, if there is not matched node.
	 */
	public Match match(Uri uri) {
		final List<String> pathSegments = new LinkedList<String>(uri.getPathSegments());
		pathSegments.add(0, uri.getAuthority());

		Match result;
		for(PathMatcher matcher : this.pathHierarchy) {
			result = matcher.match(pathSegments);
			if(result.getCode() >= 0) {
				this.applyQueryToMatch(result, uri);
				return result;
			}
		}

		return new Match(Match.NO_MATCH);
	}

	private void applyQueryToMatch(Match result, Uri uri) {
		String query = uri.getQuery();
		if (query != null) {
			String[] pairs = query.split("&");
			HashMap<String, List<String>> parsedQuery = new HashMap<String, List<String>>();
			for (String pair : pairs) {
				if (pair.contains("=")) {
					int index = pair.indexOf("=");
					String key = pair.substring(0, index);
					String value = pair.substring(index + 1);
					if(key.startsWith("$")) {
						value = value.replace(" eq ", "=");
						value = value.replace(" gt ", ">");
						value = value.replace(" lt ", "<");
						value = value.replace(" ge ", ">=");
						value = value.replace(" le ", "<=");
						value = value.replace(" ne ", "<>");
						value = value.replace(" and ", " AND ");
						value = value.replace(" or ", " OR ");
					}
					List<String> prevValues = parsedQuery.get(key);
					if(prevValues != null) {
						prevValues.add(value);
					} else {
						prevValues = new LinkedList<String>();
						prevValues.add(value);
						parsedQuery.put(key, prevValues);
					}
				} else {
					List<String> prevValues = parsedQuery.get(pair);
					if(prevValues != null) {
						prevValues.add("");
					} else {
						prevValues = new LinkedList<String>();
						prevValues.add("");
						parsedQuery.put(pair, prevValues);
					}
				}
			}

			result.addQuery(parsedQuery);
		}
	}
}
