package introsde.assignment.soap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.assignment.soap.dao.LifeCoachDao;


/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="HealthMeasureHistory")
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	@Column(name="timestamp")
	private String timestamp;

	@Column(name="value")
	private String value;

	/*@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;*/

	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "idMeasureDefinition", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;

	public HealthMeasureHistory() {}

	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	/*@XmlElement(name = "description")
	public String getMeasureDefScription(){
		return measureDefinition.getMeasureName();
	}*/

	@XmlElement(name = "created")
	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
	    return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
	    this.measureDefinition = param;
	}

	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static List<HealthMeasureHistory> getAllByPerson(long idPerson, String measureName) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
    	int idMeasureDefinition = MeasureDefinition.getIdByName(measureName);
    	List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
    	list = em.createQuery("SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idP AND h.measureDefinition.idMeasureDef = :idMD")
	    	.setParameter("idP", idPerson)
	    	.setParameter("idMD", idMeasureDefinition)
	    	.getResultList();

	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static List<HealthMeasureHistory> getMeasureHistoryById(long idPerson, String measureName, long mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
    	int idMeasureDefinition = MeasureDefinition.getIdByName(measureName);
    	List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
    	list = em.createQuery("SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idP AND h.measureDefinition.idMeasureDef = :idMD AND h.idMeasureHistory = :mid")
	    	.setParameter("idP", idPerson)
	    	.setParameter("idMD", idMeasureDefinition)
	    	.setParameter("mid", mid)
	    	.getResultList();

	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
