task :default => :pdf

desc "Generate PDF from LaTeX"
task :pdf do
  chdir("paper") do
    %w(pdflatex bibtex pdflatex pdflatex).each do |cmd|
      sh "#{cmd} paper"
    end
    sh "open paper.pdf || exit 0"
    sh "scp paper.pdf nada1.de:public_html/hadoop-scripting.pdf || exit 0"
  end
end 

desc "Spell check paper"
task :spellcheck do
  Dir.glob("paper/*.tex") { |f| sh "aspell -c #{f}" }
end