package org.opensrp.etl.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opensrp.etl.entity.HousoholdEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HouseholdRepository {
	

	public HouseholdRepository() {
		// TODO Auto-generated constructor stub
	}
	private SessionFactory sessionFactory;

	
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    
    public void addHousehold(HousoholdEntity p) {
        Session session = this.sessionFactory.getCurrentSession();
       
        try{
        session.save(p);
        }catch(Exception e){
        	e.printStackTrace();
        }
        System.out.println("Phone saved successfully, Phone Details=" + p);
    }

}
