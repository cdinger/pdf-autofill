(ns pdf-autofill.markdown-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.markdown :refer :all]
            [clojure.java.io :as io]
            [endophile.core :as md]))

(def path-to-markdown-dir (io/file "dev-resources/fields"))
(def markdown-files (files path-to-markdown-dir))
(def first-name-file (first markdown-files))
(def first-name-parsed (parsed-file first-name-file))

(deftest test-files
  (is (= 2 (count markdown-files))))

(deftest test-parse
  (let [parsed (parsed-file (first markdown-files))]
    (is (not (nil? parsed)))))

(deftest test-title
  (is (= "first_name" (title first-name-parsed))))

(deftest test-description
  (is (= "Returns the current user's first name" (description first-name-parsed))))

(deftest test-sql
  (is (= "select firstname from users where username = :principal_id" (sql first-name-parsed))))

(deftest test-field
  (let [parsed (parsed-file (first markdown-files))
        f (field parsed)]
    (is (= (keys  f) '(:first_name)))
    (is (= (:description (:first_name f)) "Returns the current user's first name"))
    (is (= (:sql (:first_name f)) "select firstname from users where username = :principal_id"))))

(deftest test-fields
  (is (= '(:first_name :last_name) (keys (fields path-to-markdown-dir)))))
