package utils;

import static play.mvc.Results.*;
import play.api.libs.iteratee.*;
import play.api.libs.iteratee.Done;
import play.api.mvc.*;
import play.libs.Scala;
import scala.Option;
import scala.Tuple2;
import scala.collection.Seq;
import scala.runtime.AbstractFunction1;
import sun.misc.BASE64Decoder;

import java.util.ArrayList;
import java.util.List;

public class BasicAuthenticationFilter implements EssentialFilter { 

 public BasicAuthenticationFilter() { 
  // Left empty
 }

 public EssentialAction apply(final EssentialAction next) { 

  return new JavaEssentialAction() { 

   @Override
   public EssentialAction apply() { 
     return next.apply();
   }      

   @Override
   public Iteratee<byte[], SimpleResult> apply(RequestHeader rh) { 
    Option<String> authorization=rh.headers().get("Authorization");  
    if (!authorization.isEmpty()) {  
     String auth = authorization.get();
     BASE64Decoder decoder = new BASE64Decoder();
     String passanduser = auth.split(" ")[1];
     try {
      String[] pass = new String(decoder.decodeBuffer(passanduser))
                                                        .split(":"); 
      String username = pass[0];
      String password = pass[1];
      if ("nicolas".equals(username)&&"nicolas".equals(password)){  
       return next.apply(rh);  
      }
     } catch(Exception e) {
      // Nothing
     }
    }

    List<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
    Tuple2<String, String> t = 
                         new Tuple2<String, String>("WWW-Authenticate",
                                       "Basic realm=\"warehouse app\"");
    list.add(t);
    Seq<Tuple2<String, String>> seq = Scala.toSeq(list); 
    return Done.apply(
                  unauthorized("Forbidden access to the warehouse app").
                      getWrappedSimpleResult().withHeaders(seq), null);  
  }
 };
}

public abstract class JavaEssentialAction extends 
   AbstractFunction1<RequestHeader, Iteratee<byte[], SimpleResult>> 
                                      implements EssentialAction {} 
}