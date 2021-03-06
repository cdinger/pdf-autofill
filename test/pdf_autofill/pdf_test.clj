(ns pdf-autofill.pdf-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.pdf :refer :all]
            [clojure.java.io :as io])
  (:import [org.apache.pdfbox.pdmodel PDDocument]))

(def pdf (-> "hello_forms.pdf"
             io/resource
             io/file
             PDDocument/load))

; TODO: don't require a network connection to run this
;(deftest test-document
  ;(document "http://policy.umn.edu/sites/policy.umn.edu/files/forms/otr014.pdf"))

(deftest test-fields
  (let [f (fields pdf)]
    (is (= (count f) 2))))

(deftest test-field-value
  (let [value (field-value pdf "autofill/first_name")]
    ;; "asdf" is already set in the pdf
    (is (= value "asdf"))))

(deftest test-set-values
  (let [values {"autofill/last_name" "McTesterson" "autofill/first_name" "Testy"}
        filled-pdf (set-values pdf values)]
    (doseq [keyval values]
      (let [fieldname (first keyval)
            value     (last keyval)]
        (is (= value (field-value filled-pdf fieldname)))))))
