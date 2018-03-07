package demo.dao;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import demo.vo.Group;

/**
 * A data access object (DAO) providing persistence and search support for Group
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see demo.dao.Group
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class GroupDAO {
	private static final Logger log = LoggerFactory.getLogger(GroupDAO.class);
	// property constants
	public static final String GROUPNAME = "groupname";
	public static final String BUILDERID = "builderid";
	public static final String MEMBERCEILING = "memberceiling";
	public static final String EXISTMEMNUM = "existmemnum";
	public static final String LABELS = "labels";

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

	public void save(Group transientInstance) {
		log.debug("saving Group instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Group persistentInstance) {
		log.debug("deleting Group instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Group findById(java.lang.String id) {
		log.debug("getting Group instance with id: " + id);
		try {
			Group instance = (Group) getCurrentSession().get("demo.vo.Group",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Group instance) {
		log.debug("finding Group instance by example");
		try {
			List results = getCurrentSession().createCriteria("demo.vo.Group")
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
		log.debug("finding Group instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Group as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGroupname(Object groupname) {
		System.out.println("groupname+"+groupname);
		String mysqlstring="select * from `Group` where GROUPNAME "+" = \""+groupname+"\"";
		System.out.println("11:+"+mysqlstring);
		List catNameList =getCurrentSession().createSQLQuery(mysqlstring).addEntity(Group.class).list();
		System.out.println("found success,"+catNameList.size());
		return catNameList;
		//return findByProperty(GROUPNAME, groupname);
	}

	public List findByBuilderid(Object builderid) {
		return findByProperty(BUILDERID, builderid);
	}

	public List findByMemberceiling(Object memberceiling) {
		return findByProperty(MEMBERCEILING, memberceiling);
	}

	public List findByExistmemnum(Object existmemnum) {
		return findByProperty(EXISTMEMNUM, existmemnum);
	}

	public List findByLabels(Object labels) {
		return findByProperty(LABELS, labels);
	}

	public List findAll() {
		log.debug("finding all Group instances");
		try {
			String queryString = "from Group";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Group merge(Group detachedInstance) {
		log.debug("merging Group instance");
		try {
			Group result = (Group) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Group instance) {
		log.debug("attaching dirty Group instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Group instance) {
		log.debug("attaching clean Group instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GroupDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GroupDAO) ctx.getBean("GroupDAO");
	}
}