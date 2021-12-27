package com.dts.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.dts.core.util.DataObject;
import com.dts.core.util.LoggerManager;


/**
 *
 * @author
 */
public class DbCon extends DataObject implements DbInterface{
    
    Connection con;
    public DbCon() {
    }

    public Connection getConnection() 
    {
        try
        {
            if(con==null)
            {
                try 
                {
                   Properties p = getProperties();
                   Class.forName(p.getProperty("driver"));
                   System.out.println("Driver loaded");
                   con = DriverManager.getConnection(p.getProperty("url"),p.getProperty("duser"),p.getProperty("dpass"));
                   System.out.println("Connection established");
                  
                  /* String JNDI=p.getProperty("JNDI_NAME");
                   InitialContext ic;
					try 
					{
						ic = new InitialContext();
						DataSource ds=(DataSource)ic.lookup(JNDI);
	                    con=ds.getConnection();
					} catch (NamingException ne) {
						LoggerManager.writeLogWarning(ne);
					}*/
                }
                catch (ClassNotFoundException cnf)
                {
                	LoggerManager.writeLogWarning(cnf);
                }
            }
        } 
        catch (SQLException sqlex) 
        {
        	
        	sqlex.printStackTrace();
        	LoggerManager.writeLogSevere(sqlex);
        }  
        return con;
    }
    public void close()
    {
        if(con!=null)
        {
            try {
                con.close();
            } catch (SQLException sqlex) {
            	LoggerManager.writeLogSevere(sqlex);
            }
        }
    }
}
