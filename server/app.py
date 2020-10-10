from flask import Flask
from flask import request

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
    'Hyundai Genesis',
    'Jaguar F-PACE',
    'KIA K5',
    'KIA Optima',
    'KIA Sportage',
    'Land Rover RANGE ROVER VELAR',
    'Mazda 3',
    'Mazda 6',
    'Mercedes A',
    'Toyota Camry'
]

cars = []


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

    car = next((x for x in cars if x.markTitle + " " + x.modelTitle == maxProbKey), None)

    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(car)

@app.route("/")
def home():
    return "Hello, Flask!"
