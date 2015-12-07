# Introsde 2015 Assignment 3 - Server

**Introduction to Service Design and Engineering | University of Trento**

This is the third assignment of the course **Introduction to Service Design and Engineering** of the **University of Trento**.

In this assignment we have to provide a WSDL service, which manages People's Health Profile.

## Important links

* [Server on Heroku][1]
* [Client][2]


## How to run the server

* GIT Clone to your machine
* Go to the project folder 
* Type `ant run`

### WSDL Functions handle by the server:

* readPersonList()
* readPerson(Long id)
* updatePerson(Person p)
* createPerson(Person p)
* deletePersonById(Long id)
* readPersonHistory(Long id, String measureType)
* readMeasureTypes()
* readPersonMeasure(Long id, String measureType, Long mid)
* savePersonMeasure(Long id, Measure m)
* supdatePersonMeasure(Long id, Measure m)



[1]: https://rocky-harbor-4297.herokuapp.com/ws/people?wsdl
[2]: https://github.com/DamianFox/introsde-2015-assignment-3-client
