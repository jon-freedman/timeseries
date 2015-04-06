Feature: Iteration

  Scenario: Can traverse to next value
    Given a LocalDateTraverser with initial value '2015-04-04'
    When traversed to next
    Then the traversed value is '2015-04-05'

  Scenario: Can traverse to nth value
    Given a LocalDateTraverser with initial value '2015-04-04'
    When traversal skipped by 1
    Then the traversed value is '2015-04-06'

  Scenario: Can traverse over weekends
    Given a WeekdayLocalDateTraverser with initial value '2015-04-03'
    When traversed to next
    Then the traversed value is '2015-04-06'

  Scenario Outline: Can traverse to nth value over weekends
    Given a WeekdayLocalDateTraverser with initial value '<initial>'
    When traversal skipped by <skip>
    Then the traversed value is '<result>'

  Examples:
    | initial    | skip | result     |
    | 2015-04-01 | 1    | 2015-04-03 |
    | 2015-04-02 | 1    | 2015-04-06 |
    | 2015-04-03 | 1    | 2015-04-07 |
    | 2015-04-03 | 3    | 2015-04-09 |
    | 2015-04-03 | 4    | 2015-04-10 |
    | 2015-04-03 | 5    | 2015-04-13 |
    | 2015-04-03 | 6    | 2015-04-14 |
    | 2015-04-03 | 8    | 2015-04-16 |
    | 2015-04-03 | 9    | 2015-04-17 |
    | 2015-04-03 | 10   | 2015-04-20 |
    | 2015-04-03 | 11   | 2015-04-21 |
    | 2015-04-03 | 18   | 2015-04-30 |
    | 2015-04-03 | 19   | 2015-05-01 |
    | 2015-04-03 | 20   | 2015-05-04 |