$:.unshift(*Dir["vendor/*/lib"])

# Amount of data in bytes
MAX_SIZE = ENV["BYTES"] ? ENV["BYTES"].to_i : ENV["MB"] ? (ENV["MB"].to_i * 1024 * 1024) : ENV["KB"] ? (ENV["KB"].to_i * 1024) : 1024 * 1024
FILE = ENV["FILE"]

namespace :get_data do
  
  desc "Download some articles from Wikipedia."
  task :wikipedia do
    require "mw_api"
    File.open(FILE || "data/wikipedia", "w") do |f|
      wiki, size = MediaWiki.wikipedia, 0
      $stderr.puts "downloading #{MAX_SIZE} bytes" 
      wiki.allpages(:apfrom => :a) do |page|
        break if size >= MAX_SIZE
        $stderr.print "reading #{page["title"]}"
        begin
          size += f.write wiki.page_content(page["title"])
        rescue Exception # fix me
        end
        $stderr.print " " * (60 - page["title"].length) if page["title"].length < 60
        $stderr.puts " #{size * 100 / MAX_SIZE} % (#{size} of #{MAX_SIZE} Bytes)"
      end
      $stderr.puts "done"
    end
  end
  
end