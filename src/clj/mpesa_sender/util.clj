(ns mpesa-sender.util
  (:import [java.io File FileInputStream FileOutputStream InputStreamReader BufferedReader]
           (org.apache.commons.io FilenameUtils))
  (:require [clojure.tools.logging :as log]
            [clojure.data.csv :as csv]
            [semantic-csv.core :as sc :refer :all]
            [clojure.java.io :as io]
            [mpesa-sender.db.db-helper :refer [save-payment]]))

(def payment-data-key (vector :name :phone_number :amount))

(defn file-path [path & [filename]]
  (java.net.URLDecoder/decode
    (str path File/separator filename)
    "utf-8"))


(defn read-csv-file
  [file]
  (with-open [in-file (io/reader file)]
    (let [data (->> (csv/read-csv in-file :separator \,)
                    remove-comments
                    ;mappify
                    (map (fn [x] (into [] (map (fn [c] (if (empty? (clojure.string/trim c)) nil c)) x))))
                    doall)
          file-name (FilenameUtils/getName (.getAbsolutePath (io/file file)))]
      (map (fn [c] (into {} (map hash-map payment-data-key c))) data))
    ))


(defn upload-file
  "uploads a file to the target folder
   when :create-path? flag is set to true then the target path will be created"
  [path {:keys [tempfile size filename]}]
  (prn (-> tempfile
           read-csv-file
           save-payment)))