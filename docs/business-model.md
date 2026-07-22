# Business model — GIN

Independent public-sector market-entry compliance for the Republic of
Guinea.

- Autorité de Régulation des Marchés Publics (ARMP) public procurement
  -- an independent administrative authority (Code des Marchés Publics,
  décret signed 17 December 2019, Article 16); a priori/a posteriori
  control of the procurement procedure is a SEPARATE body, the Direction
  Générale du Contrôle des Marchés Publics (DGCMP), housed within the
  Ministère de l'Économie et des Finances (see `src/marketentry/facts.cljc`)
- No confirmed dedicated self-service e-procurement portal; notices/
  texts are published via ARMP and the Ministère de l'Économie et des
  Finances' own sites
- OHADA member state -- RCCM (Registre du Commerce et du Crédit
  Mobilier) company/business registration performed by the Tribunal de
  Commerce de Conakry; APIP (Agence de Promotion des Investissements
  Privés) investment guichet unique (see namespace docstring for a
  genuinely unresolved March 2026 currency conflict over a possible
  APIP-to-GDB transition)
- Direction Générale des Impôts (DGI) NIFP (Numéro d'Identification
  Fiscale Permanente) tax registration
- Code des Marchés Publics Article 159 ARMP pecuniary-sanction regime --
  a 1%-2% statutory percentage-of-base-value range on any ARMP-imposed
  pecuniary sanction, and a 10-year statutory cap on exclusion-from-
  competition sanctions (the cap is recorded honestly but not modeled
  as a governor check -- see `marketentry.registry`'s docstring)
