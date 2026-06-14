package com.spboot.tx.controller;

import com.jntoo.db.DB;
import com.spboot.tx.pojo.*;
import com.spboot.tx.utils.*;
import io.swagger.annotations.Api;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "支付" })
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Resource
    HttpServletRequest request;

    /**
     * 处理支付
     * @return String
     */
    @RequestMapping("/payment")
    @ResponseBody
    public ResponseData payment() {
        String id, biao;
        if (request.getParameter("out_trade_no") != null) {
            String[] out_trade_no = request.getParameter("out_trade_no").split("\\-");
            id = out_trade_no[2];
            biao = out_trade_no[1];
        } else {
            id = request.getParameter("id");
            biao = request.getParameter("biao");
        }

        String sql = "update " + biao + " set iszf='是' where id='" + id + "'";
        DB.execute(sql);

        if ("dingdan".equals(biao)) {
            Dingdan order = DB.name(Dingdan.class).find(id);
            DB.execute("UPDATE shangpin AS s, dingdanshangpin AS ss SET s.xiaoliang = s.xiaoliang+ss.goumaishuliang, s.kucun = s.kucun-ss.goumaishuliang WHERE s.id=ss.shangpinid AND ss.dingdanid=" + order.getId() + "");

            DB.execute("UPDATE dingdan SET dingdanzhuangtai = '已支付' WHERE id=" + order.getId() + "");

        }

        return JsonResult.success("ok");
    }
}
