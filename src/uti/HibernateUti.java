package uti;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUti {
	private static SessionFactory sessf=null;
	public static SessionFactory getSessionFactory() {
		if(sessf==null) {
			sessf=new Configuration().configure().buildSessionFactory();
		}
		return sessf;
	}
}
