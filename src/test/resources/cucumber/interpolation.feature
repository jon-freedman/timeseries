Feature: Interpolation

  Scenario Outline: FlatFillInterpolator can be used to forward fill
    Given a FlatFillInterpolator using forward fill
    When interpolating for <x> given (<x1>, <y1>) and (<x2>, <y2>)
    Then the interpolated value is <y>

  Examples:
    | x | x1 | y1 | x2 | y2 | y  |
    | 5 | 1  | 1  | 10 | 10 | 1  |
    | 5 | 1  | 1  | 10 |    | 1  |
    | 5 | 1  |    | 10 | 10 | 10 |

  Scenario Outline: FlatFillInterpolator can be used to backward fill
    Given a FlatFillInterpolator using backward fill
    When interpolating for <x> given (<x1>, <y1>) and (<x2>, <y2>)
    Then the interpolated value is <y>

  Examples:
    | x | x1 | y1 | x2 | y2 | y  |
    | 5 | 1  | 1  | 10 | 10 | 10 |
    | 5 | 1  | 1  | 10 |    | 1  |
    | 5 | 1  |    | 10 | 10 | 10 |

  Scenario Outline: ZeroValueInterpolator can be used
    Given a ZeroValueInterpolator
    When interpolating for <x> given (<x1>, <y1>) and (<x2>, <y2>)
    Then the interpolated value is <y>

  Examples:
    | x | x1 | y1 | x2 | y2 | y |
    | 5 | 1  | 1  | 10 | 10 | 0 |
    | 5 | 1  | 1  | 10 |    | 0 |
    | 5 | 1  |    | 10 | 10 | 0 |

  Scenario Outline: LinearInterpolator can be used but does not handle nulls
    Given a LinearInterpolator
    When interpolating for <x> given (<x1>, <y1>) and (<x2>, <y2>)
    Then the interpolated value is <y>

  Examples:
    | x | x1 | y1 | x2 | y2 | y    |
    | 5 | 1  | 1  | 10 | 10 | 5    |
    | 5 | 1  | 1  | 10 |    | null |
    | 5 | 1  |    | 10 | 10 | null |
