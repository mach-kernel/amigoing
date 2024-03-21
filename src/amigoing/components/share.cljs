(ns amigoing.components.share
  (:require 
   [ajax.url :refer [params-to-str]]
   ["flowbite-react" :refer [Button]]
   ["react-icons/fa" :refer [FaPaperPlane]]))

(defn share
  [going?]
  (let [url (str
             "https://twitter.com/intent/tweet?"
             (params-to-str
              :java
              {:text
               (str
                "Looks like I'm "
                (when-not going?
                  "not ")
                "going!"
                \newline
                (.-href js/location))}))]
    [:> Button
     {:class ["max-w-24" "self-center"]
      :size :xs
      :href url
      :target "_blank"}
     [:> FaPaperPlane {:class "mr-2"}] "Share"]))
