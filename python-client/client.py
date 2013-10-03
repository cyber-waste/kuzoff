from httplib2 import Http
from urllib import urlencode

h = httplib2.Http()
host = 'http://localhost/'
db = 'db'

def setDatabaseName(name) :
    global db
    db = name

def setHost(hostName) :
    global host
    host = hostName
    
def parseParameters(data) :
    res = {}
    pairs = data.split(';')
    for pair in pairs :
        param = pair.split('=')
        res[param[0]] = param[1]
    return res

def listTables()  :  
    resp, content = h.request(host + db + "/listtables", "GET")
    print content

def makeTable(data):
    resp, content = h.request(host + db + "/maketable", "POST", data)
    print content
    
def removeTable(name) :
    resp, content = h.request(host + db + "/removetable", "POST", data)
    print content
    
def addRow(data) :
    resp, content = h.request(host + db + "/addrow", "POST", data)
    print content
    
def removeRow(data) :
    resp, content = h.request(host + db + "/removerow", "POST", data)
    print content
    
def dropDatabase() :
    resp, content = h.request(host + db + "/dropdatabase", "POST", data)
    print content
    
def showTable(name) :
    resp, content = h.request(host +db + "/showtable", "GET")
    print content
    
def unionTables(data) :
    resp, content = h.request(host + db + "/uniontables", "POST", data)
    print content
    
def differenceTables(data) :
    resp, content = h.request(host + db + "/differencetables", "POST", data)
    print content
    
def uniqueTable(data) :
    resp, content = h.request(host + db + "/uniquetable", "POST", data)
    print content


