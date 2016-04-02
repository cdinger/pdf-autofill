(ns pdf-autofill.config)

(def defaults {:database-spec "jdbc:sqlite:test/resources/test.sqlite3"})

(def env {:database-spec (when-let [envspec (System/getenv "DATABASE_SPEC")]
                           (read-string envspec))})

(def database-spec (or (:database-spec env) (:database-spec defaults)))
