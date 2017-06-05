function getRandomInt(min, max){
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

db = db.getSiblingDB('document');
let nameArray = [
	("Bob"),("Mark"),("Paul"),("Jack"),("Jimmy"),("Cassandra"),("Steve"),("Arnold")]

let titleArray = [
	("SomeTitle1"),("SomeTitle2"),("SomeTitle3"),("SomeTitle4"),("SomeTitle5"),("SomeTitle6"),("SomeTitle7"),("SomeTitle8"),("SomeTitle9"),("SomeTitle10")]

let textArray = [
	("SomeText1"),("SomeText2"),("SomeText3"),("SomeText4"),("SomeText5"),("SomeText6"),("SomeText7"),("SomeText8"),("SomeText9"),("SomeText10")]
	
	
for(let i = 0; i < 1000000; i++) {
	article_id = i;
	publisher = nameArray[getRandomInt(0,7)];
	title = titleArray[getRandomInt(0,9)];
	myText = textArray[getRandomInt(0,9)];
	
		db.Articles.insert({
			"article_id": article_id,
			"publisher": publisher,
			"title": title,
			"text": myText
		});
	
}
