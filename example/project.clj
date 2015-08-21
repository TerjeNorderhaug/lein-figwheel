(defproject figwheel-example "0.2.2-SNAPSHOT"
  :description "Just an example of using the lein-figwheel plugin"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [sablono "0.3.5"]
                 [org.omcljs/om "0.8.8"]
                 [ankha "0.1.4"]
                 [datascript "0.9.0"]
                 [com.andrewmcveigh/cljs-time "0.3.11"]
                 [cljs-http "0.1.35"]]

  :plugins [[lein-ring "0.8.13"]
            [lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.8-SNAPSHOT"]
            [lein-npm "0.4.0"]]

  :node-dependencies [[source-map-support "0.2.8"]
                      [express "4.10.7"]
                      [serve-static "1.9.1"]
                      [body-parser "1.12.0"]
                      [type-is "1.6.0"]
                      [ws "0.7.1"]]

  ;; this is used for testing an external server
  :ring { :handler example.server/static-server }
  
  :source-paths ["src"] 

  ;; :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  
  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/example.js"
                                    "server_out"
                                    "server_out/todo_server.js"]

  :resource-paths ["resources" "other_resources"]

  :cljsbuild {
              :builds [{ :id "example"
                         :source-paths ["src" #_"dev" #_"tests" "../support/src"]
                        
                         :figwheel { :websocket-host "localhost"
                                     :on-jsload      "example.core/fig-reload"
                                     :debug true }
                        
                         :compiler { :main example.core
                                     :asset-path "js/out"
                                     :output-to "resources/public/js/example.js"
                                     :output-dir "resources/public/js/out"
                                     :source-map-timestamp true
                                     :libs []
                                     ;; :recompile-dependents true
                                     :optimizations :none}}
                       {:id "server"
                        :source-paths ["server_src" "../support/src"]
                        :figwheel true
                        :compiler {;; :main "todo-server.core"
                                   :output-to "server_out/todo_server.js"
                                   :output-dir "server_out"
                                   :target :nodejs
                                   :optimizations :none
                                   :cache-analysis true                                   
                                   :source-map true}}
                       { :id "example-admin"
                         :source-paths ["other_src" ]
                         :compiler { :output-to "resources/public/js/compiled/example_admin.js"
                                     :output-dir "resources/public/js/compiled/admin"
                                     :source-map true
                                     :optimizations :none
                                    }}
                       { :id "example-admin-prod"
                         :source-paths ["other_src" ]
                         :compiler { :output-to "resources/public/js/prod/example_admin.js"
                                     :output-dir "resources/public/js/prod/admin"
                                     :optimizations :whitespace
                                     }}]}

  :figwheel {
             :http-server-root "public" ;; default and assumes "resources" 
             :server-port 3449 ;; default
             :css-dirs ["resources/public/css"]
             :open-file-command "emacsclient"
             ;; Start an nREPL server into the running fighweel process
             :nrepl-port 7888
             ;; to disable to launched repl 
             ;; :repl false
             ;; to specify a server logfile
             ;; :server-logfile "tmp/logs/test-server-logfile.log"
             ;; if you want to embed a server in figwheel do it like so:
             ;; :ring-handler example.server/handler
             })
