
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="user")
@SessionScoped
public class User {
    private String id;
    private String name;
 
    public String getId() { return id;}
    
    public void setId(String id) { this.id = id;
        if(this.id.equals("1"))
            name = "Cesar";
        else
            name = "Loachamin";
    }
     
    public String getName() { return name; }
     
    public void setName(String name) { this.name = name; }    
}