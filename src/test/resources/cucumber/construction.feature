Feature: Construction

  Scenario Outline: TimeSeriesCollection knows min and max time values
    Given a LocalDate ArrayTimeSeriesCollection with start date '2014-01-26' and end date '2015-01-25'
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2015-01-25' -> 2)
    Then the <stat> date is '<date>'

  Examples:
    | stat | date       |
    | min  | 2014-01-26 |
    | max  | 2015-01-25 |

  Scenario: TimeSeriesCollection can store multiple keys
    Given a LocalDate ArrayTimeSeriesCollection with start date '2014-01-26' and end date '2014-01-26'
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'bar' of ('2014-01-26' -> 2)
    Then keys contains 'foo'
    And keys contains 'bar'