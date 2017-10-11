package com.goto456.back;

import java.util.Date;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import com.goto456.ResConsts;
import com.goto456.model.Youlian;

public class YoulianController extends AbstarctBackController {

	@Override
	public void doPage(int pageNum, int pageSize) {
		// 查询数据
		Page<Record> data = Youlian.paginate(pageNum, pageSize);
		// 渲染结果
		render(ResConsts.Code.SUCCESS, null, data);
	}

	@Override
	public void add() {
		Youlian youlian = getModel(Youlian.class, "youlian");
		youlian.setCdate(new Date(System.currentTimeMillis()));
		if(youlian.save()){
			render(ResConsts.Code.SUCCESS,"保存成功");
		} else {
			render(ResConsts.Code.FAILURE,"保存失败");
		}
	}

	@Override
	protected boolean doDel(String id) {
		return Youlian.dao.deleteById(id);
	}


	@Override
	public void edit() {
		Youlian youlian = getModel(Youlian.class, "youlian");
		if(youlian.update()){
			render(ResConsts.Code.SUCCESS,"保存成功");
		} else {
			render(ResConsts.Code.FAILURE,"保存失败");
		}
		
	}
	
	
	protected Object doGet(String id){
		return Youlian.dao.findById(id);
	}
	
	
	

}
