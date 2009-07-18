DB = Sequel.connect "mysql://root:@localhost/pigtest"

base_query = DB[:splitsuc]