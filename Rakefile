task :default => :pdf

desc "Generate PDF from LaTeX"
task :pdf do
  chdir("paper") do
    %w(pdflatex bibtex pdflatex pdflatex).each do |cmd|
      sh "#{cmd} paper"
    end
    sh "open paper.pdf || exit 0"
  end
end