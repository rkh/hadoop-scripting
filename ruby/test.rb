print "."
require "markov"

print "."
MarkovChain.database = "mysql://root:@localhost/pigtest"

print "."
mc = MarkovChain.new ARGV.join
print "."

puts

print mc

loop do
  mc.add_word 
  exit unless mc.last_word 
  print " " + mc.last_word
  sleep 0.5
end