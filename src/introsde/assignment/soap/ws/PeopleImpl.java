package introsde.assignment.soap.ws;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.HealthProfile;
import introsde.assignment.soap.model.MeasureDefinition;
import introsde.assignment.soap.model.Person;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
serviceName="PeopleService")
public class PeopleImpl implements People {

	@Override
    public List<Person> readPersonList() {
		System.out.println("---> Reading all Persons");

		List<Person> pList = Person.getAll();
		System.out.println("---> Found " + pList.size() + " Persons");
		
		return pList;
    }

    @Override
    public Person readPerson(int id) {
        System.out.println("---> Reading Person by id = "+id);

        Person p = Person.getPersonById(id);
        if (p != null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Person not found ");
        }
        System.out.println("Here is the person id ==> " + p.getIdPerson());
        return p;
    }
    
    @Override
    public Person updatePerson(Person person) { 	
    	Person.updatePerson(person);
    	
        return person;
    }

    @Override
    public Person createPerson(Person person) {
        return Person.savePerson(person);
    }

    @Override
    public boolean deletePerson(int id) {
        Person p = Person.getPersonById(id);
        
        if (p != null) {
            Person.removePerson(p);
            return true;
        } else {
            return false;
        }
    }

	@Override
	public List<HealthMeasureHistory> readPersonHistory(int id, String measureType) {
		List<HealthMeasureHistory> measureHistories = HealthMeasureHistory.getAllByPerson(id, measureType);
        /*if (measureHistories == null)
            throw new RuntimeException("Get: HealthMeasureHistory with " + id + " not found");*/
        return measureHistories;
	}
	
	public List<String> readMeasureTypes(){
		List<String> measureType = new ArrayList<String>();

		List<MeasureDefinition> md = MeasureDefinition.getAll();

		for(int i=0; i<md.size(); i++){
			MeasureDefinition m = md.get(i);
			measureType.add(m.getMeasureName());
		}
		
		return measureType;
	}

	@Override
	public List<HealthMeasureHistory> readPersonMeasure(int id, String measureType, int mid) {
		List<HealthMeasureHistory> measureHistories = HealthMeasureHistory.getMeasureHistoryById(id, measureType, mid);
        return measureHistories;
	}

	@Override
	public HealthProfile savePersonMeasure(int idPerson, HealthProfile hp) {
			
		String timestamp = "";
		boolean isPresent = false;
    	Person p = Person.getPersonById(idPerson);
    	
    	String measureType = hp.getMeasure();
    	
    	int idMeasureValue = MeasureDefinition.getIdByName(measureType);
    	
    	List<HealthProfile> personHealthProfiles = p.getHealthProfile();    	
    	
    	for(int i = 0; i < personHealthProfiles.size(); i++){
    		HealthProfile singleHealthProfile = personHealthProfiles.get(i);
    		String measure = singleHealthProfile.getMeasure();
    		int measureDefinition = MeasureDefinition.getIdByName(measure);

    		if(measureDefinition == idMeasureValue){

    			isPresent = true;
    			HealthMeasureHistory hmhNew = new HealthMeasureHistory();
    			
    			hmhNew.setValue(singleHealthProfile.getValue());
    			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			Date date = new Date();
    			timestamp = dateFormat.format(date);
    			hmhNew.setTimestamp(timestamp);
    			hmhNew.setPerson(p);
    			MeasureDefinition meaDef = MeasureDefinition.getMeasureDefinitionById(measureDefinition);
    			hmhNew.setMeasureDefinition(meaDef);
    			hp.setPerson(p);
    			
    			singleHealthProfile.setValue(hp.getValue());
    			
    			HealthProfile newHealthProfile = HealthProfile.saveHealthProfile(hp);
    			
    			HealthMeasureHistory.saveHealthMeasureHistory(hmhNew);

    			return newHealthProfile;
    		}
    	}
    	
		if(isPresent == false && idMeasureValue != -1){

			MeasureDefinition measureDef = new MeasureDefinition();
			
			HealthProfile singleHealthProfile = new HealthProfile();
			
			measureDef.setIdMeasureDef(idMeasureValue);
			measureDef.setMeasureName(measureType);
			
			singleHealthProfile.setPerson(p);
			singleHealthProfile.setValue(hp.getValue());
			singleHealthProfile.setMeasure(hp.getMeasure());
			
			HealthProfile newHealthProfile = HealthProfile.saveHealthProfile(singleHealthProfile);

			return newHealthProfile;
    	}

    	return null;
	}

	@Override
	public HealthMeasureHistory updatePersonMeasure(int id, HealthMeasureHistory hmi) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
    	list = em.createQuery("SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idP AND h.idMeasureHistory = :idMeasureMid")
	    	.setParameter("idP", id)
	    	.setParameter("idMeasureMid", hmi.getIdMeasureHistory())
	    	.getResultList();
    	
    	HealthMeasureHistory hmiUpdate = list.get(0);
    	hmiUpdate.setValue(hmi.getValue());
    	
		return HealthMeasureHistory.updateHealthMeasureHistory(hmiUpdate);
	}

}