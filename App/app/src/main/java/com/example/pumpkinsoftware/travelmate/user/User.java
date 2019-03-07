package com.example.pumpkinsoftware.travelmate.user;

import com.example.pumpkinsoftware.travelmate.trip.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {
    private final String uid, name, surname, birthday;
    private String gender, relationship, email, descr, photoProfile, cover;
    private short sumReviews, numReviews;
    private ArrayList<Trip> trips;
    private ArrayList<Trip> favTrips;
    private ArrayList<String> comments;

    public User(String uid, String n, String pp){
        this.uid = uid;
        name=n;
        surname = "";
        birthday = "";
        photoProfile=pp;
    }

    public User(String uid, String name, String surname, String birthday, String gender, String relationship, String email,
                String descr, String photoProfile, String cover, int sumReviews, int numReviews) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday.substring(0, 10); // Last index - 1
        this.gender = gender;
        this.relationship = relationship;
        this.email = email;
        this.descr = descr;
        this.photoProfile = photoProfile;
        this.cover = cover;
        this.sumReviews = (short) sumReviews;
        this.numReviews = (short) numReviews;
        trips = new ArrayList<Trip>();
        favTrips = new ArrayList<Trip>();
        comments = new ArrayList<String>();
    }

    public String getUid() { return uid; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getBirthday() { return birthday; }

    public String getGender() { return gender; }

    public String getRelationship() { return relationship; }

    public String getEmail() { return email; }

    public String getDescr() { return descr; }

    public String getPhotoProfile() { return photoProfile; }

    public String getCover() { return cover; }

    // Sum of all votes received by user
    public short getSumReviews() { return sumReviews; }

    public short getNumReviews() { return numReviews; }

    public ArrayList<Trip> getTrips() { return trips; }

    public ArrayList<Trip> getFavTrips() { return favTrips; }

    public ArrayList<String> getComments() { return comments; }

    public void setRelationship(String r) { relationship = r; }

    public void setEmail(String e) { email = e; }

    public void setDescr(String d) { descr = d; }

    public void setPhotoProfile(String a) { photoProfile = a; }

    public void setCover(String c) { cover = c; }

    public void addReview(short r) { sumReviews += r; numReviews++; }

    public void deleteReview(short r) { sumReviews -= r; numReviews--; }

    public void addTrip(Trip t) { trips.add(t); }

    public void deleteTrip(Trip t) {trips.remove(t);}

    public void addFavTrip(Trip t) { favTrips.add(t); }

    public void deleteFavTrip(Trip t) { favTrips.remove(t); }

    public void addComment(String c) { comments.add(c); }

    public void deleteComment(String c) { comments.remove(c); }

    public short getAge() {
        int age = 0;
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);

            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }

            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;

            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);

            if (month2 > month1) {
                age--;
            }

            else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (short) age ;
    }

    public double getRank() {
        return (sumReviews==0 && numReviews==0)?0:sumReviews / numReviews ;
    }

}
