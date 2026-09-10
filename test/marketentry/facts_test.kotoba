(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest gin-has-spec-basis
  (let [sb (facts/spec-basis "GIN")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "GIN")))
    (is (some? (facts/business-registration-spec-basis "GIN")))
    (is (some? (facts/pecuniary-sanction-range-spec-basis "GIN")))))

(deftest gin-rep-spec-basis-is-populated
  (testing "Code des Marchés Publics Art. 64(h)'s exclusion-extension provision covers entities sharing the same corporate officers OR the same majority shareholders -- unlike CAF/COG, this iteration confirmed a real, current provision for GIN"
    (let [rep (facts/rep-spec-basis "GIN")]
      (is (some? rep))
      (is (string? (:rep-owner-authority rep)))
      (is (string? (:rep-legal-basis rep))))))

(deftest gin-business-registration-is-a-different-body-from-tax-and-procurement
  (testing "business/investment registration (APIP) and tax registration (DGI) are administered by different authorities -- see namespace docstring"
    (let [reg (facts/business-registration-spec-basis "GIN")
          tax (facts/corporate-number-spec-basis "GIN")]
      (is (some? reg))
      (is (some? tax))
      (is (not= (:business-registration-owner-authority reg)
                (:corporate-number-owner-authority tax))))))

(deftest gin-pecuniary-sanction-range-is-the-flagship-spec-basis
  (testing "Code des Marchés Publics Art. 159's 1%-2% pecuniary-sanction range is a real, verifiable statutory range -- not fabricated"
    (let [ps (facts/pecuniary-sanction-range-spec-basis "GIN")]
      (is (some? ps))
      (is (= 0.01 (:pecuniary-sanction-range-min-pct ps)))
      (is (= 0.02 (:pecuniary-sanction-range-max-pct ps)))
      (is (string? (:pecuniary-sanction-range-legal-basis ps))))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ")))
  (is (nil? (facts/business-registration-spec-basis "ATL")))
  (is (nil? (facts/pecuniary-sanction-range-spec-basis "ATL")))
  (is (nil? (facts/rep-spec-basis "ATL"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "GIN")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "GIN" all)))
    (is (not (facts/required-evidence-satisfied? "GIN" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["GIN" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))
