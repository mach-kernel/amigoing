(ns amigoing.components.form
  (:require 
   [amigoing.util :refer [debounce tw]]
   [amigoing.events :as events]
   ["flowbite-react" :refer [Label TextInput]]
   ["react-icons/fa" :refer [FaPlane]]
   [re-frame.core :as re-frame]))

(defn form
  [& {:keys [class]}]
  [:div {:class (str class " " (tw :flex :flex-col :text-center))}
   [:> Label {:class [:text-2xl :font-bold]} "Enter your flight number"]
   [:> TextInput
    {:class [:self-center
             :my-4
             :w-full
             :max-w-48
             :text-2xl
             :font-semibold]
     :style {:text-align :center}
     :icon FaPlane
     :sizing "lg"
     :required true
     :shadow true
     :on-focus (fn [e]
                 (set! (-> e .-target .-value) "")
                 (re-frame/dispatch [::events/clear]))
     :on-change (debounce 250
                          #(re-frame/dispatch
                            [::events/search (-> % .-target .-value)]))}]])