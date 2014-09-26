<CsoundSynthesizer>
<CsOptions>
csound -Wo C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\test.wav C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\temp.orc C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\temp.sco 
</CsOptions>
<!--
csound -RWfo C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\test.wav C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\temp.orc C:\Graphane\eclipseKeplerScala\workspace\Generate\csndFiles\temp.sco 
-->
<CsInstruments>
sr = 44100
ksmps = 10
nchnls = 1
instr 1 
iamp  =      p4               ; global amplitude
ifreq =      p5               ; frequency
aout  oscil  iamp, ifreq, 1   ; basic oscillator
      out    aout             ; output the sound
endin
</CsInstruments>

<CsScore>
f 1 0 4096 10 1 ; sine wave
;f 1 0 16384 7  0 4096 1 8192 -1 4097 0 ; triangle
;f 1 0 16384 7  -1 16385 1              ; sawtooth
;f 1 0 16384 7  1 8192 1 0 -1 8192 -1   ; square

;; NB a4 is 440...

;iid | sta  | dur  | amp   | freq
i 1    0      .5     10000   440
i 1    +      .      .       460
i 1    +      .      .       480
i 1    +      .25    .       460
i 1    +      2      .       440
i 1    ^+.25  1.75   .       220
i 1    ^+.25  1.5    .       660
e
</CsScore>

</CsoundSynthesizer>