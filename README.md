## Microservice-Orchestration
### ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯

[Zeebe](https://zeebe.io) is a workflow engine for microservice orchestration.
This Application is a sample implementation of zeebe which demonstrates a simple workflow process happening in a patient care center.

### Use Case :

	1.	There are 3 actors Doctor, Patient & care taker
	2.	Patient check-in and care taker collects medical information and Measures the BP(Blood pressure)
	3.	BP devices is a connected and  pushes BP data
	4.	The care taker submits the information
	5.	If the BP value is normal, he can walk out.
	6.	If the value is abnormal, an appointment is booked.
	7.	And a payment claim is initiated with insurance.
	
 Workflow process

![alt text][wf]

[wf]: document/BP-process-clinic.png "Workflow"

### Architecture :
![alt text][arch]

[arch]: document/WF_POC.png "Architecture"

### Demo : 
![alt text][demo]

[demo]: document/zeebe.gif "DEMO"

### Setup : 

To run the application following application should be started and running without error.
(https://docs.zeebe.io/java-client/get-started.html)
1. Zeebe broker configured  with elastic search
2. Elastic search 
3. Operate server
4. Mongo server

Once above server are started, start the two microservice applications and angular for UI.
To avoid CORS put the applications behind nginx and use it as reverse proxy

```javascript
server {
		    listen       1200;
			
			location /{
				  proxy_pass http://localhost:4200;
				  proxy_set_header X-Real-IP $remote_addr;
				  proxy_set_header Host $host:$server_port;
				  proxy_connect_timeout 15s;
				}
				
			 location /sockjs-node/ {
					proxy_http_version 1.1;
					proxy_set_header Upgrade $http_upgrade;
					proxy_set_header Connection "upgrade";
					rewrite ^/(.*)$  /$1  break;
					proxy_set_header Host localhost;
					proxy_pass http://localhost:4200/;
				}
			location /fhir/ {
				  proxy_pass http://localhost:8091;
				  proxy_set_header X-Real-IP $remote_addr;
				  proxy_set_header Host $host:$server_port;
				  proxy_connect_timeout 15s;
				}
			location /measurement/ {
				  proxy_pass http://localhost:8091;
				  proxy_set_header X-Real-IP $remote_addr;
				  proxy_set_header Host $host:$server_port;
				  proxy_connect_timeout 15s;
				}
	}
```

	





