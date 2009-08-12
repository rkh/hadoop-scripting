set terminal png
set output "benchmark.png"
set grid
set logscale xy
set xlabel "Input Size MiB"
set ylabel "Time hh:mm:ss"
set xrange [9.0:10000]
set ydata time
set timefmt "%H:%M:%S"
plot "< ruby1.9 calc.rb java.dat" using 1:2:3:4 title "" with yerrorbars, "< ruby1.9 calc.rb java.dat" using 1:2 with linespoints title "Java", "< ruby1.9 calc.rb jaql.dat" using 1:2:3:4 title "" with yerrorbars, "< ruby1.9 calc.rb jaql.dat" using 1:2 with linespoints title "Jaql", "< ruby1.9 calc.rb pig.dat" using 1:2:3:4 title "" with yerrorbars, "< ruby1.9 calc.rb pig.dat" using 1:2 with linespoints title "Pig"

