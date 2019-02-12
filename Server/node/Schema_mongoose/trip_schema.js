var mongoose = require('mongoose');

var TripSchema = new mongoose.Schema({
        name: String,
        description: String,
        departure: String,
        destination: String,
        image: String,
        budget: Number,
        startDate: Date,
        endDate: Date,
        pets: Boolean,
        maxPartecipant: Number,
		partecipant: [{userId:String}]
	}

);

var Trip = mongoose.model("trip", TripSchema);
module.exports = Trip;