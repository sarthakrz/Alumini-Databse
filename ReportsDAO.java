package com.dts.aoc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dts.aoc.dto.EventsDTO;
import com.dts.aoc.dto.FriendDTO;
import com.dts.aoc.dto.VacancyDTO;
import com.dts.core.util.DataObject;
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;

public class ReportsDAO extends DataObject
{
    Connection con;
	String pattern;
	String dbname;
	public ReportsDAO()
	{
		con = getConnection();
		dbname = getProperties().getProperty("dbname");
		if(dbname.equals("access"))
			pattern = "#"; 
	}
	
	//getAll the students list registered in between dates
	public ArrayList<FriendDTO> getStudents(String fromdate,String todate)
	{
		ArrayList<FriendDTO> al = new ArrayList<FriendDTO>();
		String fromnewdate = DateWrapper.parseDate(fromdate);
		String tonewdate = DateWrapper.parseDate(todate);
		FriendDTO frienddto = null;
		try
		{
			Statement st = con.createStatement();
			String query = "SELECT ld.loginname, ld.first_name, ld.last_name, lp.birthdate, lp.city, lp.state " +
					"FROM login_profile AS lp, login_details AS ld, alumni_academicdetails AS aad " +
					"WHERE (((ld.loginname)=[lp].[loginid] And (ld.loginname)=[aad].[loginname]) " +
					"AND ((ld.reg_date)>="+pattern+fromnewdate+pattern+" AND (ld.reg_date)<="+pattern+tonewdate+pattern+"))";
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			//System.out.println(rs.getFetchSize());
			while(rs.next())
			{
				frienddto = new FriendDTO();
				frienddto.setLoginame(rs.getString(1));
				frienddto.setFirstname(rs.getString(2));
				frienddto.setLastname(rs.getString(3));
				frienddto.setBirdthdate1(rs.getDate(4));
				frienddto.setCity(rs.getString(5));
				frienddto.setState(rs.getString(6));
				
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
	
//get Vacancy details
	
	public ArrayList<VacancyDTO> getVacancyReport(String fromdate, String todate, String criteria)
	{
		ArrayList<VacancyDTO> al = new ArrayList<VacancyDTO>();
		VacancyDTO vacdto = null;
		String fromnewdate = DateWrapper.parseDate(fromdate);
		String tonewdate = DateWrapper.parseDate(todate);
		try {
			Statement st = con.createStatement();
			String query = "select * from ALUMNI_VACANCIES where "+criteria+">="+pattern+fromnewdate+pattern+" and "+criteria+"<="+pattern+tonewdate+pattern;
			System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			while(rs.next())
			{
				vacdto = new VacancyDTO();
				vacdto.setVacancyid(rs.getInt(1));
				vacdto.setCompanyname(rs.getString(2));
				vacdto.setCompanyprofile(rs.getString(3));
				vacdto.setVacancyposition(rs.getString(4));
				vacdto.setJobdesc(rs.getString(5));
				vacdto.setCategory(rs.getString(6));
				vacdto.setLocation(rs.getString(7));
				vacdto.setDesiredprofile(rs.getString(8));
				vacdto.setDesiredexp(rs.getString(9));
				vacdto.setCreateddate1(rs.getDate(10));
				vacdto.setExpirydate1(rs.getDate(11));
				vacdto.setContactperson(rs.getString(12));
				vacdto.setDesignation(rs.getString(13));
				vacdto.setPhoneno(rs.getString(14));
				vacdto.setEmail(rs.getString(15));
				
				al.add(vacdto);
			}
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
		}
		return al;
	}
	
//get event details
	
	public ArrayList<EventsDTO> getEventsReport(String fromdate, String todate)
	{
		ArrayList<EventsDTO> al = new ArrayList<EventsDTO>();
		String fromnewdate = DateWrapper.parseDate(fromdate);
		String tonewdate = DateWrapper.parseDate(todate);
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS where eventdate>="+pattern+fromnewdate+pattern+" and eventdate<="+pattern+tonewdate+pattern);
			while(rs.next())
			{
				eventdto = new EventsDTO();
				eventdto.setEventid(rs.getInt(1));
				eventdto.setEventname(rs.getString(2));
				eventdto.setEventdate1(rs.getDate(3));
				eventdto.setEventtime(rs.getString(4));
				eventdto.setVenue(rs.getString(5));
				eventdto.setDescription(rs.getString(6));
				eventdto.setStatus(rs.getString(7));
				
				al.add(eventdto);
			}
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
		}
		return al;
	}
}
