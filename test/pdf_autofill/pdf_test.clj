(ns pdf-autofill.pdf-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.pdf :refer :all]
            [clojure.java.io :as io])
  (:import [org.apache.pdfbox.pdmodel PDDocument]))

(def pdf (-> "otr014.pdf"
             io/resource
             io/file
             PDDocument/load))

(deftest test-document
  (document "http://policy.umn.edu/sites/policy.umn.edu/files/forms/otr014.pdf"))

(deftest test-fields
  (let [f (fields pdf)]
    (is (= (count f) 69))))

(deftest test-field-value
  (let [value (field-value pdf "autofill/emplid")]
    ;; "asdf" is already set in the pdf
    (is (= value "asdf"))))

(deftest test-set-values
  (let [values {"autofill/last_name" "McTesterson" "autofill/first_name" "Testy"}
        filled-pdf (set-values pdf values)]
    (doseq [field (filter #(contains? values (.getPartialName %)) (fields filled-pdf))]
      (is (= (get values (.getPartialName field)) (.getValue field))))))

