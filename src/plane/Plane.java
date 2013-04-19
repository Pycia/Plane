/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plane;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mjjaniec
 */
public class Plane {

  public static class Point {

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public int x, y;
    
    @Override
    public String toString(){
      return "Point ["+x+","+y+"]";
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || !(o instanceof Point)) {
        return false;
      }
      Point b = (Point) o;
      return x == b.x && y == b.y;
    }

    @Override
    public int hashCode() {
      return (x << 1) ^ y;
    }
  }

  public static class Field {

    public Field(int z, boolean visited) {
      this.z = z;
      this.visited = visited;
    }
    
    @Override
    public int hashCode(){
      return z;
    }
    
    @Override
    public boolean equals(Object o){
      if(o==null || !(o instanceof Field))return false;
      Field f = (Field)o;
      return f.z==z;
    }
    
    @Override 
    public String toString(){
      return "Field ["+z+","+visited+"]";
    }
    public int z;
    public boolean visited;
  }
  
  static final String defaultDatabase = "jdbc:mysql://localhost/plane?user=plane&password=plane";
  static final String query = "SELECT x,y,z FROM ftable";
  static final String x = "x";
  static final String y = "y";
  static final String z = "z";

  public static Connection connectWithDatabase(String[] args) throws SQLException, ClassNotFoundException {
     // This will load the MySQL driver, each DB has its own driver
    //Class.forName("com.mysql.jdbc.Driver");
    String databaseConnection;
    if (args.length == 0) {
      databaseConnection = defaultDatabase;
    } else {
      databaseConnection = args[0];
    }
    Connection con = DriverManager.getConnection(databaseConnection);
    return con;
  }
  
  public static Map<Point, Field> retriveData(Connection con) throws SQLException {
    
    Map<Point, Field> map = new HashMap<Point, Field>();
    Statement statement = con.createStatement();
    ResultSet result = statement.executeQuery(query);
    
    while (result.next()) {
      map.put(
              new Point(result.getInt(x), result.getInt(y)),
              new Field(result.getInt(z), false));
    }
    return map;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Connection con = null;
    try {
      con = connectWithDatabase(args);
      Map<Point, Field> map = retriveData(con);
      
      for(Entry<Point,Field> entry : map.entrySet())
        System.out.println(entry.getKey().toString()+"\t"+entry.getValue());


    } catch (Exception e) {
      Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, "no retry\n" + e.getMessage(), e);
        }
      }
    }
  }
}

