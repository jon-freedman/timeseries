Feature: Iteration

  Scenario: Can iterate simple time series
    Given an Integer time series with String key "foo"
    And Integer time series with String key "foo" contains 1 -> 1
    And Integer time series with String key "foo" contains 2 -> 2
    When sum is calculated
    Then result is 3