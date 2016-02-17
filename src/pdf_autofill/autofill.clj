(ns pdf-autofill.autofill
  (:require [pdf-autofill.db :as db]
            [pdf-autofill.pdf :refer :all]
            [pdf-autofill.fields :refer :all]
            [clojure.string :as s]))

(defn fillable? [field]
  (let [fieldname (.getPartialName field)]
    (re-matches #"^autofill/" field)))

(defn fillables [fields]
  (filter #(fillable? %) fields))

(defn field-basename [field]
  (s/replace (fieldname field) #"^autofill/" ""))

(defn field-sql [field]
  (keyword (field-basename field) fields))

(defn lookup-field-value [field current-prinicpal]
  (db/query (field-sql field) current-prinicpal))

(defn fill [pdf current-principal]
  (let [fillables (fillables (get-fields pdf))]
    (do
      (map #(.setValue % (lookup-field-value %) fillables))))
      pdf)
