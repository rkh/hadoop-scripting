set term post eps enh dl 5 "Helvetica"
set output "wordcount.eps"
set grid
set xlabel "Input Size MiB"
set ylabel "Time hh:mm:ss"
set xrange [10:5000]
#set logscale xy 
set ydata time
set timefmt "%H:%M:%S"
set key left


plot  "java_wordcount.dat" using 1:2 smooth bezier with lines title "Java" lw 2, \
      "jaql_wordcount.dat" using 1:2 smooth bezier with lines title "Jaql" lw 2, \
      "pig_wordcount.dat" using 1:2 smooth bezier with lines title "Pig" lw 2

