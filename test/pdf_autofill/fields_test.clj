(ns pdf-autofill.fields-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.fields :as f]))

(deftest test-sql
  (let [sql (f/sql "first_name")]
    (is (= "select firstname from users where username = :principal_id" sql))))

(deftest test-malformed
  (let [sql (f/sql "malformed")]
    (is (= nil sql))))

;(deftest test-list-markdown-files
  ;(let [fields (map #(.getName %) f/markdown-files)]
    ;(is (= ("first_name.md" "last_name.md" "malformed.md") fields))))
