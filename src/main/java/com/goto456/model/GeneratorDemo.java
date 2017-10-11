package com.goto456.model;

import java.io.IOException;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import com.goto456.Parameters;

/**
 * GeneratorDemo
 */
public class GeneratorDemo {

	public static DataSource getDataSource() {
		DruidPlugin cp = new DruidPlugin(Parameters.DB_URL,
				Parameters.DB_USERNAME, Parameters.DB_PASSWORD);
		cp.start();
		return cp.getDataSource();
	}


	public static void gen(){
		// base model 所使用的包名
		String baseModelPackageName = "com.goto456.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/goto456/model/base";

		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.goto456.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";

		// 创建生成器
		Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 添加不需要生成的表名
		gernerator.addExcludedTable("blog_display");
		gernerator.addExcludedTable("blog_display_by_tag");
		gernerator.addExcludedTable("blog_back_display");

		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		gernerator.setRemovedTableNamePrefixes("t_");
		// 生成
		gernerator.generate();
	}

	public static void main(String[] args) throws IOException {
		gen();
	}
}









