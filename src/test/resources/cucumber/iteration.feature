Feature: Iteration

  Scenario: Can traverse to next value
    Given a LocalDateTraverser with initial value '2015-04-04'
    When traversed to next
    Then the traversed value is '2015-04-05'

  Scenario: Can traverse to nth value
    Given a LocalDateTraverser with initial value '2015-04-04'
    When traversal skipped by 1
    Then the traversed value is '2015-04-06'