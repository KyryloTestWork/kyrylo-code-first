Feature: Prices API

  Scenario: Add a new price
    When I add a new price with the following details
      | brandId | startDate           | endDate             | priceList | productId | priority | price | currency | creationDate        | creationUser | lastUpdateDate      | lastUpdateUser |
      | 1       | 2024-06-20T18:29    | 2024-06-23T18:29    | 5         | 35455     | 0        | 19.99 | EUR      | 2024-06-23T18:29    | example      | 2024-06-23T18:29    | example        |
    Then the response status should be 201
    And the response should contain the new price with the following details
      | brandId | startDate           | endDate             | priceList | productId | priority | price | currency | creationDate        | creationUser | lastUpdateDate      | lastUpdateUser |
      | 1       | 2024-06-20T18:29    | 2024-06-23T18:29    | 5         | 35455     | 0        | 19.99 | EUR      | 2024-06-23T18:29    | example      | 2024-06-23T18:29    | example        |

  Scenario: Update a price
    When I update the price with ID 5 with the following details
      | brandId | startDate           | endDate             | priceList | productId | priority | price | currency | creationDate        | creationUser | lastUpdateDate      | lastUpdateUser |
      | 1       | 2024-06-20T18:29    | 2024-06-23T18:29    | 5         | 35455     | 0        | 39.99 | EUR      | 2024-06-23T18:29    | example      | 2024-06-23T18:29    | kyrylo        |
    Then the response status should be 200
    And the response should contain the updated price with the following details
      | brandId | startDate           | endDate             | priceList | productId | priority | price | currency | creationDate        | creationUser | lastUpdateDate      | lastUpdateUser |
      | 1       | 2024-06-20T18:29    | 2024-06-23T18:29    | 5         | 35455     | 0        | 39.99 | EUR      | 2024-06-23T18:29    | example      | 2024-06-23T18:29    | kyrylo        |

  Scenario: Get price by ID 5
    When I get the price by ID 5
    Then the response status should be 200
    And the response should contain the price with the following details
      | brandId | startDate           | endDate             | priceList | productId | priority | price | currency | creationDate        | creationUser | lastUpdateDate      | lastUpdateUser |
      | 1       | 2024-06-20T18:29    | 2024-06-23T18:29    | 5         | 35455     | 0        | 39.99 | EUR      | 2024-06-23T18:29    | example      | 2024-06-23T18:29    | kyrylo        |

  Scenario: Delete a price
    When I delete the price with ID 5
    Then the response status should be 200

  Scenario: Get PVP price for 2020-06-14 10:00
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-14T10:00:00"
    Then the response status should be 200
    And the response should contain the pvp price: 35:50 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-14T10:00:00 | 1         | 35455     | 35.50 | EUR      |

  Scenario: Get PVP price for 2020-06-14 16:00
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-14T16:00:00"
    Then the response status should be 200
    And the response should contain the pvp price: 25:45 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-14T16:00:00 | 2         | 35455     | 25.45 | EUR      |

  Scenario: Get PVP price for 2020-06-14 21:00
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-14T21:00:00"
    Then the response status should be 200
    And the response should contain the pvp price: 35:50 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-14T21:00:00 | 1         | 35455     | 35.50 | EUR      |

  Scenario: Get PVP price for 2020-06-15 10:00
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-15T10:00:00"
    Then the response status should be 200
    And the response should contain the pvp price: 30:50 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-15T10:00:00 | 3         | 35455     | 30.50 | EUR      |

  Scenario: Get PVP price for 2020-06-15 11:30
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-15T11:30:00"
    Then the response status should be 200
    And the response should contain the pvp price: 35:50 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-15T11:30:00 | 1         | 35455     | 35.50 | EUR      |

  Scenario: Get PVP price for 2020-06-16 21:00
    When I request the PVP price for brandId 1, productId 35455 and applicationDate "2020-06-16T21:00:00"
    Then the response status should be 200
    And the response should contain the pvp price: 38:95 and following details
      | brandId | applicationDate     | priceList | productId | price | currency |
      | 1       | 2020-06-16T21:00:00 | 4         | 35455     | 38.95 | EUR      |
