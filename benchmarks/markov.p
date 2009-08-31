set term post eps enh dl 5 "Helvetica"    
set output "markov.eps"
set grid
set xlabel "Input Size MiB"
set ylabel "Time hh:mm:ss"
set xrange [10:2000]
#set logscale xy 
set ydata time
set timefmt "%H:%M:%S"
set key left


plot  "java_markov.dat" using 1:2 smooth bezier with lines title "Java" lt 1 lw 2, \
      "pig_markov.dat" using 1:2 smooth bezier with lines title "Pig"  lt 3 lw 2

