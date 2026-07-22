(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `pecuniary-sanction-outside-range?` is THIS vertical's own new ground-
  truth check, grounding GIN's flagship governor check
  (`marketentry.governor/pecuniary-sanction-range-violations`): the Code
  des Marchés Publics' own Article 159 ('Sanctions des candidats,
  soumissionnaires et titulaires des marchés', own primary text, see
  `marketentry.facts`) states that an ARMP pecuniary sanction against a
  candidate/bidder/contract-holder found in breach 'est compris entre un
  pour cent (1%) et deux pour cent (2%) du montant de l'offre pour le
  soumissionnaire et du montant du marché pour le titulaire
  contrevenant'. This is a GENUINELY DIFFERENT check SHAPE than every
  prior iso3166 sibling this repo mirrors: Bulgaria's ЗОП Art. 54(5)
  de-minimis is a PERCENTAGE-OF-TURNOVER ELIGIBILITY formula, Albania's
  Neni 76(2)(c) carve-out is a FLAT-CONSTANT ELIGIBILITY threshold,
  Azerbaijan's/Armenia's flagship checks are BOOLEAN registry-membership
  ELIGIBILITY reads, Antigua and Barbuda's vendor-class check is a
  THREE-TIER ELIGIBILITY-THRESHOLD classification, Benin's MPME
  mechanism is a BID-EVALUATION PRICE ADJUSTMENT, Bhutan's FDI Negative
  List is a CATEGORICAL SECTOR-EXCLUSION allow-list gate, CAF's Marché
  réservé mechanism is a MULTI-CRITERION INCLUSION-ELIGIBILITY OR-test,
  and COG's exclusion-duration-cap check is a SINGLE-SIDED STATUTORY
  CEILING VALIDATION. Guinea's own Article 159 pecuniary-sanction rule is
  none of these: it is a TWO-SIDED (floor AND ceiling) PERCENTAGE-OF-
  BASE-VALUE RANGE VALIDATION -- the first in this family to validate
  that a declared/imposed monetary amount falls BETWEEN two bounds
  derived from a base value, rather than merely under (or over) a single
  bound, a turnover ratio, a flat constant, a boolean membership test, a
  tiered classification, a price adjustment, or a sector allow-list.

  Guinea's OWN Article 159 ALSO states a single-sided statutory
  exclusion-duration ceiling ('La décision d'exclusion de la commande
  publique ne peut dépasser dix (10) ans') -- the SAME shape category
  COG's own flagship check already uses (a different law, a different
  number, and Guinea's own text carries no explicit court-ordered-
  definitive-exclusion escape hatch comparable to COG's Art. 146).
  This namespace deliberately does NOT model that duration cap as a
  governor check (to avoid re-using a shape already spoken for by a
  sibling) -- it is recorded honestly in `marketentry.facts`'s docstring
  for completeness only.

  It is entity-condition-gated like COG's/CAF's own flagship checks: a
  no-op (false) unless `:prior-armp-pecuniary-sanction?` is true (an
  engagement with no declared prior ARMP pecuniary sanction has nothing
  for this check to validate). Missing/non-numeric
  `:armp-sanction-amount` or `:armp-sanction-base-amount` for a declared
  prior sanction is never treated as violating the range HERE (that is
  the `evidence-incomplete` check's job, upstream, where an assessment
  must already exist).

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real ARMP, DGCMP, Tribunal de Commerce or DGI system. It
  builds the RECORD an operator would keep, not the act of submitting a
  BOAMP tender response or an ARMP filing itself (that is
  `marketentry.operation`'s `:filing/submit`, always human-gated -- see
  README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def pecuniary-sanction-range
  "Code des Marchés Publics, Article 159 (own primary text, visually
  confirmed at zoom against armpguinee.org's own hosting 2026-07-22): the
  statutory percentage-of-base-value RANGE (both bounds) within which an
  ARMP pecuniary sanction against a candidate/bidder/contract-holder
  must fall."
  {:min-pct 0.01
   :max-pct 0.02})

(defn pecuniary-sanction-outside-range?
  "Does `engagement`'s own declared prior ARMP pecuniary sanction fall
  OUTSIDE Article 159's own statutory 1%-2% range of its declared base
  amount (the offer amount for a soumissionnaire, the contract amount
  for a titulaire)?

  A no-op (false) unless `:prior-armp-pecuniary-sanction?` is true -- an
  engagement with no declared prior ARMP pecuniary sanction has nothing
  for this check to validate. Missing/non-numeric
  `:armp-sanction-amount` or `:armp-sanction-base-amount` for a declared
  prior sanction is never treated as violating the range here (that is
  the `evidence-incomplete` check's job, upstream, where an assessment
  must already exist)."
  [{:keys [prior-armp-pecuniary-sanction? armp-sanction-amount armp-sanction-base-amount]}]
  (boolean
   (when (true? prior-armp-pecuniary-sanction?)
     (when (and (number? armp-sanction-amount) (number? armp-sanction-base-amount)
                (pos? armp-sanction-base-amount))
       (let [ratio (/ (double armp-sanction-amount) (double armp-sanction-base-amount))]
         (or (< ratio (:min-pct pecuniary-sanction-range))
             (> ratio (:max-pct pecuniary-sanction-range))))))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a BOAMP/ARMP tender
  response or filing package. Pure function -- does not touch any real
  ARMP, DGCMP, Tribunal de Commerce or DGI system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a BOAMP/ARMP
  tender response or filing (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
