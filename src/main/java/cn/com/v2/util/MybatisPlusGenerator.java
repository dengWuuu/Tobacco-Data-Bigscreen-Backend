package cn.com.v2.util;

import java.util.Collections;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;


public class MybatisPlusGenerator {
	public static void main(String[] args) {
		FastAutoGenerator.create(
				"jdbc:sqlite:D:\\eclipse-workspace\\v2-goview-bate\\sqllite\\goview.db",
				"", "").globalConfig(builder -> {
					builder.author("fc") // 设置作者
							// .enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.disableOpenDir() //禁止打开输出目录
							.outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
				}).packageConfig(builder -> {
					builder.parent("cn.com") // 设置父包名
							.moduleName("v2") // 设置父包模块名
							.entity("model")
							// .service() // 设置自定义service路径,不设置就是默认路径
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml,
							System.getProperty("user.dir") + "/src/main/resources/mapper/")); // 设置mapperXml生成路径
				}).strategyConfig(builder -> {
					builder.addInclude("t_goview_project_data") // 设置需要生成的表名
							.addTablePrefix("t_", "c_")
							// 设置自动填充的时间字段
							.entityBuilder().addTableFills(new Column("create_time", FieldFill.INSERT),
							new Column("update_time", FieldFill.INSERT_UPDATE)); // 设置过滤表前缀

				}).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
