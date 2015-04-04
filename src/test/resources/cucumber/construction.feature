Feature: Construction

  @ignore
  Scenario Outline: Time series collection knows min and max time values
    Given a LocalDateTimeSeriesCollection with start date 2014-01-26 and end date 2015-01-25
    Then <stat> date is <date>

  Examples:
    | stat | date       |
    | min  | 2014-01-26 |
    | max  | 2015-01-25 |