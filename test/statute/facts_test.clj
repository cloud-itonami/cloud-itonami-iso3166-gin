(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest gin-has-spec-basis
  (let [sb (facts/spec-basis "GIN")]
    (is (= 3 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["GIN" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["gin.code-du-travail-2014"]
         (mapv :statute/id (facts/by-topic "GIN" :labor))))
  (is (= ["gin.ohada-auscgie"]
         (mapv :statute/id (facts/by-topic "GIN" :corporate-governance))))
  (is (= ["gin.code-des-investissements-2015"]
         (mapv :statute/id (facts/by-topic "GIN" :investment))))
  (is (empty? (facts/by-topic "GIN" :environment)))
  (is (empty? (facts/by-topic "ATL" :labor))))
