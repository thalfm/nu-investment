(ns nu-investment.serializer
  (:require [clojure.data.json :as json]
            [nu-investment.model :as nui.model]))

(defn json-operations->Map
  [operations] :- nui.model/Operations
  (json/read-str operations :key-fn keyword))

(defn tax->json
  [fees]
  (json/write-str fees))