package org.opensrp.register.mcare.dump.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opensrp.register.mcare.dump.entities.HousoholdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HouseholdDAO {
	

	public HouseholdDAO() {
		// TODO Auto-generated constructor stub
	}
	private SessionFactory sessionFactory;

	
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    
    public void addHousehold(HousoholdEntity p) {
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
