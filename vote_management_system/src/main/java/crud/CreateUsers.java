package crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entity.classes.Admin;
import entity.classes.Users;

public class CreateUsers {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("vote_management_system");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Users user1 = new Users("Deepak", "deepak12@gmail.com", "deepak1234","Rajsthan", 19, "male", "Banshi", "Vimal", "20-03-2005");
	
		System.out.println(emf);
		et.begin();
			em.persist(user1);
		et.commit();
	}

}
