package com.github.jonfreedman.timeseries.matchers

import java.util
import java.util.Map.Entry

import org.hamcrest.{Description, Matcher, TypeSafeMatcher}

/**
 * @author jon
 */
class EntryMatcher[K, V] private(keyMatcher: Matcher[_ <: K], valueMatcher: Matcher[_ <: V]) extends TypeSafeMatcher[util.Map.Entry[K, V]] {
  override def matchesSafely(item: Entry[K, V]): Boolean = keyMatcher.matches(item.getKey) && valueMatcher.matches(item.getValue)

  override def describeTo(description: Description) {
    description.appendText("Entry[key: ")
    keyMatcher.describeTo(description)
    description.appendText(" value: ")
    valueMatcher.describeTo(description)
    description.appendText("]")
  }
}

object EntryMatcher {
  def entry[K, V](keyMatcher: Matcher[_ <: K], valueMatcher: Matcher[_ <: V]): EntryMatcher[K, V] =
    new EntryMatcher(keyMatcher, valueMatcher)
}