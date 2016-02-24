(ns pdf-autofill.db-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.db :refer :all]))

(deftest test-query-no-params
  (let [results (query "select username from users")]
    (is (= 3 (count results)))
    (is (= "test" (get (first results) :username)))))

(deftest test-query-with-params
  (let [results (query "select username from users where username = :username" "asdf")]
    (is (= 1 (count results)))
    (is (= "asdf" (get (first results) :username)))))

(deftest test-query-one-no-params
  (let [result (query-one "select firstname from users where username = 'test'")]
    (is (= "Testy" result))))

(deftest test-query-one-with-params
  (let [result (query-one "select username from users where username = :username" "blah")]
    (is (= "blah" result))))
