from flask import Flask

import jsonpickle

import json
import requests

app = Flask(__name__)

api_key = "a41fe4677241147b63c0917cba17dc20"

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
def get_tasks():

    url = 'https://gw.hackathon.vtb.ru/vtb/hackathon/marketplace'
    headers = {
        'x-ibm-client-id': api_key,
        'accept': "application/json"
    }
    response = requests.get(url, headers=headers)
    jsonresponse = json.loads(response.text)

    cars = []

    for mark in jsonresponse["list"]:
        _markTitle = mark["title"]
        _country = mark["country"]["title"]
        for model in mark["models"]:
            _price = model["minprice"]
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
                car = Car(_markTitle, _country, _price, _modelTitle, _colorsCount, _bodyTitle, _doorsCount, _widgetPhoto, _mainPhoto)
                cars.append(car)

    jsonpickle.set_preferred_backend('json')
    jsonpickle.set_encoder_options('json', ensure_ascii=False)
    return jsonpickle.encode(cars)


@app.route("/")
def home():
    return "Hello, Flask!"
