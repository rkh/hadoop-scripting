task :default => :pdf

desc "Generate PDF from LaTeX"
task :pdf do
  chdir("paper") do
    %w(pdflatex bibtex pdflatex).each do |cmd|
      sh "#{cmd} paper || exit 0"
    end
    sh "pdflatex paper"
    sh "open paper.pdf || exit 0"
    sh <<-EOS
      (scp paper.pdf nada1.de:public_html/hadoop-scripting.pdf &&
      (echo 'tell application "Adium" to send the active chat message "http://nada1.de/~konstantin/hadoop-scripting.pdf"' |
      osascript)) || exit 0
    EOS
  end
end

namespace "plot" do
  
  def wordcount
    @wordcount ||= File.open("benchmarks/benchmark_wordcount.txt") do |f|
      {:java => [], :jaql => [], :pig => []}.tap do |results|
        f.each_line do |line|
          next if line =~ /^\s*$|^#/
        end
      end
    end
  end
  
  task "wordcount" do
    
  end
  
  task "markov" do
  end
  
end

file "benchmarks/benchmark.png" => %w[benchmarks/java_r.dat benchmarks/jaql_r.dat benchmarks/pig_r.dat] do
  chdir("benchmarks") { sh "gnuplot benchmark.p" }
end

rule "_r.dat" => [proc { |n| n.sub(/_r\.dat$/, ".dat") }] do |t|
  File.open(t.source) do |src|
    File.open(t.name, "w") do |target|
      src.each_line do |line|
        next if line =~ /^\s*$|^#/
        output = []
        input = line.split(/\s+/)
        size = input.shift
        input.map! { |e| e.split(":") }
        input.map! { |h,m,s| h.to_i*3600 + m.to_i*60 + s.to_i }
        output << input.inject(0) { |s,i| s + i} / input.size
        output += input.minmax.reverse
        output.map! { |v| "#{v/3600}:#{v%3600/60}:#{v%60}" }
        output.unshift size
        target.puts output.join "\t"
      end
    end
  end
end 

desc "Spell check paper"
task :spellcheck do
  Dir.glob("paper/*.tex") { |f| sh "aspell -c #{f}" }
end