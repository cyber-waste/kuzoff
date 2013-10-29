import httplib2
import json
import urllib

h = httplib2.Http()
host = 'http://localhost:8080/kuzoff-ws/api/'
db = 'db'

def setDatabaseName(name) :
    global db
    db = name
    resp, content = h.request(host + 'database/' + name, "POST", parseParameters(''))
    print resp
    print content

def setHost(hostName) :
    global host
    host = hostName
    
def parseParameters(data) :
    res = {}
    if data == '' :
        return json.dumps(res)
    pairs = data.split(';')
    for pair in pairs :
        param = pair.split('=')
        res[param[0]] = param[1]
    return json.dumps(res)

def listTables()  :  
    resp, content = h.request(host + "table", "GET")
    print resp
    print content
    
def makeTable(name,data):
    resp, content = h.request(host + "table/" + name + '?' + urllib.urlencode({"columnTypes" : data}), "POST", '')
    print resp
    print content
    
def removeTable(name) :
    resp, content = h.request(host + "table/" + name , "DELETE", '')
    print resp
    print content
    
def addRow(name,data) :
    resp, content = h.request(host  + "table/" + name + '/data' + '?' +  urllib.urlencode({"columnData" : data}) , "POST", '')
    print resp
    print content
    
# not working
def removeRow(name,data) :
    resp, content = h.request(host  + "table/" + name + '/data' + '?' +  urllib.urlencode({"columnData" : {'1':'3'}}), "DELETE", '')
    print resp
    print content
    
def dropDatabase() :
    resp, content = h.request(host +  "/database", "DELETE", '')
    print resp
    print content
    
def showTable(name) :
    resp, content = h.request(host + "table/" + name + '/data', "GET")
    print resp
    print content
    
def unionTables(name1,name2) :
    resp, content = h.request(host + "table/" + name1 + '/union/' + name2, "GET")
    print resp
    print content
    
def differenceTables(name1,name2) :
    resp, content = h.request(host + "table/" + name1 + '/difference/' + name2, "GET")
    print resp
    print content
    
def uniqueTable(name) :
    resp, content = h.request(host + "table/" + name1 + '/unique', "GET")
    print resp
    print content


