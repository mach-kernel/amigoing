(ns amigoing.page
  (:require
   [amigoing.components.form :refer [form]]
   [amigoing.components.flight :refer [flight]]
   [amigoing.events :as events]
   [amigoing.util :refer [tw]]
   [re-frame.core :as re-frame]
   ["flowbite-react" :refer [Spinner]]))

(defn page
  []
  (let [{:keys [loading flights]} @(re-frame/subscribe [::events/state])]
    [:<>
     [:h1 {:class [:delay-50
                   :p-4
                   :text-8xl
                   :font-bold
                   :subpixel-antialiased
                   :text-center
                   :text-sky-400]}
      "am i going?"]
     [form {:class [:w-full
                    :max-w-2xl
                    :self-center
                    :py-10]}]
     (when loading
       [:span {:class :self-center}
        [:> Spinner {:size :xl :class :my-6}]])
     (when-let [f (first flights)]
       [flight {:class [:w-full 
                        :max-w-2xl
                        :self-center
                        :my-10]
                :flight f}])]))
