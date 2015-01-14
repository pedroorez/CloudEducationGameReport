package com.ESIa.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SFactory {
    
        public static SessionFactory sessionFactory;
    
        // Session Factory Creator
        public SFactory() throws Exception {
        
        if(sessionFactory == null){
        
            // A SessionFactory is set up once for an application
            sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        }
        
        if(sessionFactory != null){
        
            // faz nada
            
        }

        
    }
  
    
}
