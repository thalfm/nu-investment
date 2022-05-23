(ns nu-investment.logic)

(defn buying-operation?
  [op]
  (= (get op :operation) "buy"))

(defn amount-operation
  [op]
  (* (:unit-cost op) (:quantity op)))

(defn total-operation
  [ops]
  (reduce #(+ %1 (amount-operation %2)) 0 ops))

(defn total-quantity
  [ops]
  (reduce #(+ %1 (%2 :quantity)) 0 ops))

(defn weighted-average-operation
  [ops]
    (/ (total-operation ops) (total-quantity ops)))

(defn has-loss?
  [weighted-average op]
  (> weighted-average (:unit-cost op)))

(defn desired-price
  [weighted-average op]
  (* weighted-average (:quantity op)))

(defn loss-amount
  [initial-loss weighted-average op]
  (if (has-loss? weighted-average op)
    (+ initial-loss (- (desired-price weighted-average op) (amount-operation op)))
    0))

(defn amount-less-than-fee-win-limit?
  [op]
  (<= (amount-operation op) 20000))

(defn not-charge-fee?
  [weighted-average op]
  (or (amount-less-than-fee-win-limit? op) (has-loss? weighted-average op)))

(defn profit
  [weighted-average op]
  (if (has-loss? weighted-average op)
    0
    (- (amount-operation op) (desired-price weighted-average op))))

(defn profit-less-than-loss?
  [loss-amount weighted-average op]
  (< (profit weighted-average op) loss-amount))

(defn tax-per-profit
  [weighted-average op loss-amount]
  (* (- (profit weighted-average op) loss-amount) 0.20))

(defn tax
  [loss-amount weighted-average op]
  (cond
    (buying-operation? op) {:tax 0, :loss loss-amount}
    (not-charge-fee? weighted-average op) {:tax 0, :loss loss-amount}
    (profit-less-than-loss? loss-amount weighted-average op) (:tax 0 :loss (- loss-amount (profit weighted-average op)))
    :else {:tax (tax-per-profit weighted-average op loss-amount), :loss loss-amount}))

(defn weighted-average
  "get weighted average price of operation in de moment"
  [accumulate-operations]
  (let [actual-ticker (:ticker (last accumulate-operations))]
    (->> accumulate-operations
         (filter buying-operation?)
         (filter #(= actual-ticker (:ticker %)))
         weighted-average-operation)))

(defn tax-operations
  [ops]
  (loop [
         accumulate-operations []
         fees []
         initial-loss 0
         rest-operations, ops]
    (if (not-empty rest-operations)
      (do
        (let [first-in-rest (first rest-operations)
              accumulate (conj accumulate-operations first-in-rest)
              weighted-average (weighted-average accumulate)
              loss-amount (loss-amount initial-loss weighted-average first-in-rest)
              fee (tax loss-amount weighted-average first-in-rest)]
          (recur
            accumulate
            (conj fees fee)
            loss-amount
            (next rest-operations))))
      (do
        fees))))

(defn operations->only-tax
  [operations]
  (map (fn [op] {:tax (:tax op)}) operations))