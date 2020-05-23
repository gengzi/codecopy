package fun.gengzi.codecopy.generator;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class CommonController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "codeBuildNew")
    @ResponseBody
    public String codeBuildNew(String tablename) throws SQLException {

        List<String> list = new ArrayList<>();

        String comb_str = "";

        String CODE_BUILD_SQL = "SELECT	t.COLUMN_NAME,t.COLUMN_COMMENT,t.DATA_TYPE FROM	information_schema.COLUMNS t WHERE	t.table_schema = (select database() ) AND t.table_name = '表名'";

        CODE_BUILD_SQL = CODE_BUILD_SQL.replace("表名", tablename);

        List<Map<String, Object>> datalist = jdbcTemplate.queryForList(CODE_BUILD_SQL);

        //文本框
        final String string_model = "<input  class=\"easyui-validatebox textbox\" type=\"text\" name=\"字段\" id=\"字段\" value=\"${entity.字段}\"  data-options=\"width: 200,prompt:''\"></input>";

        //数字框
        final String num_model = "<input  class=\"easyui-numberbox\" type=\"text\" name=\"字段\" id=\"字段\" value=\"${entity.字段}\"  data-options=\"min:0,precision:2,width: 200,prompt:''\"></input>";

        //隐藏域
        final String hidden_model = "<input type=\"hidden\" name=\"字段\" id=\"字段\" value=\"${entity.字段}\">";

        //日期框
        final String date_model = "<input name=\"字段\" id=\"字段\" value=\"${entity.字段}\" type=\"text\" class=\"easyui-my97\"  datefmt=\"yyyy-MM-dd\" data-options=\"width:200,prompt: ''\"/>";

        //下拉框
        final String combobox_model = "<input id=\"字段\" name=\"字段\" value=\"${entity.字段 }\" type=\"text\" class=\"easyui-validatebox\"  data-options=\"width: 200\"   />\r\n" +
                "$('#字段').combobox({    \r\n" +
                "	 url:'${ctx}/getdic/zybzl',\r\n" +
                "	 method: \"post\",\r\n" +
                "	 valueField:'code',    \r\n" +
                "	 textField:'name'\r\n" +
                "}); ";

        //js下拉框
        final String js_combobox_model = "$('#字段').combobox({    \r\n" +
                "	 url:'${ctx}/getdic/zybzl',\r\n" +
                "	 method: \"post\",\r\n" +
                "	 valueField:'code',    \r\n" +
                "	 textField:'name'\r\n" +
                "}); ";


        //输出实体类文件
        System.out.println("import javax.persistence.*;");
        System.out.println("import java.util.Date;");
        System.out.println("import com.fasterxml.jackson.annotation.JsonFormat;");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("@Entity");
        System.out.println("@Table(name=\"表名\")".replace("表名", tablename));

        String classname = SpringUtils.camelName(tablename);

        classname = classname.substring(0, 1).toUpperCase() + classname.substring(1);

        System.out.println("public class 表名 {".replace("表名", classname));

        for (Map xx : datalist) {

            Collection<String> values = xx.values();

            Object[] objects = values.toArray();
            String columnName = SpringUtils.camelName(objects[0] + "");

            String columnComment = objects[1]  + "";

            String columnDatatype = objects[2]  + "";

            if ("id".equals(columnName)) {

                System.out.println("    @Id");
                System.out.println("  	@GeneratedValue(strategy = GenerationType.IDENTITY)");

            }

            System.out.println("//" + columnComment);

            if ("date".equals(columnDatatype) || "datetime".equals(columnDatatype)) {

                System.out.println("     @JsonFormat(pattern=\"yyyy-MM-dd\")\r\n" +
                        "	@Temporal(TemporalType.DATE)\r\n" +
                        "   @Column(name = \"" + objects[0] + "\")");

            } else {
                System.out.println("   @Column(name = \"" + objects[0] + "\")");
            }

            System.out.print("    private ");

            if ("varchar".equals(columnDatatype)) {

                System.out.print("String ");

            }

            if ("date".equals(columnDatatype) || "datetime".equals(columnDatatype)) {

                System.out.print("Date ");

            }


            if ("int".equals(columnDatatype)) {

                System.out.print("Integer ");

            }

            System.out.println(columnName + ";");


        }

        System.out.println("}");


        System.out.println(classname.toLowerCase() + ".controller");
        System.out.println(classname.toLowerCase() + ".service");
        System.out.println(classname.toLowerCase() + ".entity");
        System.out.println(classname.toLowerCase() + ".dao");

        System.out.println("============dao==============");
        //生成 dao
        String createdao =
                "import org.springframework.stereotype.Repository;\n" +
                        "import com.sinosoft.ie.rms.common.persistence.SimpleHibernateDao;\n" +
                        "@Repository\n" +
                        "public interface gsjxxxDao extends SimpleHibernateDao<gsjxxx, String>{\n" +
                        "\n" +
                        "}\n";

        System.out.println(createdao.replace("gsjxxx", classname));

        //生成service
        System.out.println("============service==============");
        String createService =
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import org.springframework.stereotype.Service;\n" +
                        "\n" +
                        "import com.sinosoft.ie.rms.common.persistence.SimpleHibernateDao;\n" +
                        "import com.sinosoft.ie.rms.common.service.BaseService;\n" +
                        "@Service\n" +
                        "public class gsjxxxService extends BaseService<gsjxxx, String>{\n" +
                        "\n" +
                        "\t@Autowired\n" +
                        "    private gsjxxxDao gsjxxxxDao;\n" +
                        "\t\n" +
                        "\t\n" +
                        "\t@Override\n" +
                        "\tpublic SimpleHibernateDao<gsjxxx, String> getEntityDao() {\n" +
                        "\t\t// TODO Auto-generated method stub\n" +
                        "\t\treturn gsjxxxxDao;\n" +
                        "\t}\n" +
                        "\n" +
                        "}";
        createService = createService.replace("gsjxxxx", toLowerCaseFirstOne(classname));
        System.out.println(createService.replace("gsjxxx", classname));


        // 生成controller
        System.out.println("============controller==============");


        String cratecontroller =
                "import com.sinosoft.ie.rms.common.persistence.Page;\n" +
                        "import com.sinosoft.ie.rms.common.persistence.PropertyFilter;\n" +
                        "import com.sinosoft.ie.rms.common.web.BaseController;\n" +
                        "import com.sinosoft.ie.rms.system.entity.User;\n" +
                        "import com.sinosoft.ie.rms.system.utils.StringUtils;\n" +
                        "import com.sinosoft.ie.rms.system.utils.UserUtil;\n" +
                        "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import org.springframework.stereotype.Controller;\n" +
                        "import org.springframework.ui.Model;\n" +
                        "import org.springframework.web.bind.annotation.PathVariable;\n" +
                        "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                        "import org.springframework.web.bind.annotation.RequestMethod;\n" +
                        "import org.springframework.web.bind.annotation.ResponseBody;\n" +
                        "\n" +
                        "import javax.servlet.http.HttpServletRequest;\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.Date;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Map;\n" +
                        "\n" +
                        "@Controller\n" +
                        "@RequestMapping(value = \"business/group/gsjxxxxController\")\n" +
                        "public class gsjxxxController extends BaseController {\n" +
                        "\n" +
                        "\n" +
                        "    @Autowired\n" +
                        "    private gsjxxxService gsjxxxxService;\n" +
                        "    @Autowired\n" +
                        "    private DataViewService dataViewService;\n" +
                        "\n" +
                        "    @Autowired\n" +
                        "    private BusinessAuditInfoService businessAuditInfoService;" +
                        "    /**\n" +
                        "     * 进入数据表格页面\n" +
                        "     *\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"geturl\")\n" +
                        "    public String geturl() {\n" +
                        "        return \"business/gsjxxxxx/list\";\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 进入添加页面\n" +
                        "     *\n" +
                        "     * @param model\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"add\", method = RequestMethod.GET)\n" +
                        "    public String add(Model model) {\n" +
                        "        model.addAttribute(\"action\", \"add\");\n" +
                        "        return \"business/gsjxxxxx/form\";\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 进入查看页面\n" +
                        "     *\n" +
                        "     * @param id\n" +
                        "     * @param model\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"view/{id}\", method = RequestMethod.GET)\n" +
                        "    public String view(@PathVariable(value = \"id\") String id, Model model) {\n" +
                        "        gsjxxx entity = gsjxxxxService.get(id);\n" +
                        "        model.addAttribute(\"entity\", entity);\n" +
                        "        model.addAttribute(\"action\", \"view\");\n" +
                        "        return \"business/gsjxxxxx/form\";\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 进入更新页面\n" +
                        "     *\n" +
                        "     * @param id\n" +
                        "     * @param model\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"upd/{id}\", method = RequestMethod.GET)\n" +
                        "    public String upd(@PathVariable(value = \"id\") String id, Model model) {\n" +
                        "        gsjxxx entity = gsjxxxxService.get(id);\n" +
                        "        model.addAttribute(\"entity\", entity);\n" +
                        "        model.addAttribute(\"action\", \"upd\");\n" +
                        "        return \"business/gsjxxxxx/form\";\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 添加信息\n" +
                        "     *\n" +
                        "     * @param entity\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"add\", method = RequestMethod.POST)\n" +
                        "    @ResponseBody\n" +
                        "    public String add(gsjxxx entity) {\n" +
                        "        User user = UserUtil.getCurrentUser();\n" +
                        "        entity.setCreateUserAccount(String.valueOf(user.getId()));\n" +
                        "        entity.setCreateUserName(user.getName());\n" +
                        "        entity.setCreateDate(new Date());\n" +
                        "        entity.setIsDel(0);\n" +
                        //"        gsjxxxxService.save(entity);\n" +
                        "gsjxxx entity1 = gsjxxxxService.saveReturnID(entity);\n" +
                        "        // 获取业务id，存放到审核表\n" +
                        "        String enterpriseInfoId = entity1.getEnterpriseInfoId();\n" +
                        "        System.out.println(\"save gsjxxx success,id:\"+enterpriseInfoId);\n" +
                        "        String auditType = request.getParameter(\"auditType\");\n" +
                        "        // 保存审核信息\n" +
                        "        businessAuditInfoService.saveAuditInfoAndAuditLogInfo(enterpriseInfoId, auditType);" +
                        "        return \"success\";\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 更新信息\n" +
                        "     *\n" +
                        "     * @param entity\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"upd\", method = RequestMethod.POST)\n" +
                        "    @ResponseBody\n" +
                        "    public String upd(gsjxxx entity) {\n" +
                        "        Boolean aBoolean = businessAuditInfoService.isUpdate(entity.getEnterpriseInfoId());\n" +
                        "        if(aBoolean){" +
                        "        gsjxxxxService.update(entity);\n" +
                        "        }" +
                        "        return \"success\";\n" +
                        "    }\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 根据id删除数据\n" +
                        "     *\n" +
                        "     * @param id\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"del/{id}\")\n" +
                        "    @ResponseBody\n" +
                        "    public String del(@PathVariable(value = \"id\") String id) {\n" +
                        "  // 判断该条数据的状态是否为 待上报。\n" +
                        "        Boolean del = businessAuditInfoService.isDel(id);\n" +
                        "        if(del){" +
                        "        gsjxxx entity = gsjxxxxService.get(id);\n" +
                        "        entity.setIsDel(1);\n" +
                        "        gsjxxxxService.update(entity);\n" +
                        "        }" +
                        "        return \"success\";\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 查询数据\n" +
                        "     *\n" +
                        "     * @param request\n" +
                        "     * @return\n" +
                        "     */\n" +
                        "    @RequestMapping(value = \"getpage\")\n" +
                        "    @ResponseBody\n" +
                        "    public Map<String, Object> getpage(HttpServletRequest request) {\n" +
                        "        Page<gsjxxx> page = getPage(request);\n" +
                        "        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);\n" +
                        "        PropertyFilter filterone = new PropertyFilter(\"EQI_isDel\", \"0\");\n" +
                        "        filters.add(filterone);\n" +
                        " // 获取数据范围展示的查询条件\n" +
                        "        PropertyFilter filterInfo = dataViewService.getFilterInfo();\n" +
                        "        if(filterInfo != null){\n" +
                        "            filters.add(filterInfo);\n" +
                        "        }" +
                        "        page = gsjxxxxService.search(page, filters);\n" +
                        "        return getEasyUIData(page, gsjxxx.class);\n" +
                        "    }\n" +
                        "}\n";

        cratecontroller = cratecontroller.replace("gsjxxxxx", classname.toLowerCase());
        cratecontroller = cratecontroller.replace("gsjxxxx", toLowerCaseFirstOne(classname));
        System.out.println(cratecontroller.replace("gsjxxx", classname));


        //输出前台文件
        for (Map xx : datalist) {

            Collection<String> values = xx.values();

            Object[] objects = values.toArray();
            String columnName = SpringUtils.camelName(objects[0] + "");

            String columnComment = objects[1]  + "";

            String columnNameTable = objects[2]  + "";



            if (StringUtils.isBlank(columnComment)) {

                System.out.println("隐藏域");

                System.out.println("字段:" + "隐藏域");
                //隐藏域
                System.out.println(hidden_model.replace("字段", columnName));
            } else {

                if (StringUtils.startsWith(columnNameTable, "dt_")) {

                    System.out.println("日期框");

                    System.out.println("字段:" + columnComment);
                    //日期框
                    System.out.println(date_model.replace("字段", columnName));

                    continue;
                }


                if (StringUtils.startsWith(columnNameTable, "dic_")) {

                    System.out.println("下拉框");

                    System.out.println("字段:" + columnComment);
                    //下拉框
                    System.out.println(combobox_model.replace("字段", columnName));

                    comb_str += js_combobox_model.replace("字段", columnName) + "\r\n";

                    continue;

                }


                if (StringUtils.startsWith(columnNameTable, "num_")) {

                    System.out.println("数字框");

                    System.out.println("字段:" + columnComment);
                    //下拉框
                    System.out.println(num_model.replace("字段", columnName));

                    continue;

                }


                System.out.println("文本框");

                System.out.println("字段:" + columnComment);
                //文本框
                System.out.println(string_model.replace("字段", columnName));


            }


        }

        System.out.println("下拉框批量复制");

        System.out.println(comb_str);

        return "";


    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


}
