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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcher {

	private final String segment;
	private int code;
	private final LinkedList<PathMatcher> pathHierarchy;

	public PathMatcher(List<String> path, int code) {
		String segment = path.get(0);
		if(!("*".equals(segment)) && segment.contains("*")) {
			throw new IllegalArgumentException("The any placeholder (*) must not be placed within other characters.");
		}
		this.segment = path.get(0);
		this.code = path.size() != 1 ? AdvancedUriMatcher.Match.NO_MATCH : code;
		this.pathHierarchy = new LinkedList<PathMatcher>();
		this.addUri(path, code);
	}

	public AdvancedUriMatcher.Match match(List<String> pathSegments) {
		String match;
		if((match = this.matchSegment(pathSegments.get(0))) != null) {
			if(pathSegments.size() > 1) {
				List<String> subPathSegments = pathSegments.subList(1, pathSegments.size());
				Iterator<PathMatcher> iMatch = this.pathHierarchy.iterator();
				AdvancedUriMatcher.Match result = new AdvancedUriMatcher.Match(AdvancedUriMatcher.Match.NO_MATCH);
				while(result.getCode() == AdvancedUriMatcher.Match.NO_MATCH && iMatch.hasNext()) {
					PathMatcher matcher = iMatch.next();
					result = matcher.match(subPathSegments);
				}
				
				if(result.getCode() != AdvancedUriMatcher.Match.NO_MATCH) {
					result.addCapturedSegment(match);
				}
				
				return result;
			} else {
				AdvancedUriMatcher.Match result = new AdvancedUriMatcher.Match(this.code);
				result.addCapturedSegment(match);
				return result;
			}
		} else {
			return new AdvancedUriMatcher.Match(AdvancedUriMatcher.Match.NO_MATCH);
		}
	}
	
	private String matchSegment(String segment) {
		if(this.segment.equals(segment)) {
			return "";
		}
		
		if("*".equals(this.segment)) {
			return segment;
		}
		
		if(this.segment.contains("#")) {
			int index = this.segment.indexOf("#");
			String leading = this.segment.substring(0, index);
			leading = leading.replace("(", "\\(");
			String trailing = this.segment.substring(index + 1, this.segment.length());
			trailing = trailing.replace(")", "\\)");
			Pattern numeric = Pattern.compile(leading + "(\\d+)" + trailing);
			Matcher comparison = numeric.matcher(segment);
			if (comparison.matches()) {
				return comparison.group(1);
			}
		}
		
		return null;
	}

	public boolean addUri(List<String> path, int code) {
		if(!this.segment.equals(path.get(0))) {
			return false;
		}
		
		if(path.size() > 1) {
			path.remove(0);
			if(this.pathHierarchy.isEmpty()) {
				this.pathHierarchy.add(new PathMatcher(path, code));
			} else {
				boolean added = false;
				Iterator<PathMatcher> iMatcher = this.pathHierarchy.iterator();
				while(!added && iMatcher.hasNext()) {
					PathMatcher matcher = iMatcher.next();
					added = matcher.addUri(path, code);
				}
				
				if(!added) {
					this.pathHierarchy.add(new PathMatcher(path, code));
				}
			}
		} else {
			if(this.code == AdvancedUriMatcher.Match.NO_MATCH) {
				this.code = code;
			}
		}
		
		return true;
	}

}
