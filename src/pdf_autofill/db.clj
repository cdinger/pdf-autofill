(ns pdf-autofill.db
  (:require [clojure.java.jdbc :as jdbc]
            [jdbc.pool.c3p0 :as pool]))

(def db (pool/make-datasource-spec {:classname   "oracle.jdbc.OracleDriver" ; must be in classpath
                                    :subprotocol "oracle"
                                    :subname     "thin:@host:1521/service"
                                    :user        "someuser"
                                    :password    "somepassword"}))

(defn query [sql & parameters]
  (jdbc/query db (concat [sql] parameters)))

(defn query-one [sql & parameters]
  (let [results (apply query (concat [sql] parameters))
        first-result (first results)
        first-values (vals first-result)
        first-value (first first-values)]
    first-value))
