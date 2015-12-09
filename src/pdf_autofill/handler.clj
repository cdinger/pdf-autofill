(ns pdf-autofill.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/:blah" [blah]
    (str "<h1>Hello, " blah "</h1>"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
