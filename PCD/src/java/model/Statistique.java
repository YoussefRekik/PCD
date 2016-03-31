package model;
// Generated 30 mars 2016 10:59:16 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Statistique generated by hbm2java
 */
@Entity
@Table(name="statistique"
    ,catalog="pcd"
)
public class Statistique  implements java.io.Serializable {


     private Integer idstat;
     private Set<Administrateur> administrateurs = new HashSet<Administrateur>(0);

    public Statistique() {
    }

    public Statistique(Set<Administrateur> administrateurs) {
       this.administrateurs = administrateurs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idstat", unique=true, nullable=false)
    public Integer getIdstat() {
        return this.idstat;
    }
    
    public void setIdstat(Integer idstat) {
        this.idstat = idstat;
    }

@ManyToMany(fetch=FetchType.LAZY, mappedBy="statistiques")
    public Set<Administrateur> getAdministrateurs() {
        return this.administrateurs;
    }
    
    public void setAdministrateurs(Set<Administrateur> administrateurs) {
        this.administrateurs = administrateurs;
    }




}

