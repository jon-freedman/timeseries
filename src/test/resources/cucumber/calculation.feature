Feature: Calculation

  Scenario: Can iterate simple time series
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    When sum is calculated
    And max is calculated
    Then BigDecimal result for 'foo' is 6
    And Observation result for 'foo' is ('2014-01-28' -> 3)

  Scenario: Can iterate time series with zero value interpolation
    Given a LocalDate ArrayTimeSeriesCollection
    And collection uses zero value interpolation
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-02-01' -> 2)
    And value for key 'bar' of ('2014-01-27' -> 1)
    And value for key 'bar' of ('2014-01-29' -> 2)
    And value for key 'bar' of ('2014-01-31' -> 3)
    When sum is calculated
    Then BigDecimal result for 'foo' is 3
    Then BigDecimal result for 'bar' is 6