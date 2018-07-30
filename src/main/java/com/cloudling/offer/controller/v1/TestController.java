package com.cloudling.offer.controller.v1;

import com.cloudling.offer.annotation.action;
import com.cloudling.offer.bean.OfferBean;
import com.cloudling.offer.bean.ProductBean;
import com.cloudling.offer.exception.ParamsErrorException;
import com.cloudling.offer.model.PartModel;
import com.cloudling.offer.model.ProductModel;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;
import com.cloudling.offer.util.BeanUtil;
import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;




public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

    @action
    public void list() {
        PartModel part = new PartModel();
        ArrayList<HashMap<String, String>> list = part.list();
        success(list);
    }

    @action
    public void add() {

    }

    @action
    public void add_bean() {

//        long t = TimeUtil.getLongTimeStamp();
//        ProductModel productModel = new ProductModel();
//
//        ProductBean bean = productModel.getBean("52");
//        long ms = TimeUtil.getLongTimeStamp() - t;
//        System.out.println("ms:"+ms);

        makeHttp("{\n" +
                "    \"code\": \"W40002G\",\n" +
                "    \"create_time\": 0,\n" +
                "    \"price\": 1,\n" +
                "    \"id\": \"47\",\n" +
                "    \"spareBeans\": [\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"颜色\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"白钢\",\n" +
                "                            \"id\": \"1437\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"仿金\",\n" +
                "                            \"id\": \"1438\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"玫瑰金\",\n" +
                "                            \"id\": \"1439\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"枪色\",\n" +
                "                            \"id\": \"1440\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"05\",\n" +
                "                            \"price\": 1,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"IPS\",\n" +
                "                            \"id\": \"1441\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"06\",\n" +
                "                            \"price\": 2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"IPG\",\n" +
                "                            \"id\": \"1442\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"07\",\n" +
                "                            \"price\": 2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"IPRG\",\n" +
                "                            \"id\": \"1443\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"08\",\n" +
                "                            \"price\": 2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"IP枪\",\n" +
                "                            \"id\": \"1444\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"09\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"喷漆\",\n" +
                "                            \"id\": \"1445\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"10\",\n" +
                "                            \"price\": 0.8,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"10 micron Copper ***/ 1.25 micron white bronze/gold for color\",\n" +
                "                            \"id\": \"1446\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"11\",\n" +
                "                            \"price\": 3.8,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"10 micron Copper ***/ 2 micron white bronze/ flash PCP / 0.2 micron TiN / IP Rose gold for color\",\n" +
                "                            \"id\": \"1447\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"12\",\n" +
                "                            \"price\": 3.6,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"10 micron Copper ***/ 2 micron white bronze/ flash PCP / 0.2 micron TiN /gold for color\",\n" +
                "                            \"id\": \"1448\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"13\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1436,\n" +
                "                            \"name\": \"10 microns Copper*** / Nickel free Gun Metal / E-coating\",\n" +
                "                            \"id\": \"1449\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1436\",\n" +
                "                    \"spare_id\": 4232\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"大身尺寸\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1450,\n" +
                "                            \"name\": \"39.5mm\",\n" +
                "                            \"id\": \"1451\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1450\",\n" +
                "                    \"spare_id\": 4232\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"防水\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1452,\n" +
                "                            \"name\": \"生活防水\",\n" +
                "                            \"id\": \"1453\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.3,\n" +
                "                            \"parent_id\": 1452,\n" +
                "                            \"name\": \"1ATM\",\n" +
                "                            \"id\": \"1454\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 1,\n" +
                "                            \"parent_id\": 1452,\n" +
                "                            \"name\": \"3ATM\",\n" +
                "                            \"id\": \"1455\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1452,\n" +
                "                            \"name\": \"5ATM\",\n" +
                "                            \"id\": \"1456\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1452,\n" +
                "                            \"name\": \"10ATM\",\n" +
                "                            \"id\": \"1457\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1452\",\n" +
                "                    \"spare_id\": 4232\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602796,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"表壳\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4232\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"材质\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 1.2,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"PU\",\n" +
                "                            \"id\": \"1459\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 3.3,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"真皮\",\n" +
                "                            \"id\": \"1460\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"硅胶\",\n" +
                "                            \"id\": \"1461\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"PVC\",\n" +
                "                            \"id\": \"1462\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"05\",\n" +
                "                            \"price\": 2,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"202钢带\",\n" +
                "                            \"id\": \"1463\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"06\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"304钢带\",\n" +
                "                            \"id\": \"1464\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"07\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"网织带\",\n" +
                "                            \"id\": \"1465\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"08\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"尼龙带\",\n" +
                "                            \"id\": \"1466\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"09\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"弹簧带\",\n" +
                "                            \"id\": \"1467\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"10\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"TPU\",\n" +
                "                            \"id\": \"1468\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"11\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1458,\n" +
                "                            \"name\": \"丝巾带\",\n" +
                "                            \"id\": \"1469\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1458\",\n" +
                "                    \"spare_id\": 4233\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"耳位尺寸\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1470,\n" +
                "                            \"name\": \"20MM\",\n" +
                "                            \"id\": \"1471\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1470\",\n" +
                "                    \"spare_id\": 4233\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"表扣电镀\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"白钢\",\n" +
                "                            \"id\": \"1473\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 0.3,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"仿金\",\n" +
                "                            \"id\": \"1474\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 0.3,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"玫瑰金\",\n" +
                "                            \"id\": \"1475\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 0.3,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"枪色\",\n" +
                "                            \"id\": \"1476\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"05\",\n" +
                "                            \"price\": 0.9,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"IPG\",\n" +
                "                            \"id\": \"1477\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"06\",\n" +
                "                            \"price\": 0.9,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"IPRG\",\n" +
                "                            \"id\": \"1478\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"07\",\n" +
                "                            \"price\": 0.9,\n" +
                "                            \"parent_id\": 1472,\n" +
                "                            \"name\": \"IP枪\",\n" +
                "                            \"id\": \"1479\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1472\",\n" +
                "                    \"spare_id\": 4233\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"表带电镀\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"白钢\",\n" +
                "                            \"id\": \"1481\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 2.5,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"仿金\",\n" +
                "                            \"id\": \"1482\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 2.5,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"玫瑰金\",\n" +
                "                            \"id\": \"1483\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 2.5,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"枪色\",\n" +
                "                            \"id\": \"1484\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"05\",\n" +
                "                            \"price\": 5.8,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"IPG\",\n" +
                "                            \"id\": \"1485\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"06\",\n" +
                "                            \"price\": 6.1,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"IPRG\",\n" +
                "                            \"id\": \"1486\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"07\",\n" +
                "                            \"price\": 4.6,\n" +
                "                            \"parent_id\": 1480,\n" +
                "                            \"name\": \"IP枪\",\n" +
                "                            \"id\": \"1487\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1480\",\n" +
                "                    \"spare_id\": 4233\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"表带印刷\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0,\n" +
                "                            \"parent_id\": 1488,\n" +
                "                            \"name\": \"无印刷\",\n" +
                "                            \"id\": \"1489\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1488,\n" +
                "                            \"name\": \"印刷\",\n" +
                "                            \"id\": \"1490\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1488\",\n" +
                "                    \"spare_id\": 4233\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602796,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"表带\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4233\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"表面\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 0.25,\n" +
                "                            \"parent_id\": 1491,\n" +
                "                            \"name\": \"丝印面\",\n" +
                "                            \"id\": \"1492\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 0.15,\n" +
                "                            \"parent_id\": 1491,\n" +
                "                            \"name\": \"丝印面+UP\",\n" +
                "                            \"id\": \"1493\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 0.15,\n" +
                "                            \"parent_id\": 1491,\n" +
                "                            \"name\": \"太阳纹面\",\n" +
                "                            \"id\": \"1494\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 0.3,\n" +
                "                            \"parent_id\": 1491,\n" +
                "                            \"name\": \"太阳纹面+UP\",\n" +
                "                            \"id\": \"1495\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1491\",\n" +
                "                    \"spare_id\": 4234\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602797,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"表面\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4234\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"表针\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.075,\n" +
                "                            \"parent_id\": 1496,\n" +
                "                            \"name\": \"普通表针\",\n" +
                "                            \"id\": \"1497\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1496,\n" +
                "                            \"name\": \"特殊表针\",\n" +
                "                            \"id\": \"1498\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1496\",\n" +
                "                    \"spare_id\": 4235\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602797,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"表针\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4235\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"颜色[表壳,颜色]\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"01\",\n" +
                "                            \"price\": 0.15,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"白钢\",\n" +
                "                            \"id\": \"1500\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"02\",\n" +
                "                            \"price\": 0.05,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"仿金\",\n" +
                "                            \"id\": \"1501\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"03\",\n" +
                "                            \"price\": 0.05,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"玫瑰金\",\n" +
                "                            \"id\": \"1502\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"04\",\n" +
                "                            \"price\": 0.05,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"枪色\",\n" +
                "                            \"id\": \"1503\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"05\",\n" +
                "                            \"price\": 0.18,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"IPS\",\n" +
                "                            \"id\": \"1504\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"06\",\n" +
                "                            \"price\": 0.18,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"IPG\",\n" +
                "                            \"id\": \"1505\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"07\",\n" +
                "                            \"price\": 0.18,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"IPRG\",\n" +
                "                            \"id\": \"1506\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"08\",\n" +
                "                            \"price\": 0.18,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"IP枪\",\n" +
                "                            \"id\": \"1507\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"09\",\n" +
                "                            \"price\": 0.1,\n" +
                "                            \"parent_id\": 1499,\n" +
                "                            \"name\": \"喷漆\",\n" +
                "                            \"id\": \"1508\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1499\",\n" +
                "                    \"spare_id\": 4236\n" +
                "                },\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"防水[表壳,防水]\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.03,\n" +
                "                            \"parent_id\": 1509,\n" +
                "                            \"name\": \"1ATM\",\n" +
                "                            \"id\": \"1510\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.2,\n" +
                "                            \"parent_id\": 1509,\n" +
                "                            \"name\": \"3ATM\",\n" +
                "                            \"id\": \"1511\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1509,\n" +
                "                            \"name\": \"5ATM\",\n" +
                "                            \"id\": \"1512\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": -2,\n" +
                "                            \"parent_id\": 1509,\n" +
                "                            \"name\": \"10ATM\",\n" +
                "                            \"id\": \"1513\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1509\",\n" +
                "                    \"spare_id\": 4236\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602797,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"巴的\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4236\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"生耳\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.024,\n" +
                "                            \"parent_id\": 1514,\n" +
                "                            \"id\": \"1515\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1514\",\n" +
                "                    \"spare_id\": 4237\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602797,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"生耳\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4237\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attrBeans\": [\n" +
                "                {\n" +
                "                    \"price\": -1,\n" +
                "                    \"parent_id\": 0,\n" +
                "                    \"num\": \"-1\",\n" +
                "                    \"name\": \"装配\",\n" +
                "                    \"f_attrBeans\": [\n" +
                "                        {\n" +
                "                            \"code\": \"0\",\n" +
                "                            \"price\": 0.6,\n" +
                "                            \"parent_id\": 1516,\n" +
                "                            \"id\": \"1517\",\n" +
                "                            \"spare_id\": 0\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"id\": \"1516\",\n" +
                "                    \"spare_id\": 4238\n" +
                "                }\n" +
                "            ],\n" +
                "            \"is_float\": false,\n" +
                "            \"create_time\": 1532602797,\n" +
                "            \"product_id\": 47,\n" +
                "            \"name\": \"装配\",\n" +
                "            \"cat_id\": 22,\n" +
                "            \"id\": \"4238\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");

    }
    }
















