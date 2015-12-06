package introsde.assignment.soap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlTransient;

import introsde.assignment.soap.dao.LifeCoachDao;

/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Table(name="MeasureDefinition")
@NamedQuery(name="MeasureDefinition.findAll", query="SELECT m FROM MeasureDefinition m")
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuretype")
	@TableGenerator(name="sqlite_measuretype", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="MeasureDefinition")
	@Column(name="idMeasureDef")
	private int idMeasureDef;

	@Column(name="measureName")
	private String measureName;

	@Column(name="measureType")
	private String measureType;

	/*@OneToMany(mappedBy="measureDefinition")
	private List<MeasureDefaultRange> measureDefaultRange;*/

	public MeasureDefinition() {
	}

	@XmlTransient
	public int getIdMeasureDef() {
		return this.idMeasureDef;
	}

	public void setIdMeasureDef(int idMeasureDef) {
		this.idMeasureDef = idMeasureDef;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	@XmlTransient
	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	/*public List<MeasureDefaultRange> getMeasureDefaultRange() {
	    return measureDefaultRange;
	}

	public void setMeasureDefaultRange(List<MeasureDefaultRange> param) {
	    this.measureDefaultRange = param;
	}*/

	// database operations
	public static MeasureDefinition getMeasureDefinitionById(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureDefinition p = em.find(MeasureDefinition.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static MeasureDefinition getMeasureDefinitionByTitle(String mdDesc){
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("LOOKING FOR  "+mdDesc);
		MeasureDefinition md = new MeasureDefinition();
		try{
             md = (MeasureDefinition) em.createQuery(        
            "SELECT md FROM MeasureDefinition md WHERE md.measureName = :measurenm ")
            .setParameter("measurenm", mdDesc)
            .getSingleResult();        

        }catch(Exception e){
            System.out.println("Error"+e);
            
        }
        System.out.println("Measurement "+mdDesc+" found");
        LifeCoachDao.instance.closeConnections(em);
        return md;
    }
	
	public static List<MeasureDefinition> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();

	    List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class).getResultList();
	    
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static int getIdByName(String measureName) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		int id = 0;
	    
	    System.out.println("GETTING MEASURE DEFINITION ID BY NAME!!!");
	    
	    List<MeasureDefinition> list = em.createQuery("SELECT m FROM MeasureDefinition m WHERE m.measureName = :measureName")
	    .setParameter("measureName", measureName).getResultList();
	    
	    if(!list.isEmpty()){
	    	MeasureDefinition m = list.get(0);
		    id = m.getIdMeasureDef();
	    }
	    
	    LifeCoachDao.instance.closeConnections(em);
	    return id;
	}
	
	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static MeasureDefinition updateMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
