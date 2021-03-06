(defproject clones "0.1"
  :description "An NES emulator with style"
  :url "http://example.com/FIXME"
  :license {:name "GPLv3"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/algo.monads "0.1.4"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}
             :emu {:main clones.nes
                   :uberjar-name "clones.jar"}}
  :plugins [[speclj "2.7.0"]]
  :test-paths ["spec"])
