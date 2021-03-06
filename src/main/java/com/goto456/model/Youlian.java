package com.goto456.model;

import com.goto456.model.base.BaseYoulian;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Youlian extends BaseYoulian<Youlian> {
	public static final Youlian dao = new Youlian();

	public static Page<Record> paginate(int pageNum, int pageSize) {
		String select = "select *";
		String suffix = "from youlian";
		return Db.paginate(pageNum, pageSize, select, suffix);
	}
}
