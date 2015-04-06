Feature: Calculation

  Scenario: Can iterate simple time series
    Given a LocalDate ArrayTimeSeriesCollection with start date '2014-01-26' and end date '2014-01-28'
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    When sum is calculated
    Then BigDecimal result for 'foo' is 6