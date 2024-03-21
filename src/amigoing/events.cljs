(ns amigoing.events
  (:require 
   [ajax.core :as ajax]
   [ajax.url :refer [params-to-str]]
   [re-frame.core :as re-frame]))

(def fr-api-url
  "https://api.flightradar24.com")

(def cors-proxy-url
  "https://lulcors-299a748b62c4.herokuapp.com/fetch/")

(defn encode-request
  "A bunch of the un-CORSing services take a param of the
   whole query URI, so this makes a URI-safe URI (!)"
  [url params]
  (str url \? (params-to-str :java params)))

(defn update-query
  [query]
  (let [url (new js/URL js/location)]
    (if query
      (-> url .-searchParams (.set "q" query))
      (-> url .-searchParams (.delete "q")))
    (.pushState js/history nil "" (.toString url))))

(re-frame/reg-event-db
 ::init-db
 (fn [& _]
   {:loading false
    :flight nil}))

(re-frame/reg-event-fx
 ::clear
 (fn [{:keys [db]}]
   (println "clearing")
   (update-query nil)
   {:db (assoc db :flights nil :loading false :query nil)}))

(re-frame/reg-event-fx
 ::search
 (fn [{:keys [db]} [_ q]]
   (let [fr24-request (encode-request (str fr-api-url "/common/v1/flight/list.json")
                                      {:query q :fetchBy "flight"})]
     (update-query q)
     {:db (assoc db :loading true :query q)
      :http-xhrio {:method :get
                   :uri (str cors-proxy-url fr24-request)
                   :format (ajax/json-request-format)
                   :response-format (ajax/json-response-format {:keywords? true})
                   :with-credentials false
                   :on-success [::search-ok]
                   :on-failure [::search-ok]}})))

(re-frame/reg-event-db
 ::search-ok
 (fn [db [_ res]]
   (let [flights (get-in res [:result :response :data])
         aircraft-images (get-in res [:result :response :aircraftImages])]
     (merge db {:flights
                (->> flights
                     (filter (comp :registration :aircraft))
                     (sort-by (comp :departure :scheduled :time) >))

                :aircraft-images
                (group-by :registration aircraft-images)

                :loading false}))))

(re-frame/reg-sub
 ::state
 (fn [db _]
   db))