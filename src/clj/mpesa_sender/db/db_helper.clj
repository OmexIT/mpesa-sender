(ns mpesa-sender.db.db-helper
  (:require [mpesa-sender.db.core :as db]))

(defn save-payment [params-list]
  (map (fn [params]
         (let [current-time (java.util.Date.)
               data {:id (str (java.util.UUID/randomUUID))
                     :full_name (:name params),
                     :msisdn (:phone_number params),
                     :amount (:amount params),
                     :status 0,
                     :external_txn_id nil,
                     :mpesa_response_code nil,
                     :mpesa_conversation_id nil,
                     :mpesa_originator_conversation_id nil,
                     :mpesa_response_description nil,
                     :user_id ""}]
           (db/create-payment! (assoc data :date_created current-time :last_modified current-time)))) params-list))

(defn get-payments []
  (db/get-payments))