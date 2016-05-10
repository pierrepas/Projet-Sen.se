import sense
import paho.mqtt.client as mqtt
import time

# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, rc):
    print("Connected with result code "+str(rc))
    
	# Subscribing in on_connect() means that if we lose the connection and
	# reconnect then subscriptions will be renewed.


# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
	print(msg.topic+" "+str(msg.payload))

#notre mother sense, cle fournie sur le site de sen.se
sense.api_key = '4a5f9881e53ba0a3cf237fa1c20737a0dbfe5e99'

# initialisation du client mqtt
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.connect("127.0.0.1", 1883, 60)
client.loop_start()

#connexion au noeud contenant les informations du capteur de temperature
nodeTemp = None

#on recherche le premier capteur de temperature dans la liste des capteurs
for i in range(1,len(sense.Node.list().objects)-1):
	if sense.Node.list().objects[i].resource.slug == "temperature":
		nodeTemp = sense.Node.retrieve(sense.Node.list().objects[i].uid)
		break

if nodeTemp == None:
	print "Il n'y a pas de capteur de temperature"

#connexion a la categorie temperature du capteur
feedTemp = sense.Feed.retrieve(nodeTemp.subscribes[0].uid)

while True:
	temperature = str(feedTemp.events.list(limit=1).objects[0].data.centidegreeCelsius/100)
	#publication de la temperature sur le serveur mosquitto dans le topic temperature
	client.publish("temp", "setTemperature -temp "+temperature+" -l 1")

	#attente de 10s avant de republier
	time.sleep(10)
