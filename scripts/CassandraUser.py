from cassandra.cqlengine.columns import *
from cassandra.cqlengine.models import Model
from cassandra.cqlengine.usertype import UserType



class Comment(UserType):
    comment_id = UUID()
    user_id = UUID()
    article_id = UUID()
    event_id = UUID()
    text=Text()

class Community(UserType):
    community_id = UUID()
    community_name = Text()
    users = Set(UUID);

class Event(UserType):
    event_id = UUID()
    user_id = UUID()
    name = Text()
    text = Text()
    subj = Text()

class Article(UserType):
    id = UUID()
    title = Text()
    publisher = Text()
    text = Text()	
	
class Message(UserType):
    message_id = UUID()
    user_id = UUID()
    receaverMsgDeletedFlag = Boolean()
    posterMsgDeletedFlag = Boolean()
    text = Text()
    date = DateTime()
	
class User(Model):
    __keyspace__ = 'columnkeyspace'
    user_id = UUID(primary_key=True)
    auth_token = UUID()
    userComments = UserDefinedType(Comment)
    userEvents = UserDefinedType(Event)
    userArticles = UserDefinedType(Article)
    userMessages = UserDefinedType(Message)
    userCommunities = UserDefinedType(Community)
    userFrinds = Set(UUID)
    password = Text()
    firstname = Text()
    lastname = Text()

User.create(user_id=now(), auth_token=now(), userComments=Comment(comment_id=now(), user_id=1, article_id=1, event_id=1, text="myText"),


userEvents=Event(event_id=1, used_id=1, name="myName", text="myText", subj="mySubj"),
userArticles=Article(id=1, title="myTitle", publisher="myPublisher", text="myText"),
userMessages=Message(message_id=1, used_id=1, receaverMsgDeletedFlag=False, posterMsgDeletedFlag=False, text="myText", date=datetime.now() ),
userFrinds=({111, 222, 333}),
password="qwerty",
firstname="Big",
lastname="Lebowski")
#userCommunities=Null, #FUCKDATSHIT ILYUSHA)
