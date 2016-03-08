(ns pdf-autofill.db
  (:require [clojure.java.jdbc :as jdbc]
            [jdbc.pool.c3p0 :as pool]))

(def db (or (System/getenv "DATABASE_URL")
            "jdbc:sqlite:test/resources/test.sqlite3"))

(defn query [sql & parameters]
  (jdbc/query db (concat [sql] parameters)))

(defn query-one [sql & parameters]
  (let [results (apply query (concat [sql] parameters))
        first-result (first results)
        first-values (vals first-result)
        first-value (first first-values)]
    first-value))
