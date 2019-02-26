var express = require('express');
var mongoose = require('mongoose');
var TripSchema = require('./Schema_mongoose/trip_schema');
var tripExample = require('./Schema_mongoose/trip_example.json'); //Trip Example
//var database = require('./database');
var url = process.env.DATA || "mongodb://127.0.0.1:27017/TravelMate";


//Faccio collegare mongoose al database
mongoose.connect(url,{ useNewUrlParser: true });

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
        image: clientInput.image,
        owner: clientInput.owner,
        startDate: clientInput.startDate,
        endDate: clientInput.endDate,
        createDate: new Date(),
        vehicle: clientInput.vehicle,
        tag: clientInput.tag,
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
//Api per ottenere gli ultimi 200 viaggi inseriti

//example use /lastTripsCreated?limit=200

router.get('/lastTripsCreated', function(req, res){

	var limit = 200;

	if(req.query.limit != undefined) 
		limit = parseInt(req.query.limit);

	TripSchema.find( {tag : {$exists:true}, $where:'this.partecipant.length<this.maxPartecipant'}).sort({"createDate": 'desc'}).limit(limit).exec(function(err, trips){
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
// &pets=true&minPartecipant=1

router.get('/getTripsWithFilter', function(req, res){
	var query = {};
	var name = req.query.name;
	var departure = req.query.departure;
	var destination = req.query.destination;
	var tag = req.query.tag;
	var vehicle = req.query.vehicle;
	var minBudget = 0;
	var maxBudget = 100000;
	var minDate = new Date("1/1/1970");
	var maxDate = new Date("1/1/4000");
	var maxPartecipant = 1000000;
	var minPartecipant = 1;

	if(name != undefined)
		query.name = name.toLowerCase();
	if(departure != undefined)
		query.departure = departure.toLowerCase();
	if(destination != undefined)
		query.destination = destination.toLowerCase();
	if(tag != undefined)
		query.tag = tag;
	if(vehicle != undefined)
		query.vehicle = vehicle;
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
	if(req.query.minPartecipant != undefined)
		minPartecipant = req.query.minPartecipant;

	var condition={tag : {$exists:true}, $where:'this.partecipant.length<this.maxPartecipant'};


	TripSchema.find(condition).find(query).where('budget').gte(minBudget).lte(maxBudget).where('startDate').gte(minDate).where('endDate').lte(maxDate).where('maxPartecipant').lte(maxPartecipant).gte(minPartecipant).exec( function(err, trips){
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

	var query = {};
	
	if (JsonObject.name != undefined)
		query.name = JsonObject.name.toLowerCase();
	if (JsonObject.description != undefined)
		query.description = JsonObject.description.toLowerCase();
	if(JsonObject.departure != undefined)
		query.departure = JsonObject.departure.toLowerCase();
	if(JsonObject.destination != undefined)
		query.destination = JsonObject.destination.toLowerCase();
	if(JsonObject.pets != undefined)
		query.pets = JsonObject.pets;
	if(JsonObject.minBudget != undefined)
		minBudget = JsonObject.minBudget;
	if(JsonObject.maxBudget != undefined)
		maxBudget = JsonObject.maxBudget;
	if(JsonObject.minDate != undefined)
		minDate = new Date(JsonObject.minDate);
	if(JsonObject.maxDate != undefined)
		maxDate = new Date(JsonObject.maxDate);
	if(JsonObject.maxPartecipant != undefined)
		maxPartecipant = JsonObject.maxPartecipant;
	if(JsonObject.minPartecipant != undefined)
		minPartecipant = JsonObject.minPartecipant;
	if (JsonObject.image != undefined)
		query.image = JsonObject.image;
	
	TripSchema.findById(JsonObject.tripId).exec(function(err, trip){
		
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Cannot find trip by tripId" }));
			console.log(err);
		}

		trip.set(query);
		
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
//Api per cancellare un viaggio
//example use: /deleteTrip?tripId=5c537f4bbd73113cd71d1384

router.get('/deleteTrip', function(req, res){

	var id = req.query.tripId;    

	TripSchema.remove({_id : id }, function(err){
		if(err){
			res.send(JSON.stringify({ status: "error", message: "Error on delete your trip" }));
			console.log(err);
		}
		else{
			res.send(JSON.stringify({ status: "ok", message: "Your trip is deleted" }));
		}
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
	for(var i=0; i<laptopData.length;i++){

		var toInsert = new TripSchema({
		name : laptopData[i].name.toLowerCase(),
        description: laptopData[i].description.toLowerCase(),
        departure: laptopData[i].departure.toLowerCase(),
        destination: laptopData[i].destination.toLowerCase(),
        budget: laptopData[i].budget,
        startDate: laptopData[i].startDate,
        endDate: laptopData[i].endDate,
        vehicle: laptopData[i].vehicle,
        image: laptopData[i].image,
        tag: laptopData[i].tag,
        owner: laptopData[i].owner,
        maxPartecipant: laptopData[i].maxPartecipant,
		partecipant: []
	});

      toInsert.save()
      .catch((err)=>{
      	res.send("Error");
        console.log(err.message);
      });
	}
	res.send("Ok");
});

/****************************************/
//Api per inserire un nuovo partecipante in un viaggio

router.post('/addParticipant', function(req,res){
	
	var JsonObject = req.body;

	var buddy = {
		"userId": JsonObject.userId
	};	
	var conditions = {							
		_id: JsonObject.tripId,
		'partecipant.userId': { $ne: JsonObject.userId }		
	};
	var update = {
		$addToSet: {partecipant: buddy}
	};
	
	TripSchema.findOne(conditions, function (err, trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error with ObjectId" }));
			console.log(err);
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", message: "User is already in this trip" }));
			console.log(JSON.stringify({ status: "error", message: "User is already in this trip" }));
		}			
		else{
			trip.updateOne(update, function(err, tripupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", message: "Error on adding user" }));
					console.log(err);
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " added to trip: " + trip._id }));
					console.log(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " added to trip: " + trip._id }));
				};
			});
		};
	});
});

/****************************************/
//Api per rimuovere un utente da un viaggio

router.post('/removeParticipant', function(req,res){
	
	var JsonObject = req.body;

	var buddy = {
		"userId": JsonObject.userId
	};	
	var conditions = {							
		_id: JsonObject.tripId,
		partecipant: {$elemMatch: {userId: JsonObject.userId} } 	
	};
	var update = {
		$pull: {partecipant: buddy}
	};
	
	TripSchema.findOne(conditions, function (err, trip) {
		if (err){
			res.send(JSON.stringify({ status: "error", message: "Error with ObjectId" }));
			console.log(err);
		}
		else if (trip == null){
			res.send(JSON.stringify({ status: "error", message: "User is not in this trip" }));
			console.log(JSON.stringify({ status: "error", message: "User is not in this trip" }));
		}			
		else{
			trip.updateOne(update, function(err, tripupdate){
				if (err){
					res.send(JSON.stringify({ status: "error", message: "Error on removing user" }));
					console.log(err);
				}
				else{
					res.send(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " removed from trip: " + trip._id }));
					console.log(JSON.stringify({ status: "ok", message: "User: " + JsonObject.userId + " removed from trip: " + trip._id }));
				};
			});
		};
	});
});

module.exports = router;

