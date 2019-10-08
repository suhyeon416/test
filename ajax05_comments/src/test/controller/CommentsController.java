package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import jdk.nashorn.internal.runtime.JSONListAdapter;
import test.dao.CommentsDAO;
import test.vo.CommentsVO;

@WebServlet("/comments")
public class CommentsController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cmd=req.getParameter("cmd");
		if(cmd!=null && cmd.equals("list")) {
			list(req,resp);
		}
		if(cmd!=null && cmd.equals("insert")) {
			insert(req,resp);
		}
		if(cmd!=null & cmd.equals("delete")) {
			delete(req,resp);
		}
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int mnum=Integer.parseInt(req.getParameter("mnum"));
		CommentsDAO dao=CommentsDAO.getCommentsDAO();
		ArrayList<CommentsVO> list=dao.list(mnum);
		
		JSONArray arr=new JSONArray();
		arr.put(list);
		
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw=resp.getWriter();
		pw.print(arr);	 
		//주소창에 http://localhost:8081/ajax03_comments/comments?cmd=list&mnum=1 라고 써서 화면에 출력되는지 확인
	}
    protected void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	int mnum=Integer.parseInt(req.getParameter("mnum"));
		String id=req.getParameter("id");
		String comments=req.getParameter("comments");
		CommentsVO vo=new CommentsVO(0, mnum, id, comments);
		CommentsDAO dao=CommentsDAO.getCommentsDAO();
		int n=dao.insert(vo);
		JSONObject json=new JSONObject();
		if(n>0) {
			json.put("code","success");
			json.put("id",id);
			json.put("comments",comments);
		}else {
			json.put("code","fail");
		}
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw=resp.getWriter();
		pw.print(json.toString());
	}
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	int num=Integer.parseInt(req.getParameter("num"));
    	CommentsDAO dao=CommentsDAO.getCommentsDAO();
    	int n=dao.delete(num);
    	JSONObject json=new JSONObject();
		if(n>0) {
			json.put("code","success");
		}else {
			json.put("code","fail");
		}
    	resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw=resp.getWriter();
		pw.print(json.toString());
    }
}
