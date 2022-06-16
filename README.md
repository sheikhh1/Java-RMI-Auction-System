# Java-RMI-Auction-System

Uses Remote Method Invocation in the context of a auction system. Active Replication is used and new replicas can be spun up on differenct terminal enviroments. There is also a client and a seller. Each replica will remain consistent with eachother and have a majority consenus. If there is an issue with a replica, then the state is terminated. This also uses 5 stage authentication between the client and front end API. 

Architectural Diagram: 

![311](https://user-images.githubusercontent.com/60651558/174171377-ad4aa760-4034-4915-87cf-09ba1b9ab5d8.jpg)
