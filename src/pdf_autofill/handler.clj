(ns pdf-autofill.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.io :refer :all]
            [net.cgrand.enlive-html :as html]
            [pdf-autofill.fields :refer :all]
            [pdf-autofill.pdf :as pdf]))

(html/deftemplate index "public/index.html" [text]
  [:h1] (html/content (-> fields
                          :random-number
                          :description)))

(defn fill-pdf [url]
  (let [doc (pdf/document url)]
    (do
      (pdf/set-values doc {"autofill/last_name" "Blahblah"})
      (piped-input-stream
        (fn [output-stream]
          (pdf/save-pdf-to-stream doc output-stream))))))

(defn url->filename [url]
  (re-find #"(?<=\/)[^\/]*$" url))

(defn handle-fill [url]
  {:headers {"Content-Type" "application/pdf"
             "Content-Disposition" (str "attachment; filename=\"" (url->filename url) "\"")}
   :body (fill-pdf url)})

(defroutes app-routes
  (GET "/" [] (apply str (index "PDF Autofill service")))
  (GET "/fill" {{url :url} :params} (handle-fill url))
  (route/not-found (slurp (io/resource "public/404.html"))))

(def app
  (wrap-defaults app-routes site-defaults))
