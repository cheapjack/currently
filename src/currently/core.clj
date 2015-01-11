(ns currently.core
  ( :use [overtone.live]))

(definst trem [mult 0.3 freq 440 depth 10 rate 6 length 3]
    (* 0.3
       (line:kr 0 mult length FREE)
       (saw (+ freq (* depth (sin-osc:kr rate))))))

(def freqs  (range 400 600 5))

(def vols (concat (range 0 1 0.1)(range 1 0 -0.1)))

(map #(trem :length 1 :freq %1 :mult %2) freqs vols)

(definst trem1 [mult 0.3 freq 440 depth 10 rate 6 length 3]
    (*
       (vols)
       (saw (+ freq (* depth (sin-osc:kr rate))))))
(def snare (sample (freesound-path 26903)))

(def shrimp (load-sample "~/Documents/CURRENTLY/CURRENTLYOSLO/reeffish01.wav"))

(def water (sample (freesound-path 50623)))


(defsynth reverb-on-left [rs 1]
  (let [dry (hpf (play-buf 1 water :loop 1) (mouse-x 40 5000 EXP))
	wet (free-verb dry 1 rs 0)]
    (out 0 [wet dry])))

(definst reverb-on-left1 [rs 1]
  (let [dry (play-buf 1 water :loop 1)
	wet (free-verb dry 1 (mouse-y  0 1 EXP) 0)]
    (out 0 [wet dry])))

(reverb-on-left1)

(ctl reverb-on-left :rs 0.5)

(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))
