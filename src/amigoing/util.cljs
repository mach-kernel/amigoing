(ns amigoing.util
  (:require [clojure.string :as str]))

(defn tw
  "Makes class names string"
  [& classes]
  (->> classes 
       (map name)
       (str/join " ")))

(defn debounce
  [ms f]
  (let [timeout (atom nil)
        latest (atom nil)
        callback-f #(apply f @latest)]
    (fn [& args]
      (js/clearTimeout @timeout)
      (reset! timeout (js/setTimeout callback-f ms))
      (reset! latest args))))
