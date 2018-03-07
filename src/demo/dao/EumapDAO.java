package demo.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Gumap;

/**
 * A data access object (DAO) providing persistence and search support for Eumap
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see demo.dao.Eumap
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EumapDAO {
	private static final Logger log = LoggerFactory.getLogger(EumapDAO.class);
	// property constants
	public static final String EVENTID = "eventid";
	public static final String USERID = "userid";
	public static final String ISBUILDER = "isbuilder";

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	public void save(Eumap transientInstance) {
		log.debug("saving Eumap instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Eumap persistentInstance) {
		log.debug("deleting Eumap instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Eumap findById(java.lang.String id) {
		log.debug("getting Eumap instance with id: " + id);
		try {
			Eumap instance = (Eumap) getCurrentSession().get("demo.vo.Eumap",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Eumap instance) {
		log.debug("finding Eumap instance by example");
		try {
			List results = getCurrentSession().createCriteria("demo.vo.Eumap")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Eumap instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Eumap as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEventid(Object eventid) {
		String mysqlstring="select * from Eumap where EVENTID = \""+eventid+"\"";
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Eumap.class).list();
		return catNameList;
		//return findByProperty(EVENTID, eventid);
	}

	public List findByUserid(Object userid) {  
		String mysqlstring="select * from Eumap where USERID  = \""+userid+"\"";
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Eumap.class).list();
		return catNameList;
		//return findByProperty(USERID, userid);
	}
	public List findByUseridAndEventid(String eventid,String userid)
	{
		String sql="select * from Eumap where EVENTID = \""+eventid+"\" and USERID = \""+userid+"\"";
		System.out.println("sql in eumapDAO:"+sql);
		List catNameList =getCurrentSession().createSQLQuery(sql).addEntity(Eumap.class).list();
		return catNameList;

	}
	public List findbuilder(String eventid)
	{
		String sql="select * from Eumap where EVENTID = \""+eventid+"\" and ISBUILDER = \"yes\"";
		System.out.println("sql in eumapDAO:"+sql);
		List catNameList =getCurrentSession().createSQLQuery(sql).addEntity(Eumap.class).list();
		return catNameList;
	}
	
	public List findByIsbuilder(Object isbuilder) {
		return findByProperty(ISBUILDER, isbuilder);
	}

	public List findAll() {
		log.debug("finding all Eumap instances");
		try {
			String queryString = "from Eumap";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Eumap merge(Eumap detachedInstance) {
		log.debug("merging Eumap instance");
		try {
			Eumap result = (Eumap) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Eumap instance) {
		log.debug("attaching dirty Eumap instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Eumap instance) {
		log.debug("attaching clean Eumap instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EumapDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EumapDAO) ctx.getBean("EumapDAO");
	}
}