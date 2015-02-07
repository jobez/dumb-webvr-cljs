(ns dumb-webvr-demo.app
  (:require-macros [dumb-webvr-demo.macros :refer [+=]])
  (:require [goog.debug :as debug]))

(enable-console-print!)

(defn grab-nested-attr [obj attr-name]
  (aget obj attr-name))

(def window (partial grab-nested-attr js/window))
(def grab-three (partial grab-nested-attr js/THREE))

(def width (window "innerWidth"))
(def height (window "innerHeight"))

(def scene-class (grab-three "Scene"))
(def web-gl-renderer-class (grab-three "WebGLRenderer"))
(def perspective-camera-class (grab-three "PerspectiveCamera"))

(def vr-controls (grab-three "VRControls"))
(def vr-effects  (grab-three "VREffect"))


(def box-geometry (grab-three "BoxGeometry"))
(def mesh-normal-material (grab-three "MeshNormalMaterial"))
(def mesh (grab-three "Mesh"))

(def renderer (web-gl-renderer-class. #js {:antialias true}))

;; create a three.js scene
(def scene (scene-class.))


;; create three.js camera
(def camera (perspective-camera-class. 75 (/ width height) 1 10000))

;; apply vr headset positional data to camera
(def controls (vr-controls. camera))

;; apply vr stereo rendering to renderer
(def effect (vr-effects. renderer))

(.. effect (setSize width height))
;; create a vr manager helper to enter and exit vr mode
(def vrmgr (js/WebVRManager. effect))

;; create 3d objects
(def geometry (box-geometry. 10 10 10))
(def material (mesh-normal-material.))
(def cube (mesh. geometry material))


(.. scene (add cube))
(set! (.. cube -position -z) -20)

(defn animate! []
  (let [rotation-state-val (.. cube -rotation -y)]
      #_(+= (.. cube -rotation -y) 0.01)
      (set! (.. cube -rotation -y) (+ rotation-state-val 0.01))
      (.. controls update)

      (if (.. vrmgr isVRMode)
        (.. effect (render scene camera))
        (.. renderer (render scene camera)))

      (js/requestAnimationFrame animate!)))

(animate!)

(defn on-key [event]
  (when (= 90 (.-keyCode event))
    (.. controls zeroSensor)))

(defn on-window-resize []
  (let [width (window "innerWidth")
        height (window "innerHeight")]
      (set! (.. camera -aspect) (/ width height))
    (.. camera updateProjectionMatrix)
    (.. effect (setSize width height))))

(.. js/window (addEventListener "keydown" on-key true))
(.. js/window (addEventListener "resize" on-window-resize false))

(defn init []
  (let [canvas (.-domElement renderer)]
  ;; Append the canvas element created by the renderer to document body element.
    (.. js/document -body (appendChild canvas))))


