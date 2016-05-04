(ns pdf-autofill.autofill
  (:require [pdf-autofill.db :as db]
            [pdf-autofill.pdf :as p]
            [pdf-autofill.fields :as f]
            [clojure.string :as s]))

(def prefix "autofill/")

(defn fillable? [fieldname]
  (re-matches (re-pattern (str "^" prefix ".*")) fieldname))

(defn field-basename [field]
  (s/replace field (re-pattern (str "^" prefix)) ""))

(defn field-sql [fieldname]
  (f/sql fieldname))

(defn fillable-fieldnames [pdf]
  (let [fieldnames (p/fieldnames pdf)]
    (map #(field-basename %) (filter #(fillable? %) fieldnames))))

(defn lookup-field-value [field current-prinicpal]
  (db/query-one (field-sql field) current-prinicpal))

(defn fillable-data
  "For a given PDF and user ID, returns a map of autofillable form fieldnames
  and their queried values."
  [pdf current-prinicpal]
  (let [fieldnames (fillable-fieldnames pdf)
        full-fieldnames  (map #(str "autofill/" %) fieldnames)
        values     (map #(lookup-field-value % current-prinicpal) fieldnames)]
    (zipmap full-fieldnames values)))

(defn filled-pdf [pdf current-prinicpal]
  (p/set-values pdf (fillable-data pdf current-prinicpal))
  pdf)
