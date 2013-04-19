/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plane;

import java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author mjjaniec
 */
public class PlaneTest {
//non - senc   e comment
  @Test
  public void testRetriveData() throws Exception{
    Connection connectionMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);
    ResultSet resultMock = mock(ResultSet.class);
    
    when(connectionMock.createStatement()).thenReturn(statementMock); 
    when(statementMock.executeQuery(Plane.query)).thenReturn(resultMock);
    
    when(resultMock.next()).thenReturn(true,true,false);
    
    when(resultMock.getInt(Plane.x)).thenReturn(0,1);
    when(resultMock.getInt(Plane.y)).thenReturn(0,0);
    when(resultMock.getInt(Plane.z)).thenReturn(3,3);
 
    Map<Plane.Point,Plane.Field> expected = new HashMap<Plane.Point,Plane.Field>();
    expected.put(new Plane.Point(0,0), new Plane.Field(3,false));
    expected.put(new Plane.Point(1,0), new Plane.Field(3,false));
    
    Map<Plane.Point,Plane.Field> retrived = Plane.retriveData(connectionMock);
    assertEquals(retrived,expected);
  }
}
