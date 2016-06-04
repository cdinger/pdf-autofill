(ns pdf-autofill.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.io :refer :all]
            [ring.adapter.jetty :as jetty]
            [net.cgrand.enlive-html :as html]
            [pdf-autofill.fields :refer :all]
            [pdf-autofill.autofill :as autofill]
            [pdf-autofill.pdf :as pdf]
            [pdf-autofill.config :as config]))

(html/deftemplate index config/template-path [fs]
  [:tbody :tr] (html/clone-for [f fs]
                 [:td.fieldname] (html/content (str autofill/prefix (name (first f))))
                 [:td.description] (html/content (:description (last f)))))

(defn fill-pdf [url principal]
  (let [doc (pdf/document url)]
    (piped-input-stream
      (fn [output-stream]
        (pdf/save-pdf-to-stream (autofill/filled-pdf doc principal) output-stream)))))

(defn url->filename [url]
  (re-find #"(?<=\/)[^\/]*$" url))

(defn pdf-response [pdf filename]
  {:headers {"Content-Type" "application/pdf"
             "Content-Disposition" (str "attachment; filename=\"" filename "\"")}
   :body pdf})

(defroutes app-routes
  (GET "/" [] (apply str (index (sort fields))))
  (GET "/headers" {headers :headers} (str headers))
  (GET "/fill" {headers :headers {url :url} :params} (pdf-response (fill-pdf url (get headers config/principal-header)) (url->filename url)))
  (GET "/sample.pdf" [] (pdf-response (slurp (io/resource "hello_forms.pdf")) "test.pdf"))
  (route/resources "/")
  (route/not-found (slurp (io/resource "public/404.html"))))

(def app
  (wrap-defaults app-routes site-defaults))

(defn start-server []
  (jetty/run-jetty app {:port (Integer. (or (System/getenv "PORT") 5000)) :join? false}))

(defn -main [& args]
  (start-server))
