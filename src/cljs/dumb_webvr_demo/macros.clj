(ns dumb-webvr-demo.macros)

(defmacro += [attr amt]
  `(let [val ~attr
         new-val (+ val amt)]
     (set! attr new-val)))
