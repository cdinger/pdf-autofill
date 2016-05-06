(ns pdf-autofill.markdown
  (:require [clojure.java.io :as io]
            [pdf-autofill.config :as config]
            [endophile.core :as md]))

(def markdown-path (io/file config/markdown-path))

(defn files [path]
  (rest (file-seq path)))

(defn parsed-file [file]
  (md/to-clj (md/mp (slurp file))))

(defn parsed-files [files]
  (map #(parsed-file %1) files))

(defn title [parsed]
  (apply str (:content (first (filter #(= :h1 (:tag %1)) parsed)))))

(defn description [parsed]
  (apply str (:content (first (filter #(= :p (:tag %1)) parsed)))))

(defn sql [parsed]
  (apply str (:content (first (:content (first (filter #(= :pre (:tag %1)) parsed)))))))

(defn field [f]
  {(keyword (title f)) {:description (description f)
                        :sql         (sql f)}})

(defn fields [path]
  (let [field-seq (map #(field %1) (parsed-files (files path)))]
    (into {} field-seq)))
