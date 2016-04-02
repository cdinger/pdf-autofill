(ns pdf-autofill.config-test
  (:require [clojure.test :refer :all]
            [pdf-autofill.config :refer :all]))

;(deftest test-default-markdown-path
  ;(with-redefs [env {:markdown-path nil}]
    ;(= (markdown-path) (:markdown-path defaults))))

;(deftest test-env-markdown-path
  ;(let [fake-path "some/fake/path"]
    ;(with-redefs [env {:markdown-path fake-path}]
      ;(= fake-path (markdown-path)))))

(deftest test-default-database-spec
  (with-redefs [env {:database-spec nil}]
    (= database-spec (:database-spec defaults))))

(deftest test-env-database-spec
  (let [fake-spec {:url "blah"}]
    (with-redefs [env {:database-spec fake-spec}]
      (= fake-spec database-spec))))
