package com.goto456.model;

import java.util.ArrayList;
import java.util.List;

import com.goto456.model.base.BaseBlog;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {
	public static final Blog dao = new Blog();
	
	
	public static Blog findById(String id,String columns){
		return dao.findByIdLoadColumns(id, columns);
	}
	
	
	public static Blog openRead(String id){
		return dao.findByIdLoadColumns(id, "id,commentNum,readNum,heartNum,shareNum");
	}

	/**
	 * 根据签名查找博客
	 * @param signature
	 * @return
	 */
	public static Blog findBySignature(String signature) {
		return dao.findFirst("select id from blog where signature = ? limit 1",signature);
	}
	
	
	public static List<Blog> hotRank(int size){
		String sql = "select id,title,url,readNum,commentNum,heartNum from blog order by readNum DESC,commentNum DESC,heartNum DESC LIMIT ?";
		return dao.find(sql,Math.max(5, size));
	}
	
	public static Page<Record> paginate4Back(int pageNum,int pageSize){
		String select = "select * ";
		// 默认根据时间排序
		String sqlExceptSelect = "from blog_back_display order by publishTime desc";
		return Db.paginate(pageNum, pageSize,select, sqlExceptSelect);
	}

	
	public static final String SHOW_SELECT = "select categoryName,id,title,author,summary,commentNum,readNum,url,publishTime,type,status,coverURL,heartNum,tags,typeName,typeID,categoryID";

	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Page<Record> paginate(int pageNum,int pageSize){
		// 默认根据时间排序
		String sqlExceptSelect = "from blog_display order by publishTime desc";
		Page<Record> page = Db.paginate(pageNum, pageSize,SHOW_SELECT, sqlExceptSelect);
		return doPage(page);
	}
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Page<Record> paginateByCategory(int pageNum,int pageSize,String categoryID){
		// 默认根据时间排序
		String sqlExceptSelect = "from blog_display where categoryID = ? order by publishTime desc";
		Page<Record> page = Db.paginate(pageNum, pageSize,SHOW_SELECT, sqlExceptSelect,categoryID);
		return doPage(page);
	}
	
	public static Blog findNextBlog(Blog bean){
		String sql = "select title,url from blog where publishTime > ? limit 1";
		Blog nextBlog = Blog.dao.findFirst(sql,bean.getPublishTime());
		if(nextBlog == null){
			sql = "select title,url from blog order by publishTime limit 1";
			nextBlog = Blog.dao.findFirst(sql);
		}
		return nextBlog;
	}
	
	public static Blog findPreBlog(Blog bean){
    	String sql = "select title,url from blog where publishTime < ? limit 1";
    	Blog preBlog = Blog.dao.findFirst(sql,bean.getPublishTime());
		if(preBlog == null){
			sql = "select title,url from blog order by publishTime desc limit 1";
			preBlog = Blog.dao.findFirst(sql);
		}
		return preBlog;
    }
	
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Page<Record> paginateByTag(int pageNum,int pageSize,String tagID){
		// 默认根据时间排序
		String sqlExceptSelect = "from blog_display_by_tag where tagID = ? order by publishTime desc";
		Page<Record> page = Db.paginate(pageNum, pageSize,SHOW_SELECT, sqlExceptSelect,tagID);
		return doPage(page);
	}
	
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Page<Record> paginateByQuery(int pageNum,int pageSize,String keyword){
		// 默认根据时间排序
		String sqlExceptSelect = "from blog_display where title like ? or content like ? order by publishTime desc";
		keyword = "%" + keyword + "%";
		Page<Record> page = Db.paginate(pageNum, pageSize,SHOW_SELECT, sqlExceptSelect,keyword,keyword);
		return doPage(page);
	}
	
	private static Page<Record> doPage(Page<Record> page){
		if(page.getList().isEmpty()){
			return page;
		}
		List<Record> records = page.getList();
		for(Record record : records){
			String tags = record.get("tags");
			if(tags == null){
				record.set("tags", new ArrayList<String>());
			} else {
				String[] arr = tags.split(",");
				List<String> ts = new ArrayList<String>();
				for(String tag:arr){
					ts.add(tag);
				}
				record.set("tags", ts);
			}
		}
		return page;
	}
	
	public static List<Blog> findAll(String... columns){
		StringBuffer selectBuffer = new StringBuffer();
		selectBuffer.append("select ");
		for(int i = 0;i < columns.length;i ++){
			selectBuffer.append(columns[i]);
			if(i != columns.length - 1){
				selectBuffer.append(",");
			}
		}
		selectBuffer.append(" from blog");
		return dao.find(selectBuffer.toString());
	}


	/**
	 * @return
	 */
	public static List<Blog> lunbo() {
		String sql = "SELECT id,title,url,coverURL from blog WHERE type = 1 and status = 0 order by publishTime desc limit 6";
		return dao.find(sql);
	}
}
