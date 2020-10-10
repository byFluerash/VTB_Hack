from flask import Flask
from flask import request
from flask import jsonify, make_response

import jsonpickle

import json
import requests

app = Flask(__name__)

api_key = "a41fe4677241147b63c0917cba17dc20"
probabilities_keys = [
    'BMW 3',
    'BMW 5',
    'Cadillac ESCALADE',
    'Chevrolet Tahoe',
    'Jaguar F-PACE',
    'KIA K5',
    'KIA Optima',
    'KIA Sportage',
    'Land Rover RANGE ROVER VELAR',
    'Mazda 6',
]



class Car:

    def __init__(self, markTitle, country, price, modelTitle, colorsCount, bodyTitle, doorsCount, widgetPhoto, mainPhoto):
        self.markTitle = markTitle
        self.country = country
        self.price = price
        self.modelTitle = modelTitle
        self.colorsCount = colorsCount
        self.bodyTitle = bodyTitle
        self.doorsCount = doorsCount
        self.widgetPhoto = widgetPhoto
        self.mainPhoto = mainPhoto

class Credit:

    def __init__(self, car, requested_amount, state):
        self.car = car
        self.requested_amount = requested_amount
        self.state = state

cars = []
credits = [ Credit("LADA Granta", 450000, "Отказано")
]

@app.route('/marketplace', methods=['GET'])
def marketplace():
    if (len(cars) == 0):
        url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/marketplace'
        headers = {
            'x-ibm-client-id': api_key,
            'accept': "application/json"
        }
        response = requests.get(url, headers=headers)
        jsonresponse = json.loads(response.text)

        for mark in jsonresponse["list"]:
            _markTitle = mark["title"]
            _country = mark["country"]["title"]
            for model in mark["models"]:
                _price = model["minPrice"]
                _modelTitle = model["title"]
                _colorsCount = model["colorsCount"]
                for body in model["bodies"]:
                    _bodyAlias = body["alias"]
                    _bodyType = body["type"]
                    _bodyTitle = body["title"]
                    _doorsCount = body["doors"]
                    try:
                        _widgetPhoto = model["renderPhotos"]["render_widget_right"][_bodyAlias]["path"]
                        _mainPhoto = model["renderPhotos"]["main"][_bodyAlias]["path"]
                    except:
                        try:
                            _widgetPhoto = model["renderPhotos"]["render_widget_right"]["hatchback"]["path"]
                            _mainPhoto = model["renderPhotos"]["main"]["hatchback"]["path"]
                        except:
                            try:
                                _widgetPhoto = model["renderPhotos"]["render_widget_right"]["wagon"]["path"]
                                _mainPhoto = model["renderPhotos"]["main"]["wagon"]["path"]
                            except:
                                try:
                                    _widgetPhoto = model["renderPhotos"]["render_widget_right"]["crossover"]["path"]
                                    _mainPhoto = model["renderPhotos"]["main"]["crossover"]["path"]
                                except:
                                    try:
                                        _widgetPhoto = model["renderPhotos"]["render_widget_right"]["hatchback5d"]["path"]
                                        _mainPhoto = model["renderPhotos"]["main"]["hatchback5d"]["path"]
                                    except:
                                        _widgetPhoto = ""
                                        _mainPhoto = ""
                    car = Car(_markTitle, _country, _price, _modelTitle, _colorsCount,
                            _bodyTitle, _doorsCount, _widgetPhoto, _mainPhoto)
                    cars.append(car)

    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(cars)

@app.route('/car-recognize', methods=['POST'])
def carRecognize():
    body = request.get_json()
    url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/car-recognize'
    headers = {
        'x-ibm-client-id': api_key,
        'content-type': "application/json",
        'accept': "application/json"
    }
    response = requests.post(url, headers=headers, json=body)
    jsonresponse = json.loads(response.text)

    probabilities = jsonresponse["probabilities"]

    maxProb = -1.0
    maxProbKey = ""

    for key in probabilities_keys:
        prob = probabilities[key]
        if prob > maxProb:
            maxProb = prob
            maxProbKey = key

    if maxProbKey=="BMW 3":
        maxProbKey="BMW 3 серии"

    if maxProbKey=="BMW 5":
        maxProbKey="BMW 5 серия"

    car = next((x for x in cars if x.markTitle + " " + x.modelTitle == maxProbKey), None)

    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(car)

@app.route('/calculate', methods=['POST'])
def calculate():
    body = request.get_json()
    payload = {
        'clientTypes': ["ac43d7e4-cd8c-4f6f-b18a-5ccbc1356f75"],
        'cost': body["cost"],
        'initialFee': body["initialFee"],
        'kaskoValue':  body["kaskoValue"],
        'term': body["term"],
        'language': "ru-RU",
        'residualPayment': 51.39552832,
        'settingsName': "Haval",
        'specialConditions': [
            "57ba0183-5988-4137-86a6-3d30a4ed8dc9",
            "b907b476-5a26-4b25-b9c0-8091e9d5c65f",
            "cbfc4ef3-af70-4182-8cf6-e73f361d1e68"
            ],
      }

    url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/calculate'
    headers = {
        'x-ibm-client-id': api_key,
        'content-type': "application/json",
        'accept': "application/json"
    }
    response = requests.post(url, headers=headers, json=payload)
    jsonresponse = json.loads(response.text)

    response = {
        'loanAmount': jsonresponse["result"]["loanAmount"],
        'monthPayment': jsonresponse["result"]["payment"],
        'contractRate': jsonresponse["result"]["contractRate"]
    }

    return response

@app.route('/carloan', methods=['POST'])
def carloan():
    body = request.get_json()
    payload = {
        "comment": "Комментарий",
        "customer_party": {
            "email": body["email"],
            "income_amount": body["initialFee"],
            "person": {
                "birth_date_time": body["birthday"],
                "birth_place": "г. Cамара",
                "family_name": body["lastName"],
                "first_name": body["firstName"],
                "gender": "male",
                "middle_name": "",
                "nationality_country_code": "RU"
                },
            "phone": body["phone"],
            },
            "datetime": "2020-10-10T08:15:47Z",
            "interest_rate": body["contractRate"],
            "requested_amount": body["loanAmount"],
            "requested_term": body["term"] * 12,
            "trade_mark": body["mark"],
            "vehicle_cost": body["carPrice"]
    }

    url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/carloan'
    headers = {
        'x-ibm-client-id': api_key,
        'content-type': "application/json",
        'accept': "application/json"
    }
    response = requests.post(url, headers=headers, json=payload)
    jsonresponse = json.loads(response.text)

    response = {
        'application_status': jsonresponse["application"]["decision_report"]["application_status"],
        'monthly_payment': jsonresponse["application"]["decision_report"]["monthly_payment"]
    }

    return response

@app.route('/credits', methods=['POST'])
def addCredit():
    body = request.get_json()
    credit = Credit(body["car"], body["loanAmount"], body["state"])
    credits.append(credit)
    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(credit)

@app.route('/credits', methods=['GET'])
def getCredits():
    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(credits)

@app.route("/")
def home():
    return "Hello, Flask!"
