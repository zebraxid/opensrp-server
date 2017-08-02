package org.opensrp.etl.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opensrp.etl.entity.ElcoEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ElcoRepository {
	
	public ElcoRepository() {
		// TODO Auto-generated constructor stub
	}
	
    private SessionFactory sessionFactory;

	
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public void addelco(ElcoEntity p) {
        Session session = this.sessionFactory.getCurrentSession();
        System.err.println("Session:"+session);
        try{
        session.persist(p);
        }catch(Exception e){
        	e.printStackTrace();
        }
        System.out.println("Phone saved successfully, Phone Details=" + p);
    }

}
