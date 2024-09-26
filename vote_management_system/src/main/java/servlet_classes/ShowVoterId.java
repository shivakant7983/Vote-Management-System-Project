package servlet_classes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.classes.Users;

@WebServlet("/VoterId")
public class ShowVoterId extends HttpServlet{

	EntityManagerFactory emf;
    
    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory("vote_management_system");
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 PrintWriter pw = resp.getWriter();
		
		 resp.setContentType("text/html;character=UTF-8");
		 
		 int userData = Integer.parseInt(req.getParameter("userdata"));
		
		 EntityManager em = emf.createEntityManager();
         EntityTransaction et = em.getTransaction();
         
         Users user = em.find(Users.class, userData);
         
         resp.setContentType("text/html; charset=UTF-8");
         resp.setCharacterEncoding("UTF-8");

         try {
             // Fetch user based on email (assuming email is unique)
             Query query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id");
             query.setParameter("id", userData);
             Users user1 = (Users) query.getSingleResult();

             // Validate user credentials
             if (user != null) {
                 // Set attributes for forwarding to VoterId.jsp
                 req.setAttribute("name", user.getName());
                 req.setAttribute("father", user.getFatherName());
                 req.setAttribute("gender", user.getGender());
                 req.setAttribute("date", user.getDate());
                 
               //for setting button from voterId.jsp where we will remove update voter id anchor tag
                 req.setAttribute("isFetchingData", true);

                 // Forward to VoterId.jsp
                 RequestDispatcher rd = req.getRequestDispatcher("VoterId.jsp");
                 rd.forward(req, resp);
             } else {
              pw.println("your user is null so it will not show your data");
             }
         } catch (NoResultException e) {
             // Handle user not found
             pw.println("ERROR : "+e.getMessage());
         } finally {
             em.close();
         }
         
	}
	
	@Override
    public void destroy() {
        super.destroy();
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

