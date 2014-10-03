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
