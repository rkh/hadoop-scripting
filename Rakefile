task :default => [:plot, :pdf]

desc "Generate PDF from LaTeX"
task :pdf do
  chdir("paper") do
    %w(latex bibtex latex latex dvipdf).each do |cmd|
      sh "#{cmd} paper"
    end
    sh "open paper.pdf || exit 0"
    sh <<-EOS
      (scp paper.pdf nada1.de:public_html/hadoop-scripting.pdf &&
      (echo 'tell application "Adium" to send the active chat message "http://nada1.de/~konstantin/hadoop-scripting.pdf"' |
      osascript)) || exit 0
    EOS
  end
end

namespace "plot" do
  
  LANG = {:wordcount => [:java, :jaql, :pig], :markov => [:java, :pig]}
  
  def raw(name)
    @raw ||= {}
    @raw[name] ||= File.open("benchmarks/benchmark_#{name}.txt") do |f|
      {}.tap do |results|
        size = nil
        f.each_line do |line|
          next if line =~ /^\s*$|^#/ 
          new_size, run, *rest = line.split /\s+/
          size = new_size.to_i unless new_size.empty?
          LANG[name].each_with_index do |l,i|
            results[l] ||= {}
            results[l][size] ||= []
            results[l][size][run.to_i-1] = in_seconds rest[i]
          end 
        end
      end
    end
  end
  
  def in_seconds(value)
    value.split(":").inject(0) { |s,i| s*60+i.to_i }
  end
  
  def time(seconds)
    [seconds/3600, seconds%3600/60, seconds%60].map { |v| "#{0 if v < 10}#{v}" }.join ":"
  end
  
  def avg(ary)
    ary.inject(0) { |s,i| s+i } / ary.size
  end
  
  def bench(name, lang)
    raw(name)[lang].inject("") do |out, (size, values)|
      out << size.to_s << "\t" << time(avg(values)) << "\t" << 
        values.minmax.reverse.map! { |v| time(v) }.join("\t") << "\n"
    end
  end
  
  def plot(name)
    LANG[name].each { |l| File.open("benchmarks/#{l}_#{name}.dat", "w") { |f| f << bench(name, l) } } 
    chdir("benchmarks") { sh "gnuplot #{name}.p" }
    #sh "open benchmarks/#{name}.eps || exit 0"
  end
  
  task "wordcount" do
    plot :wordcount
  end
  
  task "markov" do
    plot :markov
  end
  
end

task :plot => %w[plot:wordcount plot:markov]

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

desc "Generate zip file"
task :zip do
  sh "git clean -fdx && git clean -fdX && zip hadoop-scripting.zip $(find .)"
end