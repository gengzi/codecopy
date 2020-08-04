package fun.gengzi.codecopy.business.authentication.service.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class SecurityInterfaceServiceImplTest {

    @Test
    public void fun01() {

        String info = "{" +
                "\"body\":\"Iphone6 16G\"," +
                "\"subject\":\"大乐透\"," +
                "\"out_trade_no\":\"70501111111S001111119\"," +
                "\"timeout_express\":\"90m\"," +
                "\"time_expire\":\"2016-12-31 10:05\"," +
                "\"total_amount\":9.00," +
                "\"seller_id\":\"2088102147948060\"," +
                "\"auth_token\":\"appopenBb64d181d0146481ab6a762c00714cC27\"," +
                "\"goods_type\":\"0\"," +
                "\"quit_url\":\"http://www.taobao.com/product/113714.html\"," +
                "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "\"product_code\":\"QUICK_WAP_WAY\"," +
                "\"promo_params\":\"{\\\"storeIdType\\\":\\\"1\\\"}\"," +
                "\"royalty_info\":{" +
                "\"royalty_type\":\"ROYALTY\"," +
                "        \"royalty_detail_infos\":[{" +
                "          \"serial_no\":1," +
                "\"trans_in_type\":\"userId\"," +
                "\"batch_no\":\"123\"," +
                "\"out_relation_id\":\"20131124001\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"desc\":\"分账测试1\"," +
                "\"amount_percentage\":\"100\"" +
                "          }]" +
                "    }," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"hb_fq_num\":\"3\"," +
                "\"hb_fq_seller_percent\":\"100\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"sub_merchant\":{" +
                "\"merchant_id\":\"2088000603999128\"," +
                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
                "    }," +
                "\"merchant_order_no\":\"20161008001\"," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"store_id\":\"NJ_001\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardAliasNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]," +
                "\"settle_period_time\":\"7d\"" +
                "    }," +
                "\"invoice_info\":{" +
                "\"key_info\":{" +
                "\"is_support_invoice\":true," +
                "\"invoice_merchant_name\":\"ABC|003\"," +
                "\"tax_num\":\"1464888883494\"" +
                "      }," +
                "\"details\":\"[{\\\"code\\\":\\\"100294400\\\",\\\"name\\\":\\\"服饰\\\",\\\"num\\\":\\\"2\\\",\\\"sumPrice\\\":\\\"200.00\\\",\\\"taxRate\\\":\\\"6%\\\"}]\"" +
                "    }," +
                "\"specified_channel\":\"pcredit\"," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
                "\"ext_user_info\":{" +
                "\"name\":\"李明\"," +
                "\"mobile\":\"16587658765\"," +
                "\"cert_type\":\"IDENTITY_CARD\"," +
                "\"cert_no\":\"362334768769238881\"," +
                "\"min_age\":\"18\"," +
                "\"fix_buyer\":\"F\"," +
                "\"need_check_info\":\"F\"" +
                "    }" +
                "  }";

        System.out.println(info);

    }


}