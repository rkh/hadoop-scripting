$:.unshift(*Dir["vendor/*/lib"])

# Amount of data in bytes
MAX_SIZE = ENV["BYTES"] ? ENV["BYTES"].to_i : ENV["MB"] ? (ENV["MB"].to_i * 1024 * 1024) : ENV["KB"] ? (ENV["KB"].to_i * 1024) : 1024 * 1024
FILE = ENV["FILE"]

require "mw_api"

def announce(text)
  $stderr << text
end

namespace :get_data do
  
  namespace :wikipedia do
    
    def get_page(name)
      begin
        text = @wiki.parse(:text => "{{:#{name}}}")["parse"]["text"]["*"]
        text.gsub!(/ *(\n|\r)+ */, " ")
        text.gsub!(/<[^>]*>/, "")
        text << "\n"
      rescue Exception
        ""
      end
    end
    
    task :setup do
      @wiki = MediaWiki.wikipedia
      @file = FILE || "data/wikipedia"
      @size = 0
    end
    
    task :download => :setup do
      announce "downloading #{MAX_SIZE} bytes\n"
      File.open(@file, "w") do |f|
        @wiki.allpages(:apfrom => "Shakespear") do |page|
          break if @size >= MAX_SIZE
          announce "reading #{page["title"]}"
          announce " " * (60 - page["title"].length) if page["title"].length < 60
          @size += f.write(get_page(page["title"]))
          announce " #{@size * 100 / MAX_SIZE} % (#{@size} of #{MAX_SIZE} Bytes)\n"
        end
      end
      announce "done\n"
    end
    
  end
  
  task :wikipedia => "wikipedia:download"
  
end