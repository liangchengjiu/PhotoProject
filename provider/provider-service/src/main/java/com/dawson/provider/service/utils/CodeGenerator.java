package com.dawson.provider.service.utils;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-14 16:15
 **/

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class CodeGenerator {

    /*final static String  dirPath = System.getProperty("user.dir");

     *//*final static String  dirPath = "classpath:/com/example/mybatisP/";*//*

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    */
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     *//*
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dirPath + "/provider/provider-service/src/main/java/");
        System.out.println( "路径为" + gc.getOutputDir());
        gc.setAuthor("Ricardo");
        gc.setFileOverride(true); //是否覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//         gc.setMapperName("%sMapper");
//         gc.setXmlName("%sMapper");
         gc.setServiceName("%sService");
//         gc.setServiceImplName("%sServiceImpl");
//         gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        *//*dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });*//*
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("admin");
        dsc.setUrl("jdbc:mysql://localhost:3306/eladmin?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com");
        pc.setModuleName("dawson.provider.service.sys");
//        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("mapper");

        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(false);
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setTablePrefix(new String[] { "tb_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//列名规则
        strategy.setEntityLombokModel(true);//是否生成lombok注解

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        //自动填充的配置
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);//设置时的生成策略
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);//设置更新时间的生成策略
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(create_time);
        list.add(update_time);
        strategy.setTableFillList(list);
        strategy.setRestControllerStyle(true);//开启驼峰命名
        // 如果 setInclude() 不加参数, 会自定查找所有表
        //strategy.setInclude(new String[] { "t_users" }); // 需要生成的表
        //strategy.setInclude(scanner("表名"));
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 注入自定义配置，可以在 VM 中使用 cfg.zwh 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Ricardo", this.getConfig().getGlobalConfig().getAuthor() + "的模板生成完成！");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
*//*        focList.add(new FileOutConfig("/template/list.jsp.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "D://my_" + tableInfo.getEntityName() + ".jsp";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);*//*

        // 调整 xml 生成目录演示
 *//*       focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return dirPath + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);

        mpg.setCfg(cfg);*//*

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";


        //自定义配置会优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return dirPath + "/mybatisplus/src/main/resources/mapper/"
                        //+ tableInfo.getEntityName() + "Mapper.xml";
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
*//*        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);*//*

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
         TemplateConfig tc = new TemplateConfig();
         tc.setController("");
//         tc.setEntity("...");
//         tc.setMapper("...");
//         tc.setXml("...");
//         tc.setService("...");
//         tc.setServiceImpl("...");
         // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
         mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("Ricardo"));
    }
*/


    // 数据库类型
    private final DbType dbType = DbType.MYSQL;
    // 数据库连结信息
    private final String dbUrl = "jdbc:mysql://localhost:3306/eladmin?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=UTC";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String userName = "root";
    private final String password = "admin";

    // 项目名(路径 + 项目名）
    private final String projectName = System.getProperty("user.dir"); // + "/provider/provider-service";
    // 指定包名
    private final String packageName = "com.dawson.provider.service.sys";

    // controller基础类
    private final String superControllerClass = packageName + "controller";
    // entity基础类
    private final String superEntityClass = packageName + ".entity";
    // 模块名 如果有模块名，则需在模块名前加. 例：.log
    private final String moduleName = "";
    // 作者名
    private final String author = "Ricardo";

    // @todo指定生成的表名 @todo
    private final String[] tableNames = new String[]{
            "sys_menu",
            "sys_role",
            "sys_user",
            "sys_dept",
            "sys_dict",
            "sys_log",
            "sys_quartz_job",
            "sys_quartz_log",
            "sys_job"
    };

    @Test
    public void generateCode() {
        // serviceNameStartWithI：user -> UserService, 设置成true: user -> IUserService
        generateByTables(false, packageName, tableNames);
    }

    /**
     * 根据表自动生成
     *
     * @param serviceNameStartWithI 默认为false
     * @param packageName           包名
     * @param tableNames            表名
     * @author Terry
     */
    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        // 配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
        // 全局变量配置
        GlobalConfig globalConfig = getGlobalConfig(serviceNameStartWithI);
        // 包名配置
        PackageConfig packageConfig = getPackageConfig(packageName);
        // 自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig);
    }

    /**
     * 集成
     *
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     * @author Terry
     */
    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 设置包名
     *
     * @param packageName 父路径包名
     * @param packageName 模块名
     * @return PackageConfig 包名配置
     * @author Terry
     */
    private PackageConfig getPackageConfig(String packageName) {
        return new PackageConfig()
                .setParent(packageName)
                .setXml("mapper.xml")
                .setMapper("mapper")
                .setController("controller")
                .setEntity("entity");
    }

    /**
     * 全局配置
     *
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     * @author Terry
     */
    private GlobalConfig getGlobalConfig(boolean serviceNameStartWithI) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setActiveRecord(false)
                // 作者
                .setAuthor(author)
                // 设置输出路径
                .setOutputDir(getOutputDir(projectName))
                .setFileOverride(true)
                // 将时间设置为Date类型
                .setDateType(DateType.ONLY_DATE);
        if (!serviceNameStartWithI) {
            //设置service名
            globalConfig.setServiceName("%sService");
        }
        return globalConfig;
    }

    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     * @author Terry
     */
    private String getOutputDir(String projectName) {
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        int index = path.indexOf(projectName);
        return projectName + "/src/main/java/";
    }

    /**
     * 策略配置
     *
     * @param tableNames 表名
     * @return StrategyConfig
     * @author Terry
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {
        // 设置时的生成策略
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);
        // 设置更新时间的生成策略
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(create_time);
        list.add(update_time);

        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                // 从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 需要生成的的表名，多个表名传数组
                .setInclude(tableNames)
                // 公共父类
                //.setSuperControllerClass(superControllerClass)
                //.setSuperEntityClass(superEntityClass)
                // 写于父类中的公共字段
                //.setSuperEntityColumns("id")
                // 使用lombok
                .setEntityLombokModel(true)
                // rest风格
                .setRestControllerStyle(true)
                .setTableFillList(list)
                .setEntityTableFieldAnnotationEnable(true);
    }

    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     * @author Terry
     */
    private DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig().setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(userName)
                .setPassword(password)
                .setDriverName(driver);
    }

    /**
     * 根据表自动生成
     *
     * @param packageName 包名
     * @param tableNames  表名
     * @author Terry
     */
    @SuppressWarnings("unused")
    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}