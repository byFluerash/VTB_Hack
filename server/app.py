from flask import Flask
import http.client

app = Flask(__name__)

api_key = "a41fe4677241147b63c0917cba17dc20"


@app.route('/marketplace', methods=['GET'])
def get_tasks():
    conn = http.client.HTTPSConnection("gw.hackathon.vtb.ru")
    headers = {
        'x-ibm-client-id': api_key,
        'accept': "application/json"
    }
    conn.request("GET", "/vtb/hackathon/marketplace", headers=headers)
    res = conn.getresponse()
    data = res.read()
    return data.decode("utf-8")


@app.route("/")
def home():
    return "Hello, Flask!"
