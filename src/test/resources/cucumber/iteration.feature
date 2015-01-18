Feature: Iteration

  Scenario: Can iterate simple time series
    Given a time series
    And time series contains 1
    And time series contains 2
    When sum is calculated
    Then result is 3