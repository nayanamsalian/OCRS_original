package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.PolicePojo;
import com.ocrs.pojo.UserPojo;

@Component
public class OcrsDaoImpl implements OcrsDoa {

	Connection con=null;

	public void addUserPojo(UserPojo user) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into user_table(user_name,first_name,last_name,gender,email) values(?,?,?,?,?)");

			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getGender());
			pstmt.setString(5, user.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addUser_Credentials(String username, String password) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into users values(?,?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, "{noop}"+password);
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addAuthorities(String username, String authority) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into authorities values(?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, authority);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addUser(UserPojo user) {
		addUserPojo(user);
		addUser_Credentials(user.getUserName(), user.getPassword());
		addAuthorities(user.getUserName(), "ROLE_USER");
	}



	@Override
	public List<UserPojo> getLoginDetails() {
		con=MyConnection.getConnection();
		List<UserPojo> users=new ArrayList<UserPojo>();
		UserPojo user=null;
		if(con!=null)
			System.out.println("success");

		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from user_table");

			/*
			 * while (rs.next()) { user=new User(rs.getString(1), rs.getString(2),
			 * rs.getString(3),
			 * rs.getString(4),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(
			 * 9)); user.setUser_id(rs.getInt(5)); users.add(user); }
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}



	@Override
	public void postComplaint(ComplaintPojo complaintPojo) {

		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into complaint_table(user_name,police_station_code,complaint,attached_file,priority,status_) values(?,?,?,?,?,?)");


			pstmt.setString(1, complaintPojo.getUsername());
			pstmt.setString(2, complaintPojo.getP_code());
			pstmt.setString(3, complaintPojo.getComplaint());
			pstmt.setString(4, complaintPojo.getAttached_file_path());
			pstmt.setString(5, complaintPojo.getPriority());
			pstmt.setString(6, complaintPojo.getStatus());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<ComplaintPojo> getAllComplaints(String username) {
		String query="";
		con=MyConnection.getConnection();
		List<ComplaintPojo> complaints=new ArrayList<ComplaintPojo>();
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			if(!username.equals(" "))
				query="select * from complaint_table where user_name='"+username+"'";
			else
				query="select * from complaint_table";

			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				ComplaintPojo complaintPojo=new ComplaintPojo(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7));
				complaintPojo.setComplaint_id(rs.getInt(1));
				complaints.add(complaintPojo);
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return complaints;
	}

	@Override
	public ComplaintPojo getComplaintByComplaintId(int complaint_id) {
		con=MyConnection.getConnection();
		ComplaintPojo complaintPojo=null;
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from complaint_table where complaint_id='"+complaint_id+"'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				complaintPojo=new ComplaintPojo(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7));
				complaintPojo.setComplaint_id(rs.getInt(1));
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return complaintPojo;
	}

	@Override
	public void updateComplaint(ComplaintPojo complaintPojo) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("update complaint_table set user_name=?, police_station_code=? ,complaint=?, status_=? where complaint_id=?");

			pstmt.setString(1, complaintPojo.getUsername());
			pstmt.setString(2, complaintPojo.getP_code());
			pstmt.setString(3, complaintPojo.getComplaint());
			pstmt.setString(4, complaintPojo.getStatus());
			pstmt.setInt(5, complaintPojo.getComplaint_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<UserPojo> getUsersByPattern(String pattern) {
		con=MyConnection.getConnection();
		List<UserPojo> users=new ArrayList<UserPojo>();
		ComplaintPojo complaintPojo=null;
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from user_table where user_name LIKE '%"+pattern+"%' or first_name LIKE '%"+pattern+"%' or last_name LIKE '%"+pattern+"%'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				UserPojo userPojo=new UserPojo();
				userPojo.setUserName(rs.getString(2));
				userPojo.setFirstName(rs.getString(3));
				userPojo.setLastName(rs.getString(4));
				userPojo.setGender(rs.getString(5));
				userPojo.setEmail(rs.getString(6));
				users.add(userPojo);
			}

		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return users;
	}

	@Override
	public void deleteUser(String username) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("delete from user_table where user_name=?");
			pstmt.setString(1, username);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement("delete from authorities where username=?");
			pstmt.setString(1, username);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement("delete from users where username=?");
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<UserPojo> getAllUsers() {
		con=MyConnection.getConnection();
		List<UserPojo> users=new ArrayList<UserPojo>();
		ComplaintPojo complaintPojo=null;
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from user_table";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				UserPojo userPojo=new UserPojo();
				userPojo.setUserName(rs.getString(2));
				userPojo.setFirstName(rs.getString(3));
				userPojo.setLastName(rs.getString(4));
				userPojo.setGender(rs.getString(5));
				userPojo.setEmail(rs.getString(6));
				users.add(userPojo);
			}

		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return users;

	}

	@Override
	public void addPolice(String username,String p_id) {
		addAuthorities(username, "ROLE_POLICE");
		con=MyConnection.getConnection();

		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into police_station_table values(?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, p_id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List getAllPolice() {
		con=MyConnection.getConnection();
		List<PolicePojo> polices=new ArrayList();
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from police_station_table";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				polices.add(new PolicePojo(rs.getString(1),rs.getString(2)));
			}
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return polices;


	}

	public void deletePolice(String username) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("delete from police_station_table where user_name=?");
			pstmt.setString(1, username);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement("delete from authorities where username=? and authoruty=?" );
			pstmt.setString(1, username);
			pstmt.setString(2, "ROLE_POLICE");
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateComplaintStatus(int c_id, String status) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("update complaint_table set status_=? where complaint_id=?");

			pstmt.setString(1, status);
			pstmt.setInt(2, c_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public List<ComplaintPojo> getComplaintsByStationId(String p_id) {
		con=MyConnection.getConnection();
		List<ComplaintPojo> complaints=new ArrayList<ComplaintPojo>();
		ComplaintPojo complaintPojo=null;
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from complaint_table where police_station_code='"+p_id+"'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				complaintPojo=new ComplaintPojo(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7));
				complaintPojo.setComplaint_id(rs.getInt(1));
				complaints.add(complaintPojo);
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return complaints;

	}

	@Override
	public UserPojo getUserByUserName(String username) {
		con=MyConnection.getConnection();
		UserPojo userPojo=new UserPojo();
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from user_table where user_name= '"+username+"'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				userPojo.setUserName(rs.getString(2));
				userPojo.setFirstName(rs.getString(3));
				userPojo.setLastName(rs.getString(4));
				userPojo.setGender(rs.getString(5));
				userPojo.setEmail(rs.getString(6));
			}

		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}		
		return userPojo;

	}

	@Override
	public void updateUser(String username, int id, String element) {
		String query="";
		if(id==1)
			query="update user_table set first_name=? where user_name=?";
		else if(id==2)
			query="update user_table set last_name=? where user_name=?";
		else if(id==3)
		    query="update user_table set email=? where user_name=?";
		else if(id==4)
		     query="update user_table set gender=? where user_name=?";
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, element);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void addComment(int complaint_id, String firstName, String comment) {
		List<CommentPojo> comments=new ArrayList<CommentPojo>();
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("insert into comment_table(complaint_id,first_name,comment_) values(?,?,?)");

			pstmt.setInt(1, complaint_id);
			pstmt.setString(2, firstName);
			pstmt.setString(3, comment);
			pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<CommentPojo> getAllCommentsByCompliantId(int complaint_id) {
		con=MyConnection.getConnection();
		List<CommentPojo> comments=new ArrayList<CommentPojo>();
		ComplaintPojo complaintPojo=null;
		if(con!=null)
			System.out.println("success");
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query="select * from comment_table where complaint_id='"+complaint_id+"'";
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				CommentPojo commentPojo=new CommentPojo(rs.getString(2), rs.getString(3), rs.getString(4));
				commentPojo.setComment_id(rs.getInt(1));
				comments.add(commentPojo);
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return comments;

	}

	@Override
	public void deleteCommentById(int comment_id) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("delete from comment_table where comment_id=?");
			pstmt.setInt(1, comment_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void deleteComplaintByID(int complaint_id) {
		con=MyConnection.getConnection();
		if(con!=null)
			System.out.println("success");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("delete from comment_table where complaint_id=?");
			pstmt.setInt(1, complaint_id);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from complaint_table where complaint_id=?");
			pstmt.setInt(1, complaint_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	


}
