(ns pdf-autofill.fields
  (:require [pdf-autofill.markdown :as md]))

(def fields (md/fields))

(defn sql [fieldname]
  (get (get fields (keyword fieldname)) :sql))

