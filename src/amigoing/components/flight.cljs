(ns amigoing.components.flight
  (:require 
   [ajax.url :refer [params-to-str]]
   [re-frame.core :as re-frame]
   ["flowbite-react" :refer [Button Card Table]]
   ["react-icons/fa" :refer [FaCheckCircle FaTimesCircle FaPaperPlane]]))

(def TableBody
  (.-Body Table))

(def TableRow
  (.-Row Table))

(def TableCell
  (.-Cell Table))

(defn cell
  [& children]
  [:> TableCell {:class [:font-bold]} children])

(defn- share-href
  [going?]
  (str
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
      (.-href js/location))})))

(defn- going
  [going?]
  [:div {:class [:flex
                 :flex-row
                 :justify-center
                 :items-center
                 :text-3xl
                 (if going?
                   :text-emerald-400
                   :text-red-400)]}
   (if going?
     [:> FaCheckCircle]
     [:> FaTimesCircle])
   [:h2 {:class [:delay-50
                 :p-2
                 :text-4xl
                 :font-bold
                 :subpixel-antialiased
                 :text-center]}
    (if going?
      "i'm going!"
      "i'm not going!")]])

(defn- inflect-airport
  [{:keys [name] {:keys [icao]} :code}]
  (str \( icao \) " " name))

(defn flight
  [& {:keys [class flight]}]
  (let [{{airline-name :name}
         :airline
         
         {{plane-model :text} :model}
         :aircraft
         
         {:keys [origin destination]}
         :airport} flight
        going? (not
                (re-matches #"(?i)boeing.*" plane-model))]
    [:> Card {:class (conj class :bg-light)}
     [going going?]
     [:> Button
      {:class [:max-w-24 :self-center] 
       :size :xs
       :href (share-href going?)
       :target "_blank"}
      [:> FaPaperPlane {:class :mr-2}] "Share"]
     [:> Table {:hoverable true
                :striped true
                :theme {:root {:shadow :none}}}
      [:> TableBody
       [:> TableRow
        [cell "Aircraft"]
        [:> TableCell plane-model]]
       [:> TableRow
        [cell "Carrier"]
        [:> TableCell airline-name]]
       [:> TableRow
        [cell "From"]
        [:> TableCell
         (inflect-airport origin)]]
       [:> TableRow
        [cell "To"]
        [:> TableCell
         (inflect-airport destination)]]]]]))