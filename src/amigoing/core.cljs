(ns amigoing.core
  (:require
   [amigoing.events :as events]
   [amigoing.page :refer [page]]
   [day8.re-frame.http-fx]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   ["flowbite-react" :refer [Flowbite DarkThemeToggle Footer]]))

(def debug?
  ^boolean goog.DEBUG)

(def layout
  [:>
   Flowbite
   [:div {:class "flex flex-col justify-between h-screen"}
    [:div {:class "flex flex-col size-full p-6"}
     [:span [:> DarkThemeToggle]]
     [page]]
    [:> Footer {:container true}
     [:> (.-Copyright Footer) 
      {:by "a happy and mentally stable anon who loves their life"
       :year 2024}]
     [:> (.-LinkGroup Footer)
      [:> (.-Link Footer) {:href "https://www.flightradar24.com"}
       "Data © FlightRadar24"]]]]])

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render layout root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (mount-root))
