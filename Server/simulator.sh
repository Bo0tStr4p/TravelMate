#!/bin/bash

#Comandi di default per modificare colore shell
red=`tput setaf 1`
yellow=`tput setaf 3`
green=`tput setaf 2`
reset=`tput sgr0`

#Caricamento degli user
bash populate_database_user.sh

#Caricamento dei trip
bash populate_database_trip.sh


#Inserimento delle recensioni per gli utenti

echo -e "Starting to add reviews to users"

echo -e "${green}"

#User1 Uid: Tm8WrtKtVzco0him7RlnB9jUMmL2
curl -H "Content-Type: application/json" -X POST -d '{"uid":"Tm8WrtKtVzco0him7RlnB9jUMmL2","sumReview":"36.5","numReview":"9"}' http://localhost:8095/user/updateUser
echo -e ""

#User2 Uid: RaHUVBSgljOzWvKNSGLl3QeSThh1
curl -H "Content-Type: application/json" -X POST -d '{"uid":"RaHUVBSgljOzWvKNSGLl3QeSThh1","sumReview":"34","numReview":"10"}' http://localhost:8095/user/updateUser
echo -e ""

#User3 Uid: kbz4sNCZtHMlfw3w3xCW3TTBco53
curl -H "Content-Type: application/json" -X POST -d '{"uid":"kbz4sNCZtHMlfw3w3xCW3TTBco53","sumReview":"52.5","numReview":"13"}' http://localhost:8095/user/updateUser
echo -e ""

#User4 Uid: pIfUG1Qpmwasqrdu77d9nake9HM2
curl -H "Content-Type: application/json" -X POST -d '{"uid":"pIfUG1Qpmwasqrdu77d9nake9HM2","sumReview":"4","numReview":"1"}' http://localhost:8095/user/updateUser
echo -e ""

#User5 Uid: 7hjchdyNdrO2itvdMRRj3b85wff2
curl -H "Content-Type: application/json" -X POST -d '{"uid":"7hjchdyNdrO2itvdMRRj3b85wff2","sumReview":"4.5","numReview":"2"}' http://localhost:8095/user/updateUser
echo -e ""

#User6 Uid: Ip5ooeVpugPGm6I0SY5Y2TUNIki2
curl -H "Content-Type: application/json" -X POST -d '{"uid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2","sumReview":"55","numReview":"12"}' http://localhost:8095/user/updateUser
echo -e ""

#User7 Uid: GutGWyaTg9OdhjecP1wzd35e0l83
curl -H "Content-Type: application/json" -X POST -d '{"uid":"GutGWyaTg9OdhjecP1wzd35e0l83","sumReview":"33.5","numReview":"7"}' http://localhost:8095/user/updateUser
echo -e ""

#User8 Uid: 3ENghkkewyPdttrz00ImV0F0QpH2
curl -H "Content-Type: application/json" -X POST -d '{"uid":"3ENghkkewyPdttrz00ImV0F0QpH2","sumReview":"21","numReview":"8"}' http://localhost:8095/user/updateUser
echo -e ""

#User9 Uid: sSiOgobodkOlLkyzbIqnqxLQQq93
curl -H "Content-Type: application/json" -X POST -d '{"uid":"sSiOgobodkOlLkyzbIqnqxLQQq93","sumReview":"34.5","numReview":"8"}' http://localhost:8095/user/updateUser
echo -e ""

#User10 Uid: pfcytk2YLdb7qQ2a224J420vZB03
curl -H "Content-Type: application/json" -X POST -d '{"uid":"pfcytk2YLdb7qQ2a224J420vZB03","sumReview":"32","numReview":"8"}' http://localhost:8095/user/updateUser
echo -e ""

echo -e "${green}"

echo -e " ${reset}"

echo -e "Script end"

#Caricamento dei partecipanti

echo -e "Starting to populate trips with partecipants"

echo -e "${green}"

#Viaggio 1 IdViaggio:5c79b80f4a8b77336e1a332a IdCreatore:Tm8WrtKtVzco0him7RlnB9jUMmL2 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"pIfUG1Qpmwasqrdu77d9nake9HM2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80f4a8b77336e1a332a","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 2 IdViaggio:5c79b80e4a8b77336e1a3329 IdCreatore:RaHUVBSgljOzWvKNSGLl3QeSThh1 maxPartecipant:10
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b80e4a8b77336e1a3329","userUid":"pIfUG1Qpmwasqrdu77d9nake9HM2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 3 IdViaggio:5c79b7e04a8b77336e1a3328 IdCreatore:kbz4sNCZtHMlfw3w3xCW3TTBco53 maxPartecipant:15
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userUid":"Tm8WrtKtVzco0him7RlnB9jUMmL2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3328","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 4 IdViaggio:5c79b7e04a8b77336e1a3327 IdCreatore:pIfUG1Qpmwasqrdu77d9nake9HM2 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3327","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 5 IdViaggio:5c79b7e04a8b77336e1a3326 IdCreatore:7hjchdyNdrO2itvdMRRj3b85wff2 maxPartecipant:16
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3326","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3326","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 6 IdViaggio:5c79b7e04a8b77336e1a3325 IdCreatore:Ip5ooeVpugPGm6I0SY5Y2TUNIki2 maxPartecipant:4
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3325","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3325","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 7 IdViaggio:5c79b7e04a8b77336e1a3324 IdCreatore:GutGWyaTg9OdhjecP1wzd35e0l83 maxPartecipant:15
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"Tm8WrtKtVzco0him7RlnB9jUMmL2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3324","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 8 IdViaggio:5c79b7e04a8b77336e1a3323 IdCreatore:3ENghkkewyPdttrz00ImV0F0QpH2 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3323","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 9 IdViaggio:5c79b7e04a8b77336e1a3322 IdCreatore:sSiOgobodkOlLkyzbIqnqxLQQq93 maxPartecipant:17
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3322","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 10 IdViaggio:5c79b7e04a8b77336e1a3321 IdCreatore:pfcytk2YLdb7qQ2a224J420vZB03 maxPartecipant:4
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3321","userUid":"pIfUG1Qpmwasqrdu77d9nake9HM2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 11 IdViaggio:5c79b7e04a8b77336e1a3320 IdCreatore:Tm8WrtKtVzco0him7RlnB9jUMmL2 maxPartecipant:7
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userUid":"RaHUVBSgljOzWvKNSGLl3QeSThh1"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a3320","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 12 IdViaggio:5c79b7e04a8b77336e1a331f IdCreatore:RaHUVBSgljOzWvKNSGLl3QeSThh1 maxPartecipant:25
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"pIfUG1Qpmwasqrdu77d9nake9HM2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c79b7e04a8b77336e1a331f","userUid":"Tm8WrtKtVzco0him7RlnB9jUMmL2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 13 IdViaggio:5c8a254ac537bc12849274e1 IdCreatore:Tm8WrtKtVzco0him7RlnB9jUMmL2 maxPartecipant:5
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a254ac537bc12849274e1","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a254ac537bc12849274e1","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a254ac537bc12849274e1","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a254ac537bc12849274e1","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""
 
#Viaggio 14 IdViaggio:5c8a2726c537bc12849274e3 IdCreatore:RaHUVBSgljOzWvKNSGLl3QeSThh1 maxPartecipant:7
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2726c537bc12849274e3","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2726c537bc12849274e3","userUid":"pIfUG1Qpmwasqrdu77d9nake9HM2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2726c537bc12849274e3","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2726c537bc12849274e3","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 15 IdViaggio:5c8a2959c537bc12849274e5 IdCreatore:7hjchdyNdrO2itvdMRRj3b85wff2 maxPartecipant:4
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2959c537bc12849274e5","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2959c537bc12849274e5","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a2959c537bc12849274e5","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 16 IdViaggio:5c8a29c4c537bc12849274e7 IdCreatore: maxPartecipant: 10
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29c4c537bc12849274e7","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29c4c537bc12849274e7","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29c4c537bc12849274e7","userUid":"pfcytk2YLdb7qQ2a224J420vZB03"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29c4c537bc12849274e7","userUid":"GutGWyaTg9OdhjecP1wzd35e0l83"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29c4c537bc12849274e7","userUid":"7hjchdyNdrO2itvdMRRj3b85wff2"}' http://localhost:8095/user/addTrip
echo -e ""

#Viaggio 17 IdViaggio:5c8a29fdc537bc12849274e9 IdCreatore:pfcytk2YLdb7qQ2a224J420vZB03 maxPartecipant:8
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29fdc537bc12849274e9","userUid":"kbz4sNCZtHMlfw3w3xCW3TTBco53"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29fdc537bc12849274e9","userUid":"Ip5ooeVpugPGm6I0SY5Y2TUNIki2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29fdc537bc12849274e9","userUid":"3ENghkkewyPdttrz00ImV0F0QpH2"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29fdc537bc12849274e9","userUid":"sSiOgobodkOlLkyzbIqnqxLQQq93"}' http://localhost:8095/user/addTrip
echo -e ""
curl -H "Content-Type: application/json" -X POST -d '{"tripId":"5c8a29fdc537bc12849274e9","userUid":"Tm8WrtKtVzco0him7RlnB9jUMmL2"}' http://localhost:8095/user/addTrip
echo -e ""

echo -e " ${reset}"

echo -e "Script end"
