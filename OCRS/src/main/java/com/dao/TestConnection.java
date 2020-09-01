package com.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ocrs.pojo.PolicePojo;
import com.ocrs.pojo.UserPojo;

import java.sql.*;

public class TestConnection {


static OcrsDaoImpl daoImpl=new OcrsDaoImpl();

public static void main(String[] args) {
		/*
		 * UserPojo userPojo=new UserPojo("sumana", "test123", "sumana", "salian",
		 * "female", "ss@gmail.com"); daoImpl.addUser(userPojo);
		 */
	
	List<PolicePojo> users=daoImpl.getAllPolice();
	for (PolicePojo userPojo : users) {
		System.out.println(userPojo.getUserName()+" -"+userPojo.getP_id());
	}
}

}
