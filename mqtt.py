import paho.mqtt.client as mqtt
import sense
import time


# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, rc):
    print("Connected with result code "+str(rc))
    
	# Subscribing in on_connect() means that if we lose the connection and
	# reconnect then subscriptions will be renewed.


# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
	print(msg.topic+" "+str(msg.payload))

# notre mother sense
sense.api_key = '4a5f9881e53ba0a3cf237fa1c20737a0dbfe5e99'


# print feed.events.list()




# initialisation du client mqtt
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.connect("localhost", 1883, 60)
client.loop_start()

while True :
	
	# cookie correspondant a la temperature
	feed = sense.Feed.retrieve('txArRCgqmNcXPVE3a1GGrurbZjlHfCGA')
	
	# recuperation de la derniere temperature mise a jour
	temperature = str(feed.events.list(limit=1).objects[0].data.centidegreeCelsius)
	client.publish("lampe",temperature)
	
	# attendre 
	time.sleep(10)


