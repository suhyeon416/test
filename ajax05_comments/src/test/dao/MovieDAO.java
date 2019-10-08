package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import test.vo.MovieVO;

public class MovieDAO {
	//싱글톤객체로 만들기
	private static MovieDAO movieDAO=new MovieDAO();
	public static MovieDAO getMovieDAO() {
		return movieDAO;
	}
	private MovieDAO() {}
	
	public MovieVO getInfo(int mnum) {
		String sql="select * from movie2 where mnum=?";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=JdbcUtil.getConn();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,mnum);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String title=rs.getString("title");
				String content=rs.getString("content");
				String director=rs.getString("director");
				MovieVO vo=new MovieVO(mnum, title, content, director);
				return vo;
			}
			return null;
		}catch(SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}finally {
			JdbcUtil.close(con, pstmt, rs);
		}
	}
}
