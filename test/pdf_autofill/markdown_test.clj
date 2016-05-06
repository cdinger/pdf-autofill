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
  (is (= "select first_name
from ps_names n
where eff_status = 'A'
  and name_type = 'PRI'
  and effdt = (
    select max(effdt)
    from ps_names
    where emplid = n.emplid
      and name_type = n.name_type
      and effdt <= sysdate
  )\n" (sql first-name-parsed))))

(deftest test-field
  (let [parsed (parsed-file (first markdown-files))
        f (field parsed)]
    (is (= (keys  f) '(:first_name)))
    (is (= (:description (:first_name f)) "Returns the current user's first name"))
    (is (= (:sql (:first_name f)) "select first_name
from ps_names n
where eff_status = 'A'
  and name_type = 'PRI'
  and effdt = (
    select max(effdt)
    from ps_names
    where emplid = n.emplid
      and name_type = n.name_type
      and effdt <= sysdate
  )\n"))))

(deftest test-fields
  (is (= '(:first_name :last_name) (keys (fields path-to-markdown-dir)))))
