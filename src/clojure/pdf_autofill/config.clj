(ns pdf-autofill.config
  (:require [clojure.java.io :as io]))

(def defaults {:database-spec {:subprotocol "sqlite"
                               :subname "test/resources/test.sqlite3"}
               :markdown-path "dev-resources/fields"
               :principal-header "remote_user"
               :template-path "public/index.html"})

(def env {:database-spec    (when-let [envspec (System/getenv "DATABASE_SPEC")]
                              (read-string envspec))
          :markdown-path    (System/getenv "MARKDOWN_PATH")
          :s3-bucket        (System/getenv "S3_BUCKET")
          :s3-access-key    (System/getenv "S3_ACCESS_KEY")
          :s3-secret-key    (System/getenv "S3_SECRET_KEY")
          :principal-header (System/getenv "PRINCIPAL_HEADER")
          :template-path    (io/file (System/getenv "TEMPLATE_PATH"))})

(def database-spec    (or (:database-spec env) (:database-spec defaults)))
(def markdown-path    (or (:markdown-path env) (:markdown-path defaults)))
(def s3-bucket        (or (:s3-bucket env) "pdf-autofill"))
(def s3-access-key    (:s3-access-key env))
(def s3-secret-key    (:s3-secret-key env))
(def principal-header (or (:principal-header env) (:principal-header defaults)))
(def template-path    (or (:template-path env) (:template-path defaults)))
