(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "GIN" 0)
        s (registry/register-submit "eng-1" "GIN" 0)]
    (is (= "GIN-DFT-000000" (get d "draft_number")))
    (is (= "GIN-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "GIN" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest pecuniary-sanction-range-recompute
  (testing "Code des Marchés Publics Art.159 -- a declared sanction within the 1%-2% statutory range is fine"
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true
                  :armp-sanction-amount 15000 :armp-sanction-base-amount 1000000}))))
  (testing "a declared sanction BELOW the 1% floor is a violation"
    (is (true? (registry/pecuniary-sanction-outside-range?
                {:prior-armp-pecuniary-sanction? true
                 :armp-sanction-amount 5000 :armp-sanction-base-amount 1000000}))))
  (testing "a declared sanction ABOVE the 2% ceiling is a violation"
    (is (true? (registry/pecuniary-sanction-outside-range?
                {:prior-armp-pecuniary-sanction? true
                 :armp-sanction-amount 50000 :armp-sanction-base-amount 1000000}))))
  (testing "exactly the floor (1%) and exactly the ceiling (2%) do not violate the range"
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true
                  :armp-sanction-amount 10000 :armp-sanction-base-amount 1000000})))
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true
                  :armp-sanction-amount 20000 :armp-sanction-base-amount 1000000}))))
  (testing "entity-condition-gated: a no-op (false) unless :prior-armp-pecuniary-sanction? is true"
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? false
                  :armp-sanction-amount 999999 :armp-sanction-base-amount 1000000})))
    (is (false? (registry/pecuniary-sanction-outside-range? {}))))
  (testing "missing/non-numeric amounts for a declared prior sanction are never treated as violating the range here (evidence-incomplete's job)"
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true})))
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true :armp-sanction-amount nil
                  :armp-sanction-base-amount 1000000})))
    (is (false? (registry/pecuniary-sanction-outside-range?
                 {:prior-armp-pecuniary-sanction? true :armp-sanction-amount 15000
                  :armp-sanction-base-amount 0})))))
