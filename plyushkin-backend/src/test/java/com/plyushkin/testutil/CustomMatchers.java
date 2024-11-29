package com.plyushkin.testutil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;
import java.util.function.Predicate;

public class CustomMatchers {
    private CustomMatchers() {
        // no op
    }

    public static <T> Matcher<Collection<T>> containsBy(Predicate<T> predicate) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Collection<T> t) {
                return t.stream().anyMatch(predicate);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
