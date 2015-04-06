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
    Given a WeekdayLocalDateTraverser with initial value '2015-04-03'
    When traversal skipped by <skip>
    Then the traversed value is '<result>'

  Examples:
    | skip | result     |
    | 3    | 2015-04-09 |
    | 4    | 2015-04-10 |
    | 5    | 2015-04-13 |
    | 6    | 2015-04-14 |
    | 8    | 2015-04-16 |
    | 9    | 2015-04-17 |
    | 10   | 2015-04-20 |
    | 11   | 2015-04-21 |
    | 18   | 2015-04-30 |
    | 19   | 2015-05-01 |
    | 20   | 2015-05-04 |