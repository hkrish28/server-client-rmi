# Project Submissions Guidelines

## General guidelines
* Please spend some time to make a proper `ReadME` markdown file, explaining all the steps necessary to execute your source code.
* Do not hardcode IP address or port numbers, try to collect these configurable information from config file/env variables/cmd input args.
* Attach screenshots of your testing done on your local environment.

## Packaging the application
* We'll make use of [Docker](https://en.wikipedia.org/wiki/Docker_(software)) to package and distribute our applications as docker containers! Please spend some time understanding the [basics](https://docs.docker.com/get-started/) of docker.
* Please install Docker desktop


* Compile the code using `javac server/*.java client/*.java`
* server usage should then be similar to `java server.ServerApp <tcp-port-number> <udp-port-number>`
* client usage should then be similar to `java client.ClientApp <host-name> <port-number> <protocol>`

#### Scripting

* The script `deploy.sh` should help you build and deploy server images along with a custom network

* The script `run_client.sh` script should help you start a client container on the same network

Note: Do not forget to change the permission of sh files to executable `chmod +x *.sh`

#### Running the Server and Client
* Execute `deploy.sh` to run the server application as a docker container and set up a docker network
* Execute `run_client.sh` to run a client that connects to the server. Examples to run the code is as below:
  * ./run_client.sh client-name 5555 udp (Runs the client interactively)
  * ./run_client.sh client-name 1111 tcp (Runs the client interactively)


#### Running locally
* # java client.ClientApp localhost 1111 tcp
