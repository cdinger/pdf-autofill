(ns pdf-autofill.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.io :refer :all]
            [net.cgrand.enlive-html :as html]
            [pdf-autofill.fields :refer :all]
            [pdf-autofill.autofill :as autofill]
            [pdf-autofill.pdf :as pdf]))

(html/deftemplate index "public/index.html" [text]
  [:h1] (html/content (-> {:random-number {:sql "select blah from blah"
                                           :description "This is a blah."}}
                          :random-number
                          :description)))

(defn fill-pdf [url]
  (let [doc (pdf/document url)]
    (piped-input-stream
      (fn [output-stream]
        ;(-> (autofill/filled-pdf doc "test")
            ;(pdf/save-pdf-to-stream output-stream))))))
        (pdf/save-pdf-to-stream (autofill/filled-pdf doc "test") output-stream)))))

(defn url->filename [url]
  (re-find #"(?<=\/)[^\/]*$" url))

(defn pdf-response [pdf filename]
  {:headers {"Content-Type" "application/pdf"
             "Content-Disposition" (str "attachment; filename=\"" filename "\"")}
   :body pdf})


(defroutes app-routes
  (GET "/" [] (apply str (index "PDF Autofill service")))
  (GET "/fill" {{url :url} :params} (pdf-response (fill-pdf url) (url->filename url)))
  (GET "/sample.pdf" [] (pdf-response (slurp (io/resource "hello_forms.pdf")) "test.pdf"))
  (route/not-found (slurp (io/resource "public/404.html"))))

(def app
  (wrap-defaults app-routes site-defaults))
