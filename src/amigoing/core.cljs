(ns amigoing.core
  (:require
   [amigoing.events :as events]
   [amigoing.page :refer [page]]
   [day8.re-frame.http-fx]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   ["flowbite-react" :refer [Flowbite DarkThemeToggle ThemeModeScript Footer]]))

(def debug?
  ^boolean goog.DEBUG)

(def layout
  [:div {:class "flex flex-col size-full p-6"}])

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render 
     (conj layout [:> 
                   Flowbite
                   [:<> 
                    [:> DarkThemeToggle] 
                    [page]]]) 
     root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (mount-root))
