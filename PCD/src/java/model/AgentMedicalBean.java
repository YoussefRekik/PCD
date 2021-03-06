package model;
import static com.mchange.v2.c3p0.impl.C3P0Defaults.password;
import com.sun.istack.internal.NotNull;
import static java.rmi.server.LogStream.log;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.apache.catalina.User;
import static org.apache.tomcat.jni.User.username;
import static org.eclipse.persistence.expressions.ExpressionOperator.NotNull;
import org.hibernate.Query;
import org.hibernate.Session;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Youssef
 */
@ManagedBean(name = "ABean", eager = true)
@RequestScoped

public class AgentMedicalBean {
    private List<AgentMedical> AgentMedicals;
     private Integer idagent;
     private String passwordagent;
     private String nomagent;
     private String prenomagent;
     private String emailagent;
     private String nomvilleagent;
     private String nompadresseagent;
     private Integer codepostalagent;
     private String telagent;
     private Integer typeagent;
     private double lon;
     private double lat;
     private String type;
     private String offre;
     private String urlphoto;
     
     private Set<Commentaire> commentaires = new HashSet<Commentaire>(0);
     private Set<Article> articles = new HashSet<Article>(0);
     
     
    public String saveagent(){
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
		//AgentMedicalDAO userDao = new AgentMedicalDAO();
                
                Set<Commentaire> commentaires = new HashSet<Commentaire>(0);
                Set<Article> articles = new HashSet<Article>(0);
                
                Set<MembreDonnerNote> membreDonnerNotes = new HashSet<MembreDonnerNote>(0);
		AgentMedical agent = new AgentMedical(passwordagent,nomagent,prenomagent,emailagent,nomvilleagent,nompadresseagent,codepostalagent,telagent,typeagent,lon,lat,offre,urlphoto,commentaires,articles);
		
                session.save(agent);
                
                Membre membre=new Membre(nomagent, prenomagent, nomagent, emailagent, passwordagent, null, membreDonnerNotes, commentaires);
                session.save(membre);
                
                session.getTransaction().commit();
		session.close();
		
		return "output";
                            }
    
    
 
    
    
    public String modifagent(){
            Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                FacesContext fc = FacesContext.getCurrentInstance();
                String email = (String)fc.getApplication().createValueBinding("#{user.emailagent}").getValue(fc);
                
                String hql = "UPDATE AgentMedical set passwordagent = :a ,lon= :e ,lat= :g, offre= :f, urlphoto= :h "  + "WHERE emailagent = :b";
		
                
                
                Query query = session.createQuery(hql);
                

                query.setParameter("a", passwordagent);
                
                query.setParameter("e",lon );
                query.setParameter("g",lat);
                query.setParameter("f",offre);
                query.setParameter("h",urlphoto);


                query.setParameter("b", email);
                query.executeUpdate();
               
		
                
                session.getTransaction().commit();
		session.close();
		return "login";
    }
    public String propagent(){
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
		//AgentMedicalDAO userDao = new AgentMedicalDAO();
                
                Set<Commentaire> commentaires = new HashSet<Commentaire>(0);
                Set<Article> articles = new HashSet<Article>(0);
                
                AgentMedical agent = new AgentMedical(null,nomagent,prenomagent,null,nomvilleagent,nompadresseagent,codepostalagent,telagent,typeagent,lon,lat,null,null,commentaires,articles);
		
                session.save(agent);
                
                session.getTransaction().commit();
		session.close();
		
		return "propsucces";
                            }
    
     
    public String fagent(){
        Session session = HibernateUtil.getSessionFactory().openSession();
       session.beginTransaction();
       AgentMedicals = session.createQuery("from AgentMedical where nomvilleagent='" + nomvilleagent + "' and typeagent='" + typeagent+"'").list();
       
       session.getTransaction().commit();
       session.close();
        return "resultatrecherche" ; 
    }
    
    @PostConstruct
    public void afficheoffres(){
        Session session = HibernateUtil.getSessionFactory().openSession();
       session.beginTransaction();
       
       AgentMedicals = session.createQuery("from AgentMedical").list();
       /*List<String> AOffres;
       for(int k=0  ;k<AgentMedicals.size();k++)
       {
           (AgentMedicals.get(k)).setOffreAgent(AOffres.get(k));
       };*/
       session.getTransaction().commit();
       session.close();
        //return "afficheoffres" ; 
    }
    
    public String verifyUser(){
		AgentMedicalDAO agentDAO = new AgentMedicalDAO();
		AgentMedical agent = new AgentMedical(passwordagent,nomagent,prenomagent,emailagent,nomvilleagent,nompadresseagent,codepostalagent,telagent,typeagent,lon,lat,offre,urlphoto,commentaires,articles);
		Integer response = agentDAO.verify(emailagent,passwordagent);
		if (response==2) {
                                    FacesContext context = FacesContext.getCurrentInstance();
                                    context.getExternalContext().getSessionMap().put("user", agent);
                                    return "adminloggedin";
                                  }
                        else if (response==1) {
                           // User user = userService.find(username, password);
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.getExternalContext().getSessionMap().put("user", agent);
                            return "adminloggedin";
                                
                        }
                else return "login";
                }
    
    
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
        }
    
    public String delete(int telagent){
        Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                
                session.createQuery("DELETE from AgentMedical as agent where agent.telagent = '"+telagent+"'");
                
                session.getTransaction().commit();
		session.close();
        return "affisupp";
        }
    
    
    
     public Integer getIdagent() {
        return idagent;
    }

    public void setIdagent(Integer idagent) {
        this.idagent = idagent;
    }

    public List<AgentMedical> getAgentMedicals() {
        return AgentMedicals;
    }

    public void setAgentMedicals(List<AgentMedical> AgentMedicals) {
        this.AgentMedicals = AgentMedicals;
    }
    
    

    

    

    

    public String getPasswordagent() {
        return passwordagent;
    }

    public void setPasswordagent(String passwordagent) {
        this.passwordagent = passwordagent;
    }

    public String getNomagent() {
        return nomagent;
    }

    public void setNomagent(String nomagent) {
        this.nomagent = nomagent;
    }

    public String getPrenomagent() {
        return prenomagent;
    }

    public void setPrenomagent(String prenomagent) {
        this.prenomagent = prenomagent;
    }

    public String getEmailagent() {
        return emailagent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmailagent(String emailagent) {
        this.emailagent = emailagent;
    }

    public String getNomvilleagent() {
        return nomvilleagent;
    }

    public void setNomvilleagent(String nomvilleagent) {
        this.nomvilleagent = nomvilleagent;
    }

    public String getNompadresseagent() {
        return nompadresseagent;
    }

    public void setNompadresseagent(String nompadresseagent) {
        this.nompadresseagent = nompadresseagent;
    }

    public Integer getCodepostalagent() {
        return codepostalagent;
    }

    public void setCodepostalagent(Integer codepostalagent) {
        this.codepostalagent = codepostalagent;
    }

    public String getTelagent() {
        return telagent;
    }

    public void setTelagent(String telagent) {
        this.telagent = telagent;
    }

    public Integer getTypeagent() {
        return typeagent;
    }

    public void setTypeagent(Integer typeagent) {
        this.typeagent = typeagent;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

   

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public String getUrlphoto() {
        return urlphoto;
    }

    public void setUrlphoto(String urlphoto) {
        this.urlphoto = urlphoto;
    }

    

     

    public AgentMedicalBean() {
    }
    
    }
