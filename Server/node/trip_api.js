var express = require('express');
var mongoose = require('mongoose');
var TripSchema = require('./Schema_mongoose/trip_schema');
var tripExample = require('./Schema_mongoose/trip_example.json'); //Trip Example
//var database = require('./database');
var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


//Faccio collegare mongoose al database
mongoose.connect(url);

//Creo il router
var router = express.Router(); 


/******************************************
			  Inizio API
******************************************/

/*****************************************/
//Api per testare il funzionamento di trip_api.js

router.get('/', function (req, res) {
    res.send("Mi trovo in trip_api.js");
});


/*****************************************/
//Api per inserire un nuovo viaggio


router.post('/newTrip', function(req,res){

	var clientInput = req.body;

	var toInsert = new TripSchema({
		name : clientInput.name.toLowerCase(),
        description: clientInput.description.toLowerCase(),
        departure: clientInput.departure.toLowerCase(),
        destination: clientInput.destination.toLowerCase(),
        budget: clientInput.budget,
        startDate: clientInput.startDate,
        endDate: clientInput.endDate,
        pets: clientInput.pets,
        maxPartecipant: clientInput.maxPartecipant,
		partecipant: []
	});

	toInsert.save(function (err) {
        if (err){ 
            console.log(err);
        	res.send(JSON.stringify({ status: "error", message: "Error to add "+toInsert.name+" on database." }));
        	console.log(JSON.stringify({ status: "error", message: "Error to add "+toInsert.name+" on database." }));
        }
        else {
        	res.send(JSON.stringify({ status: "success", message: "Trip " + toInsert.name + " created!" }));
        	console.log(JSON.stringify({ status: "success", message: "Trip " + toInsert.name + " created!" }));
        }
    });

});

/*****************************************/
//Api per ottenere tutti i viaggi

router.get('/allTrips', function(req, res){
	TripSchema.find({}, function(err, trips){
		if(err){
			console.log(err);
			res.send(JSON.stringify({ status: "error", message: "Error to send all trips." }));
		}else{
			res.send(trips);
			console.log('retrieved list of trips', trips.length);
		}
	});
});

/*****************************************/
//Api per ottenere i viaggi con filtri

// example use /getTrips?destination=rome&departure=milan&minBudget=430&maxBudget=730&startDate=12/31/2018&endDate=01/02/2019&maxPartecipant=12
// &pets = true

router.get('/getTripsWithFilter', function(req, res){
	var query = {};
	var departure = req.query.departure;
	var destination = req.query.destination;
	var pets = req.query.pets;
	var minBudget = 0;
	var maxBudget = 100000;
	var minDate = new Date("1/1/1970");
	var maxDate = new Date("1/1/4000");
	var maxPartecipant = 1000000

	if(departure != undefined)
		query.departure = departure.toLowerCase();
	if(destination != undefined)
		query.destination = destination.toLowerCase();
	if(pets != undefined)
		query.pets = pets;
	if(req.query.minBudget != undefined)
		minBudget = req.query.minBudget;
	if(req.query.maxBudget != undefined)
		maxBudget = req.query.maxBudget;
	if(req.query.minDate != undefined)
		minDate = new Date(req.query.minDate);
	if(req.query.maxDate != undefined)
		maxDate = new Date(req.query.maxDate);
	if(req.query.maxPartecipant != undefined)
		maxPartecipant = req.query.maxPartecipant;


	TripSchema.find(query).where('budget').gte(minBudget).lte(maxBudget).where('startDate').gte(minDate).where('endDate').lte(maxDate).where('maxPartecipant').lte(maxPartecipant).exec( function(err, trips){
		if(err){
			res.send(JSON.stringify({ status: "error", message: "Error parameters type." }));
			console.log(err);
		}else{
			res.send(trips);
			console.log('retrieved list of trips', trips.length);
		}
	});
});

/******************************************/
//Api per aggiornare un viaggio

router.post('/updateTrip', function(req, res){

	var JsonObject = req.body;

	TripSchema.findById(JsonObject._id).exec(function(err, trip){
		
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error with ObjectId" }));
			console.log(err);
		}

		trip.set(JsonObject);
		
		trip.save(function(err, updatetrip){
			if (err){
				res.send(JSON.stringify({ status: "error", message: "Error on update your trip" }));
				console.log(err);
			} 
			res.send(updatetrip);
		});
	});
});

/******************************************/
//Api per ottenere un viaggio da Id
//?id=

router.get('/getTripById', function(req, res){

	var id = req.query.id;

	TripSchema.findById(id).exec(function(err, trip){
		
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error get with ObjectId" }));
			console.log(err);
		}
		else{
			res.send(trip);
			console.log(trip);
		}

	});
});

/******************************************/
//Api per inserire 15.000 viaggi esempio

router.get('/loadExample', function(req, res){
	var laptopData = tripExample;
	for(var laptopItem in laptopData){
    new TripSchema(laptopData[laptopItem])
      .save()
      .catch((err)=>{
      	res.send("Error");
        console.log(err.message);
      });
	}
	res.send("Ok");
});



module.exports = router;
