(ns nu-investment.core
  (:require [nu-investment.logic :as nui.logic]
            [nu-investment.serializer :as nui.srlz]
            [nu-investment.database :as nui.db]
            [schema.core :as s]))

(s/set-fn-validation! true)

(def operations (nui.db/fetch-operation))
(defn -main
  [& _]
  (->> (nui.srlz/json-operations->Map operations)
       nui.logic/tax-operations
       nui.logic/operations->only-tax
       nui.srlz/tax->json
       println))
