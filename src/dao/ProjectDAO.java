package dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Project;
import uti.HibernateUti;

public class ProjectDAO implements DAO<Project> {

	@Override
	public List<Project> findALL() {
		// TODO Auto-generated method stub
		SessionFactory sf=HibernateUti.getSessionFactory();
		Session sess=sf.openSession();
		Transaction tx=sess.beginTransaction();
		try {
			List<Project> list=sess.createCriteria(Project.class).list();
			tx.commit();
			return list;
			
		} catch (Exception e) {
			if (tx!=null)
				tx.rollback();
		}
		return null;
	}

	@Override
	public Project findByid(Serializable id) {
		// TODO Auto-generated method stub
				SessionFactory sf=HibernateUti.getSessionFactory();
				Session sess=sf.openSession();
				Transaction tx=sess.beginTransaction();
				try {
					Project c=(Project) sess.get(Project.class,id);
					tx.commit();
					return c;
					
				} catch (Exception e) {
					if (tx!=null)
						tx.rollback();
				}
				return null;
		
	}

	@Override
	public boolean delete(Serializable id) {
		SessionFactory sf=HibernateUti.getSessionFactory();
		Session sess=sf.openSession();
		Transaction tx=sess.beginTransaction();
		try {
			Project c=(Project) sess.get(Project.class,id);
			sess.delete(c);
			tx.commit();
			return true;
			
		} catch (Exception e) {
			if (tx!=null)
				tx.rollback();
		}
		return false;
	}

	@Override
	public boolean add(Project t) {
		SessionFactory sf=HibernateUti.getSessionFactory();
		Session sess=sf.openSession();
		Transaction tx=sess.beginTransaction();
		try {
			sess.save(t);
			tx.commit();
			return true;
			
		} catch (Exception e) {
			if (tx!=null)
				tx.rollback();
		}
		return false;
	}

	@Override
	public boolean update(Project t) {
		SessionFactory sf=HibernateUti.getSessionFactory();
		Session sess=sf.openSession();
		Transaction tx=sess.beginTransaction();
		try {
			sess.saveOrUpdate(t);
			tx.commit();
			return true;
			
		} catch (Exception e) {
			if (tx!=null)
				tx.rollback();
		}
		return false;
	}

}
