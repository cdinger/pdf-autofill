(ns pdf-autofill.db
  (:require [clojure.java.jdbc :as jdbc]
            [jdbc.pool.c3p0 :as pool]
            [pdf-autofill.config :as config]))

(def db (pool/make-datasource-spec config/database-spec))

(defn query [sql & parameters]
  (jdbc/query db (concat [(str sql)] parameters)))

(defn query-one [sql & parameters]
  (let [results (apply query (concat [(str sql)] parameters))
        first-result (first results)
        first-values (vals first-result)
        first-value (first first-values)]
    first-value))
