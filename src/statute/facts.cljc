(ns statute.facts
  "General-law compliance catalog for the Republic of Guinea (GIN) --
  extends this repo's existing `marketentry.facts` (public-procurement
  market-entry only, narrow scope) with a second, orthogonal catalog of
  statutes a company operating in this jurisdiction must generally track
  for compliance. Mirrors cloud-itonami-iso3166-ben/-btn/-caf/-cog's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-
  federation).

  Every entry below cites an OFFICIAL government-hosted (or, for the
  OHADA entries, official supranational-body-hosted) URL -- never
  fabricated, all curl/WebFetch/OCR-verified 2026-07-22:

  - **Companies/commercial-entity law**: this iteration specifically
    investigated, rather than assumed by analogy to the CAF/COG
    siblings, whether Guinea is itself an OHADA member state --
    independently confirmed directly on OHADA's own 'Les Etats membres de
    l'OHADA' page (`ohada.org/les-etats-membres-de-lohada/`,
    WebFetch-verified), which lists 'Guinée' among the member states,
    explicitly distinct from 'Guinée Bissau' and 'Guinée Equatoriale' in
    the SAME sentence -- this iteration did not conflate the three. So,
    like Benin/CAF/COG, company law is governed DIRECTLY by a
    SUPRANATIONAL instrument, the OHADA Acte uniforme relatif au droit
    des sociétés commerciales et du groupement d'intérêt économique
    (AUSCGIE) -- this iteration independently re-fetched OHADA's own page
    (`ohada.org/droit-des-societes-commerciales-et-du-gie/`,
    WebFetch-verified directly, NOT copied from a sibling's entry) and
    confirmed, in OHADA's own words: 'Adoption Date: 30 janvier 2014 à
    Ouagadougou (Burkina Faso) -- Publication Date: 04 février 2014 --
    Entry into Force: 05 mai 2014'. This iteration also independently
    fetched the Traité de Port-Louis' own Article 10 text directly
    (`ohadalegis.com/telAUFR/1993-Traite-Ohada-fr.pdf`, a native
    text-layer PDF, read via `pdftotext`, NOT OCR): 'Les actes uniformes
    sont directement applicables et obligatoires dans les Etats Parties,
    nonobstant toute disposition contraire de droit interne, antérieure
    ou postérieure.' -- i.e. AUSCGIE applies in Guinea without any
    domestic transposition act, the same direct-applicability principle
    CAF's/COG's own catalogs document, here independently re-derived
    from the treaty's own primary text rather than taken on the sibling
    catalogs' word. Separately, RCCM/business-entity REGISTRATION -- as
    opposed to company FORMATION/governance law -- is governed by a
    DIFFERENT OHADA instrument, the Acte Uniforme relatif au Droit
    Commercial Général (AUDCG); this catalog does not conflate the two
    (`marketentry.facts` cites AUDCG separately for RCCM, performed in
    Guinea by the Tribunal de Commerce de Conakry per that court's own
    site).
  - **Code du Travail (Labour Code)**: Loi L/2014/072/CNT du 10 janvier
    2014 portant Code du Travail de la République de Guinée -- this
    iteration downloaded Guinea's own OFFICIAL Journal Officiel special
    issue directly (hosted by the ILO's own NATLEX legal database,
    `natlex.ilo.org/dyn/natlex2/natlex2/files/download/96583/GIN-
    96583.pdf`; the PDF is itself a scanned reproduction of the
    Journal Officiel de la République de Guinée, published by the
    Secrétariat Général du Gouvernement, no embedded text layer --
    `pdftotext` returned zero output, read instead via French-language
    Tesseract OCR). Own cover/title text (OCR-read, HIGH confidence,
    clean typeset, not handwritten): 'JOURNAL OFFICIEL DE LA REPUBLIQUE
    DE GUINEE ... PRESIDENCE DE LA REPUBLIQUE SECRETARIAT GENERAL DU
    GOUVERNEMENT ... LOI L/2014/072/CNT DU 10 JANVIER 2014, PORTANT CODE
    DU TRAVAIL DE LA REPUBLIQUE DE GUINEE ... LE CONSEIL NATIONAL DE LA
    TRANSITION ... adopte la Loi dont la teneur suit: Article 1er: est
    adoptée la présente Loi portant Code du Travail de la République de
    Guinée.' -- independently corroborated by Guinea's own Cour Suprême
    website (`coursupreme.org.gn/fr/code-du-travail-du-10-janvier-2014/`,
    WebFetch-verified directly), which lists this same law, dated 10
    January 2014, under its own 'Codes en Vigueur' (Codes in Force)
    section -- a second, independent official Guinean government source
    confirming the same date.
  - This iteration also independently confirmed, via APIP's own site
    (`apip.gov.gn/code-des-investissements/`, WebFetch-verified) and the
    Assemblée Nationale de Guinée's own archive
    (`cnt.gov.gn/archive.assemblee/www.assemblee.gov.gn/l2015008an-
    portant-code-des-investissements-de-la-republique-de-guinee.html`,
    WebFetch-verified directly, an official institutional portal for
    Guinea's legislative body), a further Guinea-specific statute
    relevant to compliance: Loi L/2015/008/AN du 25 mai 2015 portant
    Code des Investissements de la République de Guinée, administered by
    APIP (Agence de Promotion des Investissements Privés) -- see
    `marketentry.facts`'s namespace docstring for the honestly-flagged,
    unresolved March 2026 currency conflict over whether APIP has since
    been absorbed into a new 'Agence Guinéenne de Développement' (GDB).
    This iteration did NOT independently fetch the Code des
    Investissements' own primary text (only its own citing pages), an
    honest gap not resolved by guessing article numbers.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"GIN"
   [{:statute/id "gin.ohada-auscgie"
     :statute/title "Acte uniforme relatif au droit des sociétés commerciales et du groupement d'intérêt économique (AUSCGIE)"
     :statute/jurisdiction "GIN"
     :statute/kind :law
     :statute/law-number "OHADA Uniform Act -- adopted 30 January 2014 (Ouagadougou), published in the OHADA Official Journal 4 February 2014, in force 5 May 2014; directly applicable in the Republic of Guinea as an OHADA member state per Traité de Port-Louis Art. 10 (own text, independently fetched: 'directement applicables et obligatoires ... nonobstant toute disposition contraire de droit interne'), no domestic transposition act required"
     :statute/url "https://www.ohada.org/droit-des-societes-commerciales-et-du-gie/"
     :statute/url-provenance :official-ohada-org
     :statute/enacted-date "2014-01-30"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "gin.code-du-travail-2014"
     :statute/title "Code du Travail de la République de Guinée"
     :statute/jurisdiction "GIN"
     :statute/kind :law
     :statute/law-number "Loi L/2014/072/CNT du 10 janvier 2014 (own primary text, OCR-read from Guinea's own Journal Officiel scan hosted by ILO NATLEX; HIGH confidence, independently corroborated by Guinea's own Cour Suprême 'Codes en Vigueur' listing)"
     :statute/url "https://natlex.ilo.org/dyn/natlex2/natlex2/files/download/96583/GIN-96583.pdf"
     :statute/url-provenance :official-journal-officiel-via-ilo-natlex
     :statute/enacted-date "2014-01-10"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "gin.code-des-investissements-2015"
     :statute/title "Code des Investissements de la République de Guinée"
     :statute/jurisdiction "GIN"
     :statute/kind :law
     :statute/law-number "Loi L/2015/008/AN du 25 mai 2015 (per APIP's own site and the Assemblée Nationale de Guinée's own archive; this iteration did not independently fetch the law's own primary text, an honest gap)"
     :statute/url "https://cnt.gov.gn/archive.assemblee/www.assemblee.gov.gn/l2015008an-portant-code-des-investissements-de-la-republique-de-guinee.html"
     :statute/url-provenance :official-assemblee-nationale-gn
     :statute/enacted-date "2015-05-25"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:investment}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-gin statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "GIN")) " GIN statutes seeded with an "
                 "official citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :corporate-governance)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
