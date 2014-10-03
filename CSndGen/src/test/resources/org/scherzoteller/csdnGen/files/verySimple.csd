
<CsoundSynthesizer>
<CsOptions>
</CsOptions>
<CsInstruments>

; http://www.csounds.com/chapter1/#SoundDesignEtude1
; http://www.csounds.com/journal/issue14/CsoundWorkbench.html

sr = 44100
ksmps = 10
nchnls = 1
instr 1 
iamp  =      p4               ; global amplitude
ifreq =      p5               ; frequency
aout  oscil  iamp, ifreq, 1   ; basic oscillator
      out    aout             ; output the sound
endin

instr 2 
iamp  =      p4               ; global amplitude
ifreq =      p5               ; frequency
aout  oscil  iamp, ifreq, 2   ; basic oscillator
      out    aout             ; output the sound
endin

instr 3 
iamp  =      p4               ; global amplitude
ifreq =      p5               ; frequency
aout  oscil  iamp, ifreq, 3   ; basic oscillator
      out    aout             ; output the sound
endin

instr 4 
iamp  =      p4               ; global amplitude
ifreq =      p5               ; frequency
aout  oscil  iamp, ifreq, 4   ; basic oscillator
      out    aout             ; output the sound
endin
</CsInstruments>

<CsScore>
;; cf. http://en.flossmanuals.net/csound/d-function-tables/
;; http://www.csounds.com/manual/html/f.html
f 1 0 4096 10 1 ; sine wave
f 2 0 16384 7  0 4096 1 8192 -1 4097 0 ; triangle
f 3 0 16384 7  -1 16385 1              ; sawtooth
f 4 0 16384 7  1 8192 1 0 -1 8192 -1   ; square

;;p1 -- Table number by which the stored function will be known. A negative number requests that the table be destroyed.
;;p2 -- Action time of function generation (or destruction) in beats.
;;p3 -- Size of function table (i.e. number of points) Must be a power of 2, or a power-of-2 plus 1 if this number is positive. Maximum table size is 16777216 (224) points.
;;p4 -- Number of the GEN routine to be called (see GEN ROUTINES). A negative value will cause rescaling to be omitted.
;;p5 ... PMAX -- Parameters whose meaning is determined by the particular GEN routine.

;; see gens here... http://www.csounds.com/manual/html/ScoreGenRef.html 

;; NB a4 is 440...

;iid | sta  | dur  | amp   | freq
i 1    0      .5     10000   440
i 1    +      .      .       460
i 1    +      .      .       480
i 1    +      .25    .       460
i 1    +      2      .       440
i 1    ^+.25  1.75   .       220
i 1    ^+.25  1.5    .       660
i 2    ^+.25  1.75   .       220
i 2    ^+.25  1.5    .       660
i 3    ^+.25  1.75   .       220
i 3    ^+.25  1.5    .       660
i 4    ^+.25  1.75   .       220
i 4    ^+.25  1.5    .       660
e
</CsScore>

</CsoundSynthesizer>