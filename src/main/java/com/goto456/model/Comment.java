package com.goto456.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.goto456.model.base.BaseComment;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Comment extends BaseComment<Comment> {
	public static final Comment dao = new Comment();
	
	
	
	public static Page<Comment> findByBlogID(int pageNumber,int pageSize,String id){
		String sqlExp = "from comment where blogID = ? and status = 1 order by cdate desc,likeNum desc,shareNum desc";
		return dao.paginate(pageNumber, pageSize, "select * ",sqlExp,id);
	}
	
	public static Page<Record> paginate(int pageNum, int pageSize,int check) {
		String select = "select * ";
		String suffix = "from comment where comment.check = ? order by cdate desc";
		return Db.paginate(pageNum, pageSize, select, suffix,check);
	}
}
