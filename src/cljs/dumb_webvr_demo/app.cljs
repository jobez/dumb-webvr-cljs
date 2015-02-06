(ns dumb-webvr-demo.app
  (:require [goog.debug :as debug]))

(def three js/THREE)
(def init identity)
(def width (.. js/window -innerWidth))
(def height (.. js/window -innerHeight))
(def webglrenderer (.. three (WebGLRenderer. (clj-js {:antialias true}))))



(comment 
  
  
  
  
  

  ;; append the canvas element created by the renderer to document body element


  (.. js/console (log (debug/expose renderer)))
  (.. document body (appendChild ()))


  (defn init []
    
    #_(let [c (.. js/document (createElement "DIV"))]
        (.. js/document (getElementById "container") (appendChild c))))


  (.. js/console (log renderer))
  )
