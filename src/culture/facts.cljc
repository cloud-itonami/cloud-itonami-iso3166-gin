(ns culture.facts
  "Country-level regional-culture catalog for Guinea (GIN) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"GIN"
   [{:culture/id "gin.dish.fou-fou"
     :culture/name "Fou fou"
     :culture/name-local "Tôreuy"
     :culture/country "GIN"
     :culture/kind :dish
     :culture/summary "Savoury dough topped with okra sauce, one of the staple dishes of Guinean cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Cuisine_of_Guinea"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.dish.maafe"
     :culture/name "Maafe"
     :culture/country "GIN"
     :culture/kind :dish
     :culture/summary "Peanut-sauce stew listed among the sauces of Guinean cuisine; the stew is a staple across Western Africa with origins in Senegal and Mali."
     :culture/url "https://en.wikipedia.org/wiki/Cuisine_of_Guinea"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.dish.footi"
     :culture/name "Footi"
     :culture/country "GIN"
     :culture/kind :dish
     :culture/summary "Thick Guinean sauce of eggplants, onions, kidney beans and tomatoes."
     :culture/url "https://en.wikipedia.org/wiki/Cuisine_of_Guinea"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.beverage.bissap"
     :culture/name "Bissap"
     :culture/country "GIN"
     :culture/kind :beverage
     :culture/summary "Purple-coloured hibiscus drink, sometimes including mint, one of the common beverages of Guinean cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Cuisine_of_Guinea"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.product.fonio"
     :culture/name "Fonio"
     :culture/country "GIN"
     :culture/kind :product
     :culture/summary "Cultivated West African grain vital to rural areas such as Guinea's Fouta Djallon mountains; Guinea produces the most fonio in the world, over 75% of world production in 2019."
     :culture/url "https://en.wikipedia.org/wiki/Fonio"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.craft.balafon"
     :culture/name "Balafon"
     :culture/country "GIN"
     :culture/kind :craft
     :culture/summary "Gourd-resonated West African xylophone, originating in Mali and historically centred in Guinea, recognised by UNESCO as intangible cultural heritage since 2001."
     :culture/url "https://en.wikipedia.org/wiki/Balafon"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "gin.heritage.mount-nimba"
     :culture/name "Mount Nimba Strict Nature Reserve"
     :culture/country "GIN"
     :culture/kind :heritage
     :culture/summary "Transnational nature reserve spanning Guinea and Côte d'Ivoire, recognised by UNESCO as a World Heritage Site since 1981 for its exceptional biodiversity."
     :culture/url "https://en.wikipedia.org/wiki/Mount_Nimba_Strict_Nature_Reserve"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-gin culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "GIN"))
                 " GIN entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
