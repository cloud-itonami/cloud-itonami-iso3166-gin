# cloud-itonami-iso3166-gin

**GIN**: Republic of Guinea (Guinée).

- ARMP (Autorité de Régulation des Marchés Publics) public procurement,
  DGCMP (Direction Générale du Contrôle des Marchés Publics) a priori
  control; Code des Marchés Publics (décret, signed 17 December 2019 by
  Président Alpha Condé, en application de la Loi L/2012/020/CNT du 11
  octobre 2012 modifiée par la Loi L/2018/028/AN du 05 juillet 2018)
- OHADA RCCM (Registre du Commerce et du Crédit Mobilier) company/
  business registration via the Tribunal de Commerce de Conakry; NIFP
  (Numéro d'Identification Fiscale Permanent) tax registration via the
  Direction Générale des Impôts (DGI); APIP (Agence de Promotion des
  Investissements Privés) investment guichet unique
- Code des Marchés Publics Article 159 ARMP pecuniary-sanction 1%-2%
  statutory range gate (flagship check)

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as `cloud-itonami-iso3166-caf`/`-cog` (the closest architectural match:
Republic of the Congo is also a Francophone OHADA member state sharing
the same supranational company-law instrument):

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Code des
  Marchés Publics (a décret implementing Loi L/2012/020/CNT du 11
  octobre 2012, as amended by Loi L/2018/028/AN du 05 juillet 2018;
  the décret's own number field is left blank in the officially-hosted
  scanned copy -- an honest, explicitly-flagged gap) creating ARMP
  (Article 16) and naming DGCMP (per the Ministère de l'Économie et des
  Finances' own directorate listing) as the separate a priori/a
  posteriori control body; RCCM (OHADA's Acte Uniforme relatif au Droit
  Commercial Général, performed by the Tribunal de Commerce de Conakry
  per that court's own site) and NIFP (Numéro d'Identification Fiscale
  Permanent, Direction Générale des Impôts) tax registration.
  `governor.cljc`'s flagship check independently recomputes whether an
  engagement's own declared prior ARMP pecuniary sanction (Article 159)
  falls within the Code's own 1%-2% statutory range of its base amount
  -- a TWO-SIDED (floor AND ceiling) percentage-of-value range
  validation, a check shape genuinely different from every other
  iso3166 sibling's (see the namespace docstrings for the full research
  trail and honestly-narrowed scope, including a genuinely unresolved
  currency conflict this iteration found over whether Guinea's
  investment-promotion agency APIP has since been absorbed into a new
  "Agence Guinéenne de Développement" per a March 2026 press report).
- `src/statute/facts.cljc` -- general-law catalog: the OHADA Uniform
  Act on Commercial Companies (AUSCGIE, directly applicable, no
  domestic transposition act -- this iteration independently fetched
  the Traité de Port-Louis' own Article 10 text to confirm), the Code
  du Travail (Loi L/2014/072/CNT du 10 janvier 2014, confirmed directly
  from Guinea's own Journal Officiel), and the Code des Investissements
  (Loi L/2015/008/AN du 25 mai 2015).

Every citation is curl/WebFetch-verified against an official source
(armpguinee.org, mef.gov.gn, ohada.org, tc-conakry.gov.gn, dgi.gov.gn,
apip.gov.gn, natlex.ilo.org's hosting of Guinea's own Journal Officiel);
the Code des Marchés Publics and Code du Travail PDFs are both scanned
images with no embedded text layer, read via French-language Tesseract
OCR across every page, with key pages (cover, signature, Article 64,
Article 159) additionally zoomed and read directly as images -- see
`marketentry.facts`'s docstring for exactly which facts are HIGH
confidence vs. an honestly-flagged gap.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Guinea:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
