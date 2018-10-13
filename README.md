# Taxi-Service-Aggregator-

Nowadays services like Ola and Uber have become very popular. These
services aggregate the services of a group of drivers who offer their cars
as taxis through the platform provided by the company. One feature that
makes these services particularly attractive is that their apps inform you of
the time it will take for the nearest taxi to reach your current location. In
this project we developed the software structure required to support
this kind of service.

We have made the taxi search problem slightly more realistic. Firstly, the taxis are always be in motion. Either they
are wandering through the graph or they are servicing a customer request. The taxis are located at different vertices
at the start of the program. However, instead of waiting at the vertices of the graph, each taxi chooses one destination
vertex and starts moving towards it. Once it reaches the destination, the taxi chooses another destination and
starts moving towards it. A taxi changes this path only when it is chosen to service a customer request. For the sake of 
simplicity, we have assumed that a taxi can immediately take a U-turn from any point on the map. When a
customer requests for a taxi, we search for a taxi which can reach the customer at the earliest; 
irrespective of whether the taxi is currently available or it is servicing a customer request (car-pooling :)).
