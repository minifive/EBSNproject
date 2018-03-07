package demo.dao;

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

import demo.vo.Gumap;

/**
 * A data access object (DAO) providing persistence and search support for Gumap
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see demo.dao.Gumap
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class GumapDAO {
	private static final Logger log = LoggerFactory.getLogger(GumapDAO.class);
	// property constants
	public static final String GROUPID = "groupid";
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

	public void save(Gumap transientInstance) {
		log.debug("saving Gumap instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Gumap persistentInstance) {
		log.debug("deleting Gumap instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			System.out.println("delete failed");
			log.error("delete failed", re);
			throw re;
		}
	}

	public Gumap findById(java.lang.String id) {
		log.debug("getting Gumap instance with id: " + id);
		try {
			Gumap instance = (Gumap) getCurrentSession().get("demo.vo.Gumap",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Gumap instance) {
		log.debug("finding Gumap instance by example");
		try {
			List results = getCurrentSession().createCriteria("demo.vo.Gumap")
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
		log.debug("finding Gumap instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Gumap as model where model."
					+ propertyName + "= ?";			
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGroupid(Object groupid) {
		System.out.println("find by groupid begins:"+groupid);
		String mysqlstring="select * from Gumap where GROUPID "+" = \""+groupid+"\"";
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Gumap.class).list();
		//findByProperty(GROUPID, groupid);
		return catNameList;
	}

	public List finduserfromgroup(String userid,String groupid){//
		//System.out.println("delete user begins:"+groupid);
		String sql="select * from Gumap where GROUPID = \""+groupid+"\" and USERID =\""+ userid+"\"";
		List gumap=getCurrentSession().createSQLQuery(sql).addEntity(Gumap.class).list();
		return gumap;
	}
	
	public List findByUserid(Object userid) {
		String sql="select * from Gumap where USERID = \""+ userid+"\"";
		List gumap=getCurrentSession().createSQLQuery(sql).addEntity(Gumap.class).list();
		return gumap;
		//return findByProperty(USERID, userid);
	}
	public List findByIsbuilder(Object isbuilder) {
		return findByProperty(ISBUILDER, isbuilder);
	}

	public List findAll() {
		log.debug("finding all Gumap instances");
		try {
			String queryString = "from Gumap";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Gumap merge(Gumap detachedInstance) {
		log.debug("merging Gumap instance");
		try {
			Gumap result = (Gumap) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Gumap instance) {
		log.debug("attaching dirty Gumap instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Gumap instance) {
		log.debug("attaching clean Gumap instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GumapDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GumapDAO) ctx.getBean("GumapDAO");
	}
}