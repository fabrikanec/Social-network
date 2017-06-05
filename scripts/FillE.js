function getRandomInt(min, max){
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

db = db.getSiblingDB('DBName');
nameArray = [
	("Bob"),("Mark"),("Paul"),("Jack"),("Jimmy"),("Cassandra"),("Steve"),("Arnold")]

subjArray = [
	("SomeSubj1"),("SomeSubj2"),("SomeSubj3"),("SomeSubj4"),("SomeSubj5"),("SomeSubj6"),("SomeSubj7"),("SomeSubj8"),("SomeSubj9"),("SomeSubj10")]

textArray = [
	("SomeText1"),("SomeText2"),("SomeText3"),("SomeText4"),("SomeText5"),("SomeText6"),("SomeText7"),("SomeText8"),("SomeText9"),("SomeText10")]


for(i = 0; i < 1000000; i++) {
	event_id = i;
	user_id = getRandomInt(0,10000);
	name = nameArray[getRandomInt(0,7)];
	subj = subjArray[getRandomInt(0,9)];
	myText = textArray[getRandomInt(0,9)];
	
		db.Event.insert({
			"event_id": event_id,
			"user_id": user_id,
			"name": name,
			"text": myText,
			"subj": subj
		});
}
