package de.ibs.app.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by macio GmbH on 01.08.2014.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AdvancedUriMatcherTests.class, PathMatchingTest.class, PathPlaceholderMatchingTest.class,
        QueryMatchingTest.class})
public class UtilsTestSuit {
}
