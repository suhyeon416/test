package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.JdbcUtil;
import test.vo.CommentsVO;

public class CommentsDAO {
	//싱글톤객체로 만들기
	private static CommentsDAO commentsDAO=new CommentsDAO();
	public static CommentsDAO getCommentsDAO() {
		return commentsDAO;
	}
	private CommentsDAO() {}
	
	public int insert(CommentsVO vo) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="insert into comments values(comments_seq.nextval,?,?,?)";
		try {
			con=JdbcUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,vo.getMnum());
			pstmt.setString(2,vo.getId());
			pstmt.setString(3,vo.getComments());
			return pstmt.executeUpdate();
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		}finally {
			JdbcUtil.close(con, pstmt, null);
		}
	}
	public ArrayList<CommentsVO> list(int mnum){
		String sql="select * from comments where mnum=?";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=JdbcUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,mnum);
			rs=pstmt.executeQuery();
			ArrayList<CommentsVO> commList=new ArrayList<CommentsVO>();
			while(rs.next()) {
				CommentsVO vo=new CommentsVO(rs.getInt("num"),rs.getInt("mnum"),rs.getString("id"),rs.getString("comments"));
				commList.add(vo);
			}
			return commList;
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}finally {
			JdbcUtil.close(con, pstmt, rs);
		}
	}
	public int delete(int num) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="delete from comments where num=?";
		try {
			con=JdbcUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,num);
			return pstmt.executeUpdate();
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		}finally {
			JdbcUtil.close(con, pstmt, null);
		}
	}
}
