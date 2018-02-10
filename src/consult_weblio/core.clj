(ns consult-weblio.core
  (:gen-class)
  (:require [clojure.string :as string]))

(defn fetch
  [word]
  (let [base-url "https://ejje.weblio.jp/content/"]
      (.get
        (org.jsoup.Jsoup/connect (str base-url word)))))
  
(defn edit
  [doc]
  (let [ext-words "#h1Query, .content-explanation, .phoneticEjjeDesc, .qotC"]
    (.select doc ext-words)))

(defn consult-weblio
  [word]
  (doseq [elem (edit (fetch word))]
    (let [lowtext (.text elem)
          text (string/replace lowtext #"例文帳に追加" "\n") ]
      (if (not= "" text) (println text)))))

(defn -main
  [& args]
  (consult-weblio (first args)))
