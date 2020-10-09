from flask import Flask

import json
import requests

app = Flask(__name__)

api_key = "a41fe4677241147b63c0917cba17dc20"


@app.route('/marketplace', methods=['GET'])
def get_tasks():

    url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/marketplace'
    headers = {
        'x-ibm-client-id': api_key,
        'accept': "application/json"
    }
    response = requests.get(url, headers=headers)
    jsonresponse = json.loads(response.text)
    return jsonresponse


@app.route("/")
def home():
    return "Hello, Flask!"
