from neo4jrestclient.client import GraphDatabase
from random import randint

db = GraphDatabase("http://localhost:7474", username="neo4j", password="admin")

user = db.labels.create("User")
community = db.labels.create("Community")

for x in range(0,100000):
	u1=db.nodes.create(id=x, login="someLogin"+str(x), name="someName")
	u2=db.nodes.create(id=x+1000000, login="someLogin"+str(x+1000000), name="someName")
	u3=db.nodes.create(id=x+2000000, login="someLogin"+str(x+2000000), name="someName")
	u4=db.nodes.create(id=x+3000000, login="someLogin"+str(x+3000000), name="someName")
	u5=db.nodes.create(id=x+4000000, login="someLogin"+str(x+4000000), name="someName")
	user.add(u1)
	user.add(u2)
	user.add(u3)
	user.add(u4)
	user.add(u5)
	listu = [u1, u2, u3, u4, u5]
	
	
	c1=db.nodes.create(id= x, name="someCommName"+str(x))
	c2=db.nodes.create(id= 1000000+x, name="someCommName"+str(1000000+x))
	community.add(c1)
	community.add(c2)
	
	if randint(0,1):
		u1.relationships.create("subscribed", c1)
	else:
		u1.relationships.create("subscribed", c2)
	if randint(0,1):
		u2.relationships.create("subscribed", c1)
	else:
		u2.relationships.create("subscribed", c2)
	if randint(0,1):
		u3.relationships.create("subscribed", c1)
	else:
		u3.relationships.create("subscribed", c2)
	if randint(0,1):
		u4.relationships.create("subscribed", c1)
	else:
		u4.relationships.create("subscribed", c2)
	if randint(0,1):
		u5.relationships.create("subscribed", c1)
	else:
		u5.relationships.create("subscribed", c2)
	
	u1.relationships.create("friend",listu[randint(2,4)])
	u1.relationships.create("friend",listu[randint(2,4)])
	u1.relationships.create("friend",listu[randint(2,4)])
	u2.relationships.create("friend",listu[randint(3,4)])
	u2.relationships.create("friend",listu[randint(3,4)])
	u4.relationships.create("friend",u1)
