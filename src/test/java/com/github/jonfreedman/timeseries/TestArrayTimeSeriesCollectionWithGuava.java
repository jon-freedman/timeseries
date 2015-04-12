package com.github.jonfreedman.timeseries;

import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper;
import com.google.common.collect.testing.IteratorTestSuiteBuilder;
import com.google.common.collect.testing.SortedSetTestSuiteBuilder;
import com.google.common.collect.testing.TestIteratorGenerator;
import com.google.common.collect.testing.TestStringSortedSetGenerator;
import com.google.common.collect.testing.features.CollectionSize;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 * @author jon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestArrayTimeSeriesCollectionWithGuava.GuavaTests.class
})
public class TestArrayTimeSeriesCollectionWithGuava {
    @RunWith(AllTests.class)
    public static class GuavaTests {
        public static TestSuite suite() {
            final TestSuite suite = new TestSuite();
            suite.addTest(new IteratorTestSuiteBuilderWrapper<Map.Entry<String, TimeSeries<LocalDate, Double>>>()
                    .named("ArrayTimeSeriesCollection Iteration Tests")
                    .createTestSuite());
            suite.addTest(SortedSetTestSuiteBuilder.using(new TestStringSortedSetGenerator() {
                @Override
                protected SortedSet<String> create(String[] elements) {
                    if (elements.length == 0) {
                        return Collections.unmodifiableSortedSet(Collections.emptySortedSet());
                    }
                    final LocalDate d = LocalDate.of(2015, 4, 5);
                    ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder builder = new ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder();
                    for (String element : elements) {
                        builder = builder.addValue(element, d, 1);
                    }
                    return builder.build().keySet();
                }
            })
                    .named("ArrayTimeSeriesCollection KeySet Tests")
                    .withFeatures(
                            CollectionSize.ONE,
                            CollectionSize.SEVERAL
                    )
                    .createTestSuite());
            return suite;
        }

        private static class IteratorTestSuiteBuilderWrapper<E> extends IteratorTestSuiteBuilder<E> {
            public IteratorTestSuiteBuilderWrapper() {
                usingGenerator(new TestIteratorGenerator<Map.Entry<String, TimeSeries<LocalDate, Double>>>() {
                    @Override
                    public Iterator<Map.Entry<String, TimeSeries<LocalDate, Double>>> get() {
                        final LocalDate d = LocalDate.of(2015, 4, 5);
                        return new ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder().addValue("foo", d, 1).build().iterator();
                    }
                });
            }
        }
    }
}
