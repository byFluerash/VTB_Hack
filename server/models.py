from app import db

class Car(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    markTitle = db.Column(db.NVARCHAR(100), nullable=False)
    country = db.Column(db.NVARCHAR(100), nullable=False)
    price = db.Column(db.Integer, nullable=False)
    modelTitle = db.Column(db.NVARCHAR(100), nullable=False)
    colorsCount = db.Column(db.Integer, nullable=False)
    bodyTitle = db.Column(db.NVARCHAR(100), nullable=False)
    doorsCount = db.Column(db.Integer, nullable=False)
    widgetPhoto = db.Column(db.NVARCHAR(200), nullable=False)
    mainPhoto = db.Column(db.NVARCHAR(200), nullable=False)

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

