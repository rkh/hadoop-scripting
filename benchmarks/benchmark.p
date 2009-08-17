set terminal png
set output "benchmark.png"
set grid
set logscale xy
set xlabel "Input Size MiB"
set ylabel "Time hh:mm:ss"
set xrange [9.0:10000]
set ydata time
set timefmt "%H:%M:%S"

plot "java_r.dat" using 1:2:3:4 title "" with yerrorbars, \
        "java_r.dat" using 1:2 smooth csplines with lines title "Java", \
        "jaql_r.dat" using 1:2:3:4 title "" with yerrorbars, \
        "jaql_r.dat" using 1:2 with lines title "Jaql", \
        "pig_r.dat" using 1:2:3:4 title "" with yerrorbars, \
        "pig_r.dat" using 1:2 with lines title "Pig"

