package introsde.assignment.soap.client;

import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.HealthProfile;
import introsde.assignment.soap.ws.People;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient{
    public static void main(String[] args) throws Exception {
    	try{
    		
    	}catch (Exception e){
    		System.out.println("ERROR --> " + e);
    	}
    	
    	URL url = new URL("http://192.168.1.3:6900/ws/people?wsdl");
        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.soap.assignment.introsde/", "PeopleService");
        Service service = Service.create(url, qname);

        People people = service.getPort(People.class);
        
        Person p = people.readPerson(3);
        System.out.println("Person " + p);
        System.out.println("Id person " + p.getIdPerson());
        System.out.println("Firstname " + p.getFirstname());
        System.out.println("Lastname " + p.getLastname());
        System.out.println("Birthdate " + p.getBirthdate());
        
        //p.setIdPerson(3);
        p.setFirstname("Pavel");
        p.setLastname("Kucherbaev");
        p.setBirthdate("1990-01-01");
        Person personUp = people.updatePerson(p);
        
        Person newPerson = new Person();
        newPerson.setFirstname("Maurizio");
        newPerson.setLastname("Marchese");
        newPerson.setBirthdate("1967-01-01");
        Person personCreated = people.createPerson(newPerson);
        
        boolean isDeleted = people.deletePerson(2);
        
        List<Person> pList = people.readPersonList();
        
        List<String> measures = people.readMeasureTypes();
        
        List<HealthMeasureHistory> hList = people.readPersonHistory(3, "height");
        
        List<HealthMeasureHistory> hmList = people.readPersonMeasure(3, "height", 4);
        
        HealthMeasureHistory hmi = hmList.get(0);
        hmi.setValue("60");
        
        people.updatePersonMeasure(3, hmi);
        
        HealthProfile hp = new HealthProfile();
        hp.setMeasure("weight");
        hp.setValue("75");
        people.savePersonMeasure(6, hp);
        
        
        System.out.println("Result person ==> "+p);
        System.out.println("Result people ==> "+pList);
        System.out.println("Result person history ==> "+hList);
        System.out.println("Result person history by id==> "+hmList);
        System.out.println("Measures ==> "+measures);
        System.out.println("First Person in the list ==> "+pList.get(0).getFirstname());
        System.out.println("Is Deleted ==> "+isDeleted);
        System.out.println("Person updated ==> " + personUp);
        System.out.println("Person created ==> " + personCreated.getIdPerson());
    }
}