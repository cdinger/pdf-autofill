(ns pdf-autofill.fields)
  ;(:require [clojure.java.io :as io]
            ;[endophile.core :as md]
            ;[pdf-autofill.config :as config]))

;(def path config/markdown-path)

;(defn markdown-files []
  ;(let [files (file-seq (io/file path))]
    ;(rest files)))

;(defn markdown-file [fieldname]
  ;(-> (str path "/" fieldname ".md")
      ;io/resource
      ;io/file))

;(defn markdown-content [fieldname]
  ;(slurp (markdown-file fieldname)))

;(defn markdown-map [fieldname]
  ;(let [content (markdown-content fieldname)]
    ;(md/to-clj (md/mp content))))

;(defn sql [fieldname]
  ;(let [m (markdown-map fieldname)]
    ;; ick.
    ;(-> (filter #(= :pre (get % :tag)) m)
        ;first
        ;(get :content)
        ;first
        ;(get :content)
        ;first)))

(def fields {:first_name {:description "Returns the current user's first name."
                          :sql         "select firstname from users where username = :principal_id"}
             :last_name  {:description "Returns the current user's last name."
                          :sql         "select lastname from users where username = :principal_id"}
             :malformed  {:asdf "blah"}})

(defn sql [fieldname]
  (get (get fields (keyword fieldname)) :sql))

