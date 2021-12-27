package com.dts.aoc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dts.aoc.dto.FriendDTO;
import com.dts.core.util.DateWrapper;
import com.dts.aoc.dto.AcadamicDTO;
import com.dts.core.util.DataObject;
import com.dts.core.util.LoggerManager;

public class FriendDAO extends DataObject
{
    Connection con;
    String dbname;
    String pattern;
	public FriendDAO()
	{
		con = getConnection();
	}
	public ArrayList<FriendDTO> getBatchmates(String loginname)
	{
		ArrayList<FriendDTO> al = new ArrayList<FriendDTO>();
		FriendDTO frienddto = null;
		try
		{
			Statement st = con.createStatement();
			String query = "select ld.loginname,ld.first_name,ld.last_name,lp.birthdate,aad.profession from " +
			"login_profile lp,login_details ld,alumni_academicdetails aad where ld.loginname=lp.loginid and ld.loginname=aad.loginname and aad.yearofpassedout<=(select yearofpassedout " +
			"from alumni_academicdetails where loginname='"+loginname+"') and aad.yearofjoining>=(select yearofjoining " +
			"from alumni_academicdetails where loginname='"+loginname+"')";
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next())
			{
				frienddto = new FriendDTO();
				frienddto.setLoginame(rs.getString(1));
				frienddto.setFirstname(rs.getString(2));
				frienddto.setLastname(rs.getString(3));
				frienddto.setBirdthdate1(rs.getDate(4));
				frienddto.setProfession(rs.getString(5));
				
				al.add(frienddto);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		}
		return al;
	}
	
	
	
	//getFriend details
	public FriendDTO getFriendDetails(String loginname)
	{
		FriendDTO frienddto = null;
		try
		{
			Statement st = con.createStatement();
			String query = "select ld.first_name,ld.last_name,lp.birthdate,lp.city,lp.state,lp.country,aad.profession from " +
			"login_profile lp,login_details ld,alumni_academicdetails aad where ld.loginname=lp.loginid and ld.loginname=aad.loginname and ld.loginname='"+loginname+"'";
			
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next())
			{
				frienddto = new FriendDTO();
				frienddto.setLoginame(loginname);
				frienddto.setFirstname(rs.getString(1));
				frienddto.setLastname(rs.getString(2));
				frienddto.setBirdthdate1(rs.getDate(3));
				frienddto.setCity(rs.getString(4));
				frienddto.setState(rs.getString(5));
				frienddto.setCountry(rs.getString(6));
				frienddto.setProfession(rs.getString(7));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LoggerManager.writeLogSevere(e);
		}
		return frienddto;
	}
	
	//Search for Friends
	
	public ArrayList<FriendDTO> search(AcadamicDTO acdto)
	{
		//String name = acdto.getLoginname();
		//int yearofpass = acdto.getYearofpass();
		//int yearofjoin = acdto.getYearofjoining();
		//String rollno = acdto.getRollno();
		
		String query = "select ld.loginname,ld.first_name,ld.last_name,lp.birthdate,aad.profession from " +
				"login_profile lp,login_details ld,alumni_academicdetails aad where ld.loginname=lp.loginid " +
				"and ld.loginname=aad.loginname";
		if(acdto.getLoginname()!="")
			query+=" and ld.first_name='"+acdto.getLoginname()+"' or  ld.last_name='"+acdto.getLoginname()+"'";
		if(acdto.getYearofpass()!=0)
			query+=" and aad.yearofpassedout="+acdto.getYearofpass();
		if(acdto.getYearofjoining()!=0)
			query+=" and aad.yearofjoining="+acdto.getYearofjoining();
		
        System.out.println(query); 
		ArrayList<FriendDTO> al = new ArrayList<FriendDTO>();
		FriendDTO frienddto = null;
		Statement st;
		try {
			st = con.createStatement();
		
		   ResultSet rs = st.executeQuery(query);
		   while(rs.next())
		   {
			    frienddto = new FriendDTO();
				frienddto.setLoginame(rs.getString(1));
				frienddto.setFirstname(rs.getString(2));
				frienddto.setLastname(rs.getString(3));
				frienddto.setBirdthdate1(rs.getDate(4));
				frienddto.setProfession(rs.getString(5));
				
				al.add(frienddto);
			  
		   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
		
	}
}
