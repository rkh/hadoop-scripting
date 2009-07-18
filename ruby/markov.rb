require "sequel"

class MarkovChain
  
  class Phrase
    
    attr_accessor :map, :table
    
    def initialize(table, first, second, third)
      @table, @first, @second, @third = table, first, second, third
      @map = []
      request.each do |e|
        add_entry e
      end
    end
    
    def get_word
      map[rand(map.size)]
    end
    
    private
    
    def filter?(word)
      word !~ /^\w+.$/ 
    end
    
    def add_entry(entry)
      entry[:count].times { map << entry[:w4] } unless filter? entry[:w4]
    end
    
    def request
      query = table.select :w4, :count
      query.where! :w1 => @first if @first
      query.where! :w2 => @second if @second
      query.where! :w3 => @third
    end
    
    def to_s
      "#{@first} #{@second}, #{@third}"
    end

    def inspect
      "#<#{self.class.name}: #{to_s.inspect}>"
    end
    
  end
  
  def self.database=(db)
    const_set "DB", Sequel.connect(db)
  end
  
  def initialize(start = "", table = :splitsuc)
    @table = DB[table]
    @stack = start.split " "
  end
  
  def fill(size)
    add_word until @stack.size >= size
    self
  end
  
  def add_word
    @stack << current_phrase.get_word
    self
  end
  
  def last_word
    @stack.last
  end
  
  def current_phrase
    return phrase(*@stack) unless @stack.size > 2
    phrase(*@stack.slice(-2..-1))
  end
  
  def phrase(*words)
    @known_phrases ||= {}
    words.unshift nil until words.size > 2
    @known_phrases[words] ||= Phrase.new(@table, *words)
  end
  
  def to_s
    @stack.join " "
  end
  
  def inspect
    "#<#{self.class.name}: #{to_s.inspect}>"
  end
  
end