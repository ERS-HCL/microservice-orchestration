export const uiConfig = {
    "protocol": "http",
    "host": "localhost",
    "port": "200",
    "domain": "hcl.com",
    "configPort": "4000",
    "apiConfig": {
        "patient": {
            "module": "fhir/Patient",
		    "port": "200"
        },
        "bp": {
            "module1": "measurement/check-in",
            "module2": "measurement/bp-data",
		    "port": "200"
        }
    }
}