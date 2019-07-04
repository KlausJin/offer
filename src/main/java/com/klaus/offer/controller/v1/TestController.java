package com.klaus.offer.controller.v1;


import com.klaus.offer.annotation.action;
import com.klaus.offer.model.InputExcelModel;
import com.klaus.offer.server.Controller;
import com.klaus.offer.server.ControllerContext;
import com.klaus.offer.util.InputExcelUtil;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-04  13:07
 * @Version 1.0
 **/
public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

//    @action
//    public void test(){
//        File file = new File("assets/resourses/01.xlsx");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        HashMap<String, String> m = new HashMap<String, String>();
//        m.put("id", "id");
//        m.put("姓名", "name");
//        m.put("年龄", "age");
//        List<HashMap<String, String>> ls = null;
//        try {
//            ls = parseExcel(fis, file.getName(), m);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(ls));
//    }
//    @action
//    public void test1(){
//        String id = I("id") == null ? "" : I("id").toString();
//        PartCatModel pm =new PartCatModel();
//        success(pm.getBean_real(id).getData());
//    }

//    @action
//    public void test2(){
//        PersonExcelModel  pm=new PersonExcelModel();
//        try {
//            List<HashMap<String, String>> ls = pm.excelToPersonList();
//            System.out.println(JSON.toJSONString(ls));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @action
//    public void test3(){
//        String x="123.0";
//        double a=0;
//        int b=0;
//        a=Double.valueOf(x);
//        b=(int)a;
//        System.out.println(b);
//    }

//    @action
//    public void test4(){
//        ArrayList<HashMap<String, String>> list = M("attr").field("id,parent_id").where(" parent_id !=0 and num=-2").select();
//        HashMap<String, String> spe=new HashMap<>();
//        for(int j=0;j<list.size();j++){
//            String x=list.get(j).get("id");
//            String y=list.get(j).get("parent_id");
//            spe.put(x,y);
//        }
//        success(spe);
//    }

    @action
    public void test5(){
        InputExcelUtil.copyExcel("assets/resourses/watch/英姿、万思刻-所有业务员.xls","assets/resourses/watch/test.xls");
        System.out.println("1");
    }

    @action
    public void test6(){

        InputExcelModel iem=new InputExcelModel("工作簿1.xlsx","9");
        try{
            String data = iem.do_excel();
            success(data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
