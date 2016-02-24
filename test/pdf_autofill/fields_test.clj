(ns pdf-autofill.fields-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.fields :as f]))

(deftest test-sql
  (let [sql (f/sql "firstname")]
    (is (= "select firstname\nfrom users\nwhere username = :principal_id\n" sql))))
