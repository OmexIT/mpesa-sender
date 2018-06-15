(ns mpesa-sender.mpesa.service
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clj-time.local :as l]
            [clj-time.coerce :as c]
            [clojure.tools.logging :as log]))

(def authentication-token (atom {:token {:access_token {} :expires_in "0"} :start (c/to-long (l/local-now))}))

(defn token-expired? [start expiry]
  (if (<= expiry (- (c/to-long (l/local-now)) start 200))
    true false))

(defn get-authentication-token []
  "
  Gets oauth 2 token from safaricom if our stored token has expired
  "
  (let [start-time (:start @authentication-token)
        token (:token @authentication-token)]
    (if-not (token-expired? start-time (Long/valueOf (:expires_in token)))
      token
      (do
        (log/info "Token expired!")
        (let [consumer-key "Dlj3gRjIoMNu1NMcY73rGQDm2GnjRFsn"
              consumer-secret "kI1Rty8GACojXbYr"
              endpoint "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"
              {:keys [body]}
              (client/get endpoint {:basic-auth [consumer-key consumer-secret]})]
          (swap! authentication-token assoc
                 :token (json/read-str body :key-fn keyword)
                 :start (c/to-long (l/local-now)))
          (:token @authentication-token))))))

(defn safaricom-b2c [params]
  "Call mpesa b2c api"
  (let [{:keys [body]}
        (clj-http.client/post
          "https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest"
          {
           :headers     {"Content-Type" "application/json"}
           :oauth-token (get-authentication-token)
           :body        (json/write-str
                          {
                           :InitiatorName      "safaricom.5"
                           :SecurityCredential "PDkmLcfBAaXbyesG3OJzaFVYouuCMWFIitL9XSgeYvQRYLLQkslfh0Ba3Pbe6TtnOTvr4bgkuol6qMz7CB7zo+MMtRPqJeumKb1H0tFhAg5Qlejf5/A/5g9X+rTiuoCIeoKv9wziuL49DaTP1Xte4junVSC0XjY/y2meUpFxqLhZe5ie8+KkvIMPeruJtoTP8knbiZTVmEQV5Kp/ANW2L6YJWttIc5eTP1wDFOXSQ+h7Y6ncOzVRVUKwoOCXVLC1I5SIGoRqiBdR8I/ksD1grQXIL81p2VTNSRjX3cbQQSP4Zu5ryk5oX0pOxZBVhdr8FbnFBRihHPypcwWpA1xneg=="
                           :CommandID          "BusinessPayment"
                           :Amount             (:amount params)
                           :PartyA             "600000"
                           :PartyB             (:msisdn params)
                           :Remarks            (:remarks params)
                           :QueueTimeOutURL    "http://your_timeout_url"
                           :ResultURL          "http://your_result_url"
                           :Occasion           "Test"
                           })
           })]
    (json/read-str body :key-fn keyword)))