(ns pdf-autofill.fields)

(def fields {:first_name {:description "Returns the current user's first name."
                          :sql         "select firstname from users where username = :principal_id"}
             :last_name  {:description "Returns the current user's last name."
                          :sql         "select lastname from users where username = :principal_id"}
             :malformed  {:asdf "blah"}})

(defn sql [fieldname]
  (get (get fields (keyword fieldname)) :sql))

