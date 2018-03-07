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

import demo.vo.Event;

/**
 * A data access object (DAO) providing persistence and search support for Event
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see demo.dao.Event
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class EventDAO {
	private static final Logger log = LoggerFactory.getLogger(EventDAO.class);
	// property constants
	public static final String EVENT_NAME = "eventName";
	public static final String POSTER = "poster";
	public static final String COORDINATE = "coordinate";
	public static final String MAXNUM = "maxnum";
	public static final String EXSTNUM = "exstnum";
	public static final String ADDRESS = "address";
	public static final String GROUPID = "groupid";
	public static final String STATE = "state";
	public static final String INTRODUCE = "introduce";

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

	public void save(Event transientInstance) {
		log.debug("saving Event instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Event persistentInstance) {
		log.debug("deleting Event instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Event findById(java.lang.String id) {
		log.debug("getting Event instance with id: " + id);
		try {
			Event instance = (Event) getCurrentSession().get("demo.vo.Event",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	public List findByExample(Event instance) {
		log.debug("finding Event instance by example");
		try {
			List results = getCurrentSession().createCriteria("demo.vo.Event")
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
		log.debug("finding Event instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Event as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEventName(Object eventName) {
		return findByProperty(EVENT_NAME, eventName);
	}

	public List findByPoster(Object poster) {
		return findByProperty(POSTER, poster);
	}

	public List findByCoordinate(Object coordinate) {
		return findByProperty(COORDINATE, coordinate);
	}

	public List findByMaxnum(Object maxnum) {
		return findByProperty(MAXNUM, maxnum);
	}

	public List findByExstnum(Object exstnum) {
		return findByProperty(EXSTNUM, exstnum);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByGroupid(Object groupid) {
		String mysqlstring="select * from Event where GROUPID = \""+groupid+"\"  order by STARTTIME desc";
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Event.class).list();
		return catNameList;
		//return findByProperty(GROUPID, groupid);
	}
	/*public Event findbyid(String id)
	{
		String mysqlstring="select * from Event where EVENTID = \""+id+"\"";
		Event e=(Event) getCurrentSession().createSQLQuery(mysqlstring).addEntity(Event.class);
		return e;
	}*/
	public List mysearch(String key){
		String mysqlstring="select * from Event where EVENTNAME like \"%"+key+"%\" order by STARTTIME desc";
		System.out.println(mysqlstring);
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Event.class).list();
		return catNameList;
	}
	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByIntroduce(Object introduce) {
		return findByProperty(INTRODUCE, introduce);
	}

	public List findAll() {
		log.debug("finding all Event instances");
		try {
			//String queryString = "from Event";
			String queryString="from Event  order by STARTTIME desc";
			System.out.println("EventDAO:"+queryString);
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Event merge(Event detachedInstance) {
		log.debug("merging Event instance");
		try {
			Event result = (Event) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Event instance) {
		log.debug("attaching dirty Event instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Event instance) {
		log.debug("attaching clean Event instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EventDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EventDAO) ctx.getBean("EventDAO");
	}
}