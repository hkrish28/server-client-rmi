# Project 1

#### Running the Server and Client
* Execute `deploy.sh` to run the server application as a docker container and set up a docker network
* Execute `run_client.sh` to run a client that connects to the server. Examples to run the code is as below:
  * ./run_client.sh client-name 5555 udp (Runs the client interactively)
  * ./run_client.sh client-name 1111 tcp (Runs the client interactively)


#### Scripting

* The script `deploy.sh` should help you build and deploy server images along with a custom network

* The script `run_client.sh` script should help you start a client container on the same network

Note: Do not forget to change the permission of sh files to executable `chmod +x *.sh`

#### Running locally

* Compile the code using `javac src/server/*.java src/client/*.java src/shared/*.java`
* server usage should then be similar to `java src.server.ServerApp <tcp-port-number> <udp-port-number>`
* client usage should then be similar to `java src.client.ClientApp <host-name> <port-number> <protocol>`


#### When the client begins
* The client begins by running pre-population script which is present in the ClientInput.txt
* The client then prompts for user input of command
* User can exit the client application by inputting `exit`