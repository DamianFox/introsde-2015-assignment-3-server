package introsde.assignment.soap.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.HealthProfile;
import introsde.assignment.soap.model.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface People {
    
	@WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();
	
	@WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="id") int id);
	
	@WebMethod(operationName="updatePerson")
    @WebResult(name="id") 
    public Person updatePerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="createPerson")
    @WebResult(name="id") 
    public Person createPerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="deletePerson")
    @WebResult(name="id") 
    public boolean deletePerson(@WebParam(name="id") int id);

    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="HealthMeasureHistory")
    public List<HealthMeasureHistory> readPersonHistory(@WebParam(name="id") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="MeasureDefinition")
    public List<String> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measures") 
    public List<HealthMeasureHistory> readPersonMeasure(@WebParam(name="id") int id, @WebParam(name="measureType") String measureType,
    		@WebParam(name="mid") int mid);
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="HealthProfile") 
    public HealthProfile savePersonMeasure(@WebParam(name="id") int id, @WebParam(name="healthprofile") HealthProfile hp);
    
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="HealthProfile") 
    public HealthMeasureHistory updatePersonMeasure(@WebParam(name="id") int id, @WebParam(name="healthprofile") HealthMeasureHistory hp);
}