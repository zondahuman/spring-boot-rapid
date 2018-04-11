package com.abin.lee.rapid.framework.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

/**
 * http://jy03100000.iteye.com/blog/2279525
 */
public class GeneratorMain {
	/**
	 * 请直接修改以下代码调用不同的方法以执行相关生成任务.
	 */
	public static void main(String[] args) throws Exception {
		GeneratorFacade generatorFacade = new GeneratorFacade();
		// g.printAllTableNames(); // 打印数据库中的表名称
		// g.deleteOutRootDir(); //删除生成器的输出目录
		generatorFacade.getGenerator().addTemplateRootDir("./rapid-framework-generator/template");

		// 自动搜索数据库中的所有表并生成文件,template为模板的根目录
		// g.generateByAllTable("template");
		//通过数据库表生成文件,template为模板的根目录
		generatorFacade.generateByTable("order");
//		generatorFacade.generateByTable("ts_ki", "template");
//		generatorFacade.deleteByTable("table_name", "template"); //删除生成的文件
//		generatorFacade.generateByClass(Blog.class,"template_clazz");
		// 打开文件夹
		Runtime.getRuntime().exec(
				"cmd.exe /c start "
						+ GeneratorProperties.getRequiredProperty("outRoot"));
	}



}
