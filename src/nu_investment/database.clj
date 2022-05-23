(ns nu-investment.database)

(defn fetch-operation
  []
  "
[
  {
    \"operation\": \"buy\",
    \"unit-cost\": 10,
    \"quantity\": 10000,
    \"ticker\": \"AAPL\"
  },
  {
    \"operation\": \"buy\",
    \"unit-cost\": 15,
    \"quantity\": 10000,
    \"ticker\": \"MANU\"
  },
  {
    \"operation\": \"sell\",
    \"unit-cost\": 20,
    \"quantity\": 10000,
    \"ticker\": \"AAPL\"
  },
  {
    \"operation\": \"sell\",
    \"unit-cost\": 30,
    \"quantity\": 10000,
    \"ticker\": \"MANU\"
  }
]")

(fetch-operation)