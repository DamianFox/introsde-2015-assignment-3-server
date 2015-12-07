package introsde.assignment.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlTransient;

import introsde.assignment.soap.dao.LifeCoachDao;

@Entity
@Table(name = "HealthProfile")
@NamedQuery(name = "HealthProfile.findAll", query = "SELECT mt FROM HealthProfile mt")
public class HealthProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_healthprofile")
	@TableGenerator(name="sqlite_healthprofile", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthProfile")
	
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private String value;

	@Column(name = "measure")
	private String measure;
	
	/*@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;*/
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public HealthProfile() {}
	
	/*public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}*/
	
	/*public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}*/

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static HealthProfile getHealthProfileById(int measuretypeId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthProfile p = em.find(HealthProfile.class, measuretypeId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthProfile> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthProfile> list = em.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthProfile saveHealthProfile(HealthProfile hp) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(hp);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return hp;
	}
	
	public static HealthProfile updateHealthProfile(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthProfile(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
