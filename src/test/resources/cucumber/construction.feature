Feature: Construction

  Scenario Outline: TimeSeriesCollection knows min and max time values
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2015-01-25' -> 2)
    Then the <stat> date is '<date>'

  Examples:
    | stat | date       |
    | min  | 2014-01-26 |
    | max  | 2015-01-25 |

  Scenario: TimeSeriesCollection can store multiple keys
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'bar' of ('2014-01-26' -> 2)
    Then there are 2 keys
    And keys contains 'foo'
    And keys contains 'bar'

  Scenario: TimeSeriesCollection can be iterated
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    Then TimeSeries for key 'foo' contains [1, 2, 3]

  Scenario: TimeSeriesCollection can be iterated with time values
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    Then TimeSeries for key 'foo' contains values [1, 2, 3] and timeValues ['2014-01-26', '2014-01-27', '2014-01-28']

  Scenario: TimeSeriesCollection has a length corresponding to the number of time values
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    Then length is 3

  Scenario Outline: TimeSeriesCollection can be filtered
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-28' -> 3)
    And value for key 'bar' of ('2014-01-26' -> -1)
    And value for key 'bar' of ('2014-01-27' -> -2)
    When collection is filtered with keys = ['<key>']
    Then there are 1 keys
    And keys contains '<key>'
    And length is 3

  Examples:
    | key |
    | foo |
    | bar |

  Scenario: TimeSeriesCollection can collate values
    Given a LocalDate ArrayTimeSeriesCollection
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-27' -> 2)
    And value for key 'foo' of ('2014-01-26' -> 3)
    Then TimeSeries for key 'foo' contains [4, 2]

  Scenario: TimeSeriesCollection can be built with null value interpolator
    Given a LocalDate ArrayTimeSeriesCollection
    And collection uses null value interpolation
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-28' -> 3)
    Then TimeSeries for key 'foo' contains [1, null, 3]

  Scenario: TimeSeriesCollection can be built with interpolator
    Given a LocalDate ArrayTimeSeriesCollection
    And collection uses linear interpolation
    And value for key 'foo' of ('2014-01-26' -> 1)
    And value for key 'foo' of ('2014-01-28' -> 3)
    Then TimeSeries for key 'foo' contains [1, 2, 3]

