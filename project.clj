(defproject pdf-autofill "1.0.0"
  :description "A clojure web service that automatically fills PDFs based on user-defined queries"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :resource-paths ["resources" "lib/ojdbc6.jar"]
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [enlive "1.1.6"]
                 [com.mchange/c3p0 "0.9.5.2"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.apache.pdfbox/pdfbox "2.0.1"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.2"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [endophile "0.1.2"]
                 [clj-aws-s3 "0.3.10"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-auto "0.1.2"]]
  :ring {:handler pdf-autofill.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]
         :resource-paths ["test/resources"]}})
