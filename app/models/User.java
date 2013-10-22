package models;


import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class User extends Model {

  @Id
  public Long id;
  @Constraints.Required
  public String email;
  @Constraints.Required
  public String password;

  public User() {
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static User authenticate(String email,
                           String password) { 
// Should be something like
// return finder.where().eq("email", email)
//   .eq("password", password).findUnique();
      if ("nicolas".equals(email) && "nicolas".equals(password))
        return new User("nicolas", "nicolas");
      else
        return null;
  }

  public static Finder<Long, User> finder
          = new Finder<Long, User>(Long.class, User.class);

}