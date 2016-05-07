(ns pdf-autofill.s3
  (:require [aws.sdk.s3 :as s3]
            [pdf-autofill.config :as config]
            [pdf-autofill.markdown :as md]))

(def cred {:access-key config/s3-access-key :secret-key config/s3-secret-key})

(defn files
  ([credentials bucket]
    (s3/list-objects credentials bucket))
  ([]
    (files cred config/s3-bucket)))

(defn all-keys []
  (map #(:key %1) (:objects (files))))

(defn parsed-files
  ([files]
    (md/parsed-files files))
  ([]
    (parsed-files (map #(:content (s3/get-object cred config/s3-bucket %1)) (all-keys)))))

(defn fields
  ([parsed-seq]
    (map #(md/field %1) parsed-seq))
  ([]
    (fields (parsed-files))))




