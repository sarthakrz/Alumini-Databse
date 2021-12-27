package com.dts.aoc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dts.aoc.dto.EventsDTO;
import com.dts.core.util.DataObject;
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;
public class EventsDAO extends DataObject{
	
	public EventsDTO eventdto;
	public Connection con;
	public EventsDAO()
	{
	   	con = getConnection();
	}
	//Registering event
	
	public boolean registerEvent(EventsDTO edto)
	{
		boolean flag=false;
		
		int eventid = 0;
		String eventname=edto.getEventname();
		String eventdate=DateWrapper.parseDate(edto.getEventdate());
		String eventvenue=edto.getVenue();
		String eventdesc=edto.getDescription();
		String eventtime=edto.getEventtime();
		String status=edto.getStatus();
		
		try 
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(eventid) from alumni_events");
			
			if(rs.next())
			    eventid = rs.getInt(1);	
			
			eventid++;
		
			PreparedStatement pst = con.prepareStatement("insert into ALUMNI_EVENTS values(?,?,?,?,?,?,?)");
			
			pst.setInt(1, eventid);
			pst.setString(2, eventname);
			pst.setString(3, eventdate);
			pst.setString(4, eventtime);
			pst.setString(5, eventvenue);
			pst.setString(6, eventdesc);
			pst.setString(7, status);
			
			int i=pst.executeUpdate();
			
			if(i!=0)
		        flag=true;
			else
				flag=false;
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
			flag=false;
		}
		return flag;
	}
    
//get event details
	
	public ArrayList<EventsDTO> getEventDetails()
	{
		ArrayList<EventsDTO> al = new ArrayList<EventsDTO>();
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS");
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
	
	//get Event details by id
	
	public EventsDTO getEventDetails(int eventid)
	{
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS where eventid="+eventid);
			if(rs.next())
			{
				eventdto = new EventsDTO();
				eventdto.setEventid(rs.getInt(1));
				eventdto.setEventname(rs.getString(2));
				eventdto.setEventdate1(rs.getDate(3));
				eventdto.setEventtime(rs.getString(4));
				eventdto.setVenue(rs.getString(5));
				eventdto.setDescription(rs.getString(6));
				eventdto.setStatus(rs.getString(7));
		    }
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
		}
		return eventdto;	
	}
	
	//update Event details
	
	public boolean updateEvent(EventsDTO edto)
	{
		boolean flag=false;
		
		int eventid = edto.getEventid();
		String eventname=edto.getEventname();
		String eventdate=DateWrapper.parseDate(edto.getEventdate());
		String eventvenue=edto.getVenue();
		String eventdesc=edto.getDescription();
		String eventtime=edto.getEventtime();
		String status=edto.getStatus();
		
		try 
		{
			PreparedStatement pst = con.prepareStatement("update ALUMNI_EVENTS set eventname=?,eventdate=?,eventtime=?,venue=?,description=?,status=? where eventid=?");
			
			pst.setString(1, eventname);
			pst.setString(2, eventdate);
			pst.setString(3, eventtime);
			pst.setString(4, eventvenue);
			pst.setString(5, eventdesc);
			pst.setString(6, status);
			pst.setInt(7, eventid);
			
			int i=pst.executeUpdate();
			
			if(i!=0)
		        flag=true;
			else
				flag=false;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			LoggerManager.writeLogSevere(sqlex);
			flag=false;
		}
		return flag;
	}
	
//get Event details for particular date
	
	public ArrayList<EventsDTO> getEventDetailsBydate(String pdate)
	{
		ArrayList<EventsDTO> al = new ArrayList<EventsDTO>();
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS where eventdate='"+pdate+"'");
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

//get Event details between dates

	public ArrayList<EventsDTO> getEventDetailsBydate(String fdate,String ldate)
	{
		ArrayList<EventsDTO> al = new ArrayList<EventsDTO>();
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS where eventdate in between "+fdate+"and "+ldate);
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
	
	//get Event details by status

	public ArrayList<EventsDTO> getEventDetails(String status)
	{
		ArrayList<EventsDTO> al = new ArrayList<EventsDTO>();
		EventsDTO eventdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_EVENTS where status='"+status+"'");
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
