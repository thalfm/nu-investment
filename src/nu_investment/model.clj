(ns nu-investment.model
  (:require [schema.core :as s]))

(def PosInt (s/pred pos-int?))

(def Operation
  {:operation s/Str
   :unit-cost PosInt
   :quantity  PosInt
   :ticker    s/Str})

(def Operations [Operation])