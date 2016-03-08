(ns pdf-autofill.autofill-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.autofill :refer :all]
            [pdf-autofill.pdf :as p]
            [clojure.java.io :as io])
  (:import [org.apache.pdfbox.pdmodel PDDocument]))

(def pdf (-> "hello_forms.pdf"
             io/resource
             io/file
             PDDocument/load))

(deftest test-fillable-fieldnames
  (is (= '("first_name" "last_name")
         (fillable-fieldnames pdf))))

(deftest test-lookup-field-value
  (is (= "Testy" (lookup-field-value "first_name" "test"))))

(deftest test-fillable-data
  (let [data (fillable-data pdf "test")]
    (is (= data {"autofill/first_name" "Testy" "autofill/last_name" "McTesterson"}))))

(deftest test-filled-pdf
  (let [filled (filled-pdf pdf "test")]
    (is (= "Testy" (p/field-value pdf "autofill/first_name")))
    (is (= "McTesterson" (p/field-value pdf "autofill/last_name")))))
