task :default => :pdf

desc "Generate PDF from LaTeX"
task :pdf do
  chdir("paper") do
    %w(pdflatex bibtex pdflatex pdflatex).each do |cmd|
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

file "_r.dat" => [proc { |n| n.sub(/_r\.dat$/, ".dat") }] do |t|
  File.open(t.source) do |src|
    File.open(t.name, "w") do |target|
      src.each_line do |line|
        next if line =~ /^\s*$|^#/
        input = line.split(/\s+/)
        size = input.shift
        input.map! { |h,m,s| h*60*60 + m*60 + s }
        
      end
    end
  end
end 

desc "Spell check paper"
task :spellcheck do
  Dir.glob("paper/*.tex") { |f| sh "aspell -c #{f}" }
end