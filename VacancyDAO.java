package com.dts.aoc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dts.aoc.dto.EventsDTO;
import com.dts.aoc.dto.VacancyDTO;
import com.dts.core.util.DataObject;
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;
public class VacancyDAO extends DataObject{
	
	public EventsDTO eventdto;
	public Connection con;
	public VacancyDAO()
	{
	   	con = getConnection();
	}
	//Registering Vacancy
	
	public boolean registerVacancy(VacancyDTO vdto)
	{
		boolean flag=false;
		
		int vacancyid = 0;
		String companyname = vdto.getCompanyname();
		String companyprofile = vdto.getCompanyprofile();
		String vacancyposition = vdto.getVacancyposition();
		String jobdesc = vdto.getJobdesc();
		String category = vdto.getCategory();
		String location = vdto.getLocation();
		String desiredprofile = vdto.getDesiredprofile();
		String desiredexperience = vdto.getDesiredexp();
		String createddate = DateWrapper.parseDate(vdto.getCreateddate());
	    String expirydate = DateWrapper.parseDate(vdto.getExpirydate());
		String contactperson = vdto.getContactperson();
		String designation = vdto.getDesignation();
		String phoneno = vdto.getPhoneno();
		String email = vdto.getEmail();
		try 
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(vacancyid) from alumni_vacancies");
			
			if(rs.next())
				vacancyid = rs.getInt(1);	
			
			vacancyid++;
			
			PreparedStatement pst = con.prepareStatement("insert into ALUMNI_VACANCIES values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pst.setInt(1, vacancyid);
			pst.setString(2, companyname);
			pst.setString(3, companyprofile);
			pst.setString(4, vacancyposition);
			pst.setString(5, jobdesc);
			pst.setString(6, category);
			pst.setString(7, location);
			pst.setString(8, desiredprofile);
			pst.setString(9, desiredexperience);
			pst.setString(10, createddate);
			pst.setString(11, expirydate);
			pst.setString(12, contactperson);
			pst.setString(13, designation);
			pst.setString(14, phoneno);
			pst.setString(15, email);
			
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
    
//get Vacancy details
	
	public ArrayList<VacancyDTO> getVacancyDetails()
	{
		ArrayList<VacancyDTO> al = new ArrayList<VacancyDTO>();
		VacancyDTO vacdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_VACANCIES");
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
	
	//get Vacancy details by id
	
	public VacancyDTO getVacancyDetails(int vacancyid)
	{
		VacancyDTO vacdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_VACANCIES where vacancyid="+vacancyid);
			if(rs.next())
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
		    }
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
		}
		return vacdto;	
	}
	
//update Vacancy details
	
	public boolean updateVacancy(VacancyDTO vdto)
	{
		boolean flag=false;
		
		int vacancyid = vdto.getVacancyid();
		String companyname = vdto.getCompanyname();
		String companyprofile = vdto.getCompanyprofile();
		String vacancyposition = vdto.getVacancyposition();
		String jobdesc = vdto.getJobdesc();
		String category = vdto.getCategory();
		String location = vdto.getLocation();
		String desiredprofile = vdto.getDesiredprofile();
		String desiredexperience = vdto.getDesiredexp();
		String createddate = vdto.getCreateddate();
	    String expirydate = vdto.getExpirydate();
		String contactperson = vdto.getContactperson();
		String designation = vdto.getDesignation();
		String phoneno = vdto.getPhoneno();
		String email = vdto.getEmail();
		try 
		{
			PreparedStatement pst = con.prepareStatement("update ALUMNI_VACANCIES set companyname=?,companyprofile=?,vacancyposition=?,jobdescription=?,category=?,location=?,desiredprofile=?,desiredexp=?,createddate=?,expirydate=?,contactperson=?,designation=?,phoneno=?,email=? where vacancyid=?");
			
			pst.setInt(15, vacancyid);
			pst.setString(1, companyname);
			pst.setString(2, companyprofile);
			pst.setString(3, vacancyposition);
			pst.setString(4, jobdesc);
			pst.setString(5, category);
			pst.setString(6, location);
			pst.setString(7, desiredprofile);
			pst.setString(8, desiredexperience);
			pst.setString(9, createddate);
			pst.setString(10, expirydate);
			pst.setString(11, contactperson);
			pst.setString(12, designation);
			pst.setString(13, phoneno);
			pst.setString(14, email);
			
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
	
//get vacancy details for particular date
	
	public ArrayList<VacancyDTO> getVacancyDetailsBydate(String pdate)
	{
		ArrayList<VacancyDTO> al = new ArrayList<VacancyDTO>();
		VacancyDTO vacdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_VACANCIES where createddate='"+pdate+"'");
			if(rs.next())
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
		    }
		} catch (SQLException sqlex) {
			LoggerManager.writeLogSevere(sqlex);
		}
		return al;	
	}
	
	//get Vacancy details between dates

	public ArrayList<VacancyDTO> getEventDetailsBydate(String fdate,String ldate)
	{
		ArrayList<VacancyDTO> al = new ArrayList<VacancyDTO>();
		VacancyDTO vacdto = null;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from ALUMNI_VACANCIES where createddate in between "+fdate+"and "+ldate);
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
}
