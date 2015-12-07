package introsde.assignment.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElementWrapper;

import introsde.assignment.soap.dao.LifeCoachDao;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="idPerson")
    int idPerson;
    @Column(name="lastname")
    String lastname;
    @Column(name="firstname")
    String firstname;
    @Column(name="birthdate")
    String birthdate;
    
    // mappedBy must be equal to the name of the attribute in MeasureType that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthProfile> healthProfile;
    
    @XmlElementWrapper(name = "healthProfiles")
    public List<HealthProfile> getHealthProfile() {
        return healthProfile;
    }
    // add below all the getters and setters of all the private attributes
    
    // getters
    public int getIdPerson(){
        return this.idPerson;
    }

    public String getLastname(){
        return this.lastname;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getBirthdate(){
        return this.birthdate;
    }
    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setBirthdate(String birthdate){
        this.birthdate = birthdate;
    }
    public void setHealthProfile(List<HealthProfile> healthProfile) {
        this.healthProfile = healthProfile;
    }

    public void addPersonToHealthProfile(){
        for(int i=0; i<healthProfile.size(); i++){
            this.healthProfile.get(i).setPerson(this);
        }
    }
    
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }
    
    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        if(p.healthProfile != null){
            p.addPersonToHealthProfile();
        }
        /*if(p.birthdate != null){
            this.setBirthdate(p.birthdate);
        }
        if(p.lastname != null){
            this.setLastname(p.lastname);
        }
        if(p.firstname != null){
            this.setFirstname(p.firstname);
        }*/
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
    	/*if (p.birthdate != null) {
    		this.setBirthdate(p.birthdate);
    	}
    	if (p.firstname != null) {
    		this.setFirstname(p.firstname);
    	}
    	if (p.lastname != null) {
    		this.setLastname(p.lastname);
    	}*/

    	EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		System.out.println("Person updated");
		return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
}