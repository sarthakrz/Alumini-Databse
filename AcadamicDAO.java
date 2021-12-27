package com.dts.aoc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dts.aoc.dto.AcadamicDTO;
import com.dts.core.util.DataObject;
import com.dts.core.util.LoggerManager;

public class AcadamicDAO extends DataObject{
  
	Connection con;
	public AcadamicDAO()
	{
		con=getConnection();
	}
	
	//Registering acadamic details
	public boolean registerAcadamicDetails(AcadamicDTO adto)
	{
		Statement st;
		boolean flag = false;
		
		try {
              String loginname=adto.getLoginname();
              int yearofpass=adto.getYearofpass();
              int yearofjoin=adto.getYearofjoining();
              String profession=adto.getProfession();
              String rollno=adto.getRollno();
              
              PreparedStatement pst = con.prepareStatement("insert into ALUMNI_ACADEMICDETAILS values(?,?,?,?,?)");
              
              pst.setString(1, loginname);
              pst.setInt(2, yearofpass);
              pst.setInt(3, yearofjoin);
              pst.setString(4,profession);
              pst.setString(5, rollno);
              
              int i=pst.executeUpdate();
              
              if(i!=0)
              {
            	      st=con.createStatement();
                      st.executeUpdate("Update login_details set firstlogin=1 where loginname='"+loginname+"'");
                      flag=true; 
           	  
              }
              else
            	  flag=false;
              
              
		} catch (SQLException e) {
			flag=false;
			LoggerManager.writeLogSevere(e);
		}
		return flag;
	}
	
	//checking acadamic details
	public boolean checkAcadamicDetails(String loginname)
	{
		Statement st;
		boolean flag = false;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_ACADAMICDETAILS where loginname='"+loginname+"'");
			if(rs.next())
				flag = true;
				
		} catch (SQLException e) {
			LoggerManager.writeLogSevere(e);
		}
		return flag;
	}
	
	//get Acadamic details
	
	public AcadamicDTO getAcademicDetails(String loginname)
	{
		AcadamicDTO acdto=null;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select yearofpassedout,yearofjoining,profession,rollno from ALUMNI_ACADEMICDETAILS where loginname='"+loginname+"'");
			if(rs.next())
			{
				acdto = new AcadamicDTO();
			    acdto.setYearofpass(rs.getInt(1));
			    acdto.setYearofjoining(rs.getInt(2));
			    acdto.setProfession(rs.getString(3));
			    acdto.setRollno(rs.getString(4));
			    acdto.setLoginname(loginname);
			}
		}
		catch(SQLException sex)
		{
			LoggerManager.writeLogSevere(sex);
		}
		return acdto;
	}
	
	//Updating acadamic details
	public boolean updateAcadamicDetails(AcadamicDTO adto)
	{
		Statement st;
		boolean flag = false;
		
		try {
              String loginname=adto.getLoginname();
              int yearofpass=adto.getYearofpass();
              int yearofjoin=adto.getYearofjoining();
              String profession=adto.getProfession();
              String rollno=adto.getRollno();
              
              PreparedStatement pst = con.prepareStatement("update ALUMNI_ACADEMICDETAILS set yearofpassedout=?,yearofjoining=?,profession=?,rollno=? where loginname=?");
              
              pst.setInt(1, yearofpass);
              pst.setInt(2, yearofjoin);
              pst.setString(3,profession);
              pst.setString(4, rollno);
              pst.setString(5, loginname);
              
              int i=pst.executeUpdate();
              System.out.println("-------"+loginname);
              if(i!=0)
              {
            	  flag = true;
              }
		}
		catch(Exception e)
		{
			LoggerManager.writeLogSevere(e);
		}
		return flag;
	}
}
