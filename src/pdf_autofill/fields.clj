(ns pdf-autofill.fields)

(def fields {:random-number {:description "Generates a random number"
                             :sql "select dbms_random.value from dual"}
             :today         {:description "Today's date"
                             :sql "select sysdate from dual"}
             :emplid        {:description "The current user's emplid"
                             :sql "select emplid from ps_hr.ps_personal_data where alter_emplid=upper(:current_principal)"}})


