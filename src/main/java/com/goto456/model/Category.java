package com.goto456.model;

import java.util.List;

import com.goto456.model.base.BaseCategory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Category extends BaseCategory<Category> {
	public static final Category dao = new Category();
	
	
	public static List<Category> show(){
		String sql = "select id,name,blogNum from category order by blogNum desc";
		return dao.find(sql);
	}
	
	public static List<Category> findAll(){
		String tableName = TableMapping.me().getTable(Category.class).getName();
		String sql = "select * from " + tableName;
		return dao.find(sql);
	}

	public static Page<Record> paginate(int pageNum, int pageSize) {
		String select = "select category.*,type.name as typeName";
		String suffix = "from category,type where category.typeID = type.id";
		return Db.paginate(pageNum, pageSize, select, suffix);
	}
}
