require 'active_support'
require 'duration'

s=""

File.open(ARGV[0]) do |f| 
  f.lines.to_a[1..-1].each do |l|
    high = 0
    low = 1.0/0
    avg = (l.split[1..-1].inject(0) do |sum,e|
       sp = e.split(":").map { |i| i.to_i }
       t = sp[0]*3600 + sp[1]*60 + sp[2]
       high = t if t > high
       low = t if t < low
       sum = t + sum
    end) / 4
    keys = []
    [avg, high, low].each do |item|
      keys << Duration.new(item).format("%H:%M:%S")
    end
    s += "#{l.split[0]}\t"+keys.join("\t")+"\n"
  end
end

puts s
