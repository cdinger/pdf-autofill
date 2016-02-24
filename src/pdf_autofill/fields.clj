(ns pdf-autofill.fields
  (:require [clojure.java.io :as io]
            [endophile.core :as md]))

(defn markdown-file [fieldname]
  (-> (str "fields/" fieldname ".md")
      io/resource
      io/file))

(defn markdown-content [fieldname]
  (slurp (markdown-file fieldname)))

(defn markdown-map [fieldname]
  (let [content (markdown-content fieldname)]
    (md/to-clj (md/mp content))))

(defn sql [fieldname]
  (let [m (markdown-map fieldname)]
    ; ick.
    (-> (filter #(= :pre (get % :tag)) m)
        first
        (get :content)
        first
        (get :content)
        first)))

(def fields {:random-number {:description "Generates a random number"
                             :sql "select dbms_random.value from dual"}
             :today         {:description "Today's date"
                             :sql "select sysdate from dual"}
             :emplid        {:description "The current user's emplid"
                             :sql "select emplid from ps_hr.ps_personal_data where alter_emplid=upper(:current_principal)"}})


