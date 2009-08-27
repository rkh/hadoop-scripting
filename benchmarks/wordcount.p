set terminal png
set output "wordcount.png"
set grid
set xlabel "Input Size MiB"
set ylabel "Time hh:mm:ss"
set xrange [10:5000]
#set logscale xy 
set ydata time
set timefmt "%H:%M:%S"


plot  "java_wordcount.dat" using 1:2 smooth bezier with lines title "Java" lt 1 lw 2, \
      "jaql_wordcount.dat" using 1:2 smooth bezier with lines title "Jaql" lt 2 lw 2, \
      "pig_wordcount.dat" using 1:2 smooth bezier with lines title "Pig"  lt 3 lw 2

