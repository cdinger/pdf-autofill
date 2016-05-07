(ns pdf-autofill.fields
  (:require [pdf-autofill.markdown :as md]
            [pdf-autofill.s3 :as s3]))


(def fields (merge (md/fields) (s3/fields)))

(defn sql [fieldname]
  (get (get fields (keyword fieldname)) :sql))

