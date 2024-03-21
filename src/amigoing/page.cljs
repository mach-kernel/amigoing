(ns amigoing.page
  (:require
   [amigoing.components.form :refer [form]]
   [amigoing.components.flight :refer [flight]]
   [amigoing.events :as events]
   [re-frame.core :as re-frame]
   ["flowbite-react" :refer [Alert Spinner]]
   ["react-icons/fa" :refer [FaTimesCircle]]))

(defn page
  []
  (let [{:keys [loading flights error query]} @(re-frame/subscribe [::events/state])
        matched-flight (first flights)]
    [:<>
     [:h1 {:class ["delay-50"
                   "p-4"
                   "text-8xl"
                   "font-bold"
                   "subpixel-antialiased"
                   "text-center"
                   "text-sky-400"]}
      "am i going?"]
     (when (and query (not loading) (or error (not matched-flight)))
       [:> Alert
        {:class ["max-w-lg" "self-center"]
         :color "failure"
         :icon FaTimesCircle}
        "We couldn't find that flight. Try again?"])
     [form {:class ["w-full"
                    "max-w-2xl"
                    "self-center"
                    "py-10"]
            :query query}]
     (when loading
       [:span {:class :self-center}
        [:> Spinner {:size :xl :class "my-6"}]])
     (when matched-flight
       [flight {:class ["w-full" 
                        "max-w-2xl"
                        "self-center"
                        "my-10"]
                :flight matched-flight}])]))
