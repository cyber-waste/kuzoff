import httplib2

h = httplib2.Http(".cache")
host = 'http://localhost/'
db = 'db'

def setDatabaseName(name) :
    global db
    db = name

def setHost(hostName) :
    global host
    host = hostName

def listTables()  :  
    resp, content = h.request(host + "listtables", "GET")
    print content

def makeTable(name, scheme):
    pass
    
def removeTable(name) :
    pass
    
def addRow(name, data) :
    pass
    
def removeRow(name, data) :
    pass
    
def dropDatabase() :
    pass
    
def showTable(name) :
    resp, content = h.request(host + "showtable", "GET")
    print content
    
def unionTables(name1, name2) :
    pass
    
def differenceTables(name1, name2) :
    pass
    
def uniqueTable(name) :
    pass


