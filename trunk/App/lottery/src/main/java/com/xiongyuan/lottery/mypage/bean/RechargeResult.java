package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by gameben on 2017-08-03.
 */

public class RechargeResult {

    /**
     * errormsg :
     * result : {"payType":{"scan":"高通扫码","banking":"网银支付"},"payWay":{"weixin_scan":{"title":"微信扫码支付","type":"scan","mode":"QRCode"},"alipay_scan":{"title":"支付宝扫码支付","type":"scan","mode":"QRCode"},"qq_scan":{"title":"QQ钱包扫码支付","type":"scan","mode":"QRCode"},"ABC":{"title":"农业银行","type":"banking","mode":"direct"},"ICBC":{"title":"工商银行","type":"banking","mode":"direct"},"CCB":{"title":"建设银行","type":"banking","mode":"direct"},"BCOM":{"title":"交通银行","type":"banking","mode":"direct"},"BOC":{"title":"中国银行","type":"banking","mode":"direct"},"CMB":{"title":"招商银行","type":"banking","mode":"direct"},"CMBC":{"title":"民生银行","type":"banking","mode":"direct"},"CEBB":{"title":"光大银行","type":"banking","mode":"direct"},"BOB":{"title":"北京银行","type":"banking","mode":"direct"},"SHB":{"title":"上海银行","type":"banking","mode":"direct"},"NBB":{"title":"宁波银行","type":"banking","mode":"direct"},"HXB":{"title":"华夏银行","type":"banking","mode":"direct"},"CIB":{"title":"兴业银行","type":"banking","mode":"direct"},"PSBC":{"title":"中国邮政银行","type":"banking","mode":"direct"},"SPABANK":{"title":"平安银行","type":"banking","mode":"direct"},"SPDB":{"title":"浦发银行","type":"banking","mode":"direct"},"ECITIC":{"title":"中信银行","type":"banking","mode":"direct"},"HZB":{"title":"杭州银行","type":"banking","mode":"direct"},"GDB":{"title":"广发银行","type":"banking","mode":"direct"}}}
     * TIME : 1501715334
     */

    private String errormsg;
    private ResultBean result;
    private int TIME;

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getTIME() {
        return TIME;
    }

    public void setTIME(int TIME) {
        this.TIME = TIME;
    }

    public static class ResultBean {
        /**
         * payType : {"scan":"高通扫码","banking":"网银支付"}
         * payWay : {"weixin_scan":{"title":"微信扫码支付","type":"scan","mode":"QRCode"},"alipay_scan":{"title":"支付宝扫码支付","type":"scan","mode":"QRCode"},"qq_scan":{"title":"QQ钱包扫码支付","type":"scan","mode":"QRCode"},"ABC":{"title":"农业银行","type":"banking","mode":"direct"},"ICBC":{"title":"工商银行","type":"banking","mode":"direct"},"CCB":{"title":"建设银行","type":"banking","mode":"direct"},"BCOM":{"title":"交通银行","type":"banking","mode":"direct"},"BOC":{"title":"中国银行","type":"banking","mode":"direct"},"CMB":{"title":"招商银行","type":"banking","mode":"direct"},"CMBC":{"title":"民生银行","type":"banking","mode":"direct"},"CEBB":{"title":"光大银行","type":"banking","mode":"direct"},"BOB":{"title":"北京银行","type":"banking","mode":"direct"},"SHB":{"title":"上海银行","type":"banking","mode":"direct"},"NBB":{"title":"宁波银行","type":"banking","mode":"direct"},"HXB":{"title":"华夏银行","type":"banking","mode":"direct"},"CIB":{"title":"兴业银行","type":"banking","mode":"direct"},"PSBC":{"title":"中国邮政银行","type":"banking","mode":"direct"},"SPABANK":{"title":"平安银行","type":"banking","mode":"direct"},"SPDB":{"title":"浦发银行","type":"banking","mode":"direct"},"ECITIC":{"title":"中信银行","type":"banking","mode":"direct"},"HZB":{"title":"杭州银行","type":"banking","mode":"direct"},"GDB":{"title":"广发银行","type":"banking","mode":"direct"}}
         */

        private PayTypeBean payType;
        private PayWayBean payWay;

        public PayTypeBean getPayType() {
            return payType;
        }

        public void setPayType(PayTypeBean payType) {
            this.payType = payType;
        }

        public PayWayBean getPayWay() {
            return payWay;
        }

        public void setPayWay(PayWayBean payWay) {
            this.payWay = payWay;
        }

        public static class PayTypeBean {
            /**
             * scan : 高通扫码
             * banking : 网银支付
             */

            private String scan;
            private String banking;

            public String getScan() {
                return scan;
            }

            public void setScan(String scan) {
                this.scan = scan;
            }

            public String getBanking() {
                return banking;
            }

            public void setBanking(String banking) {
                this.banking = banking;
            }
        }

        public static class PayWayBean {
            /**
             * weixin_scan : {"title":"微信扫码支付","type":"scan","mode":"QRCode"}
             * alipay_scan : {"title":"支付宝扫码支付","type":"scan","mode":"QRCode"}
             * qq_scan : {"title":"QQ钱包扫码支付","type":"scan","mode":"QRCode"}
             * ABC : {"title":"农业银行","type":"banking","mode":"direct"}
             * ICBC : {"title":"工商银行","type":"banking","mode":"direct"}
             * CCB : {"title":"建设银行","type":"banking","mode":"direct"}
             * BCOM : {"title":"交通银行","type":"banking","mode":"direct"}
             * BOC : {"title":"中国银行","type":"banking","mode":"direct"}
             * CMB : {"title":"招商银行","type":"banking","mode":"direct"}
             * CMBC : {"title":"民生银行","type":"banking","mode":"direct"}
             * CEBB : {"title":"光大银行","type":"banking","mode":"direct"}
             * BOB : {"title":"北京银行","type":"banking","mode":"direct"}
             * SHB : {"title":"上海银行","type":"banking","mode":"direct"}
             * NBB : {"title":"宁波银行","type":"banking","mode":"direct"}
             * HXB : {"title":"华夏银行","type":"banking","mode":"direct"}
             * CIB : {"title":"兴业银行","type":"banking","mode":"direct"}
             * PSBC : {"title":"中国邮政银行","type":"banking","mode":"direct"}
             * SPABANK : {"title":"平安银行","type":"banking","mode":"direct"}
             * SPDB : {"title":"浦发银行","type":"banking","mode":"direct"}
             * ECITIC : {"title":"中信银行","type":"banking","mode":"direct"}
             * HZB : {"title":"杭州银行","type":"banking","mode":"direct"}
             * GDB : {"title":"广发银行","type":"banking","mode":"direct"}
             */

            private WeixinScanBean weixin_scan;
            private AlipayScanBean alipay_scan;
            private QqScanBean qq_scan;
            private ABCBean ABC;
            private ICBCBean ICBC;
            private CCBBean CCB;
            private BCOMBean BCOM;
            private BOCBean BOC;
            private CMBBean CMB;
            private CMBCBean CMBC;
            private CEBBBean CEBB;
            private BOBBean BOB;
            private SHBBean SHB;
            private NBBBean NBB;
            private HXBBean HXB;
            private CIBBean CIB;
            private PSBCBean PSBC;
            private SPABANKBean SPABANK;
            private SPDBBean SPDB;
            private ECITICBean ECITIC;
            private HZBBean HZB;
            private GDBBean GDB;

            public WeixinScanBean getWeixin_scan() {
                return weixin_scan;
            }

            public void setWeixin_scan(WeixinScanBean weixin_scan) {
                this.weixin_scan = weixin_scan;
            }

            public AlipayScanBean getAlipay_scan() {
                return alipay_scan;
            }

            public void setAlipay_scan(AlipayScanBean alipay_scan) {
                this.alipay_scan = alipay_scan;
            }

            public QqScanBean getQq_scan() {
                return qq_scan;
            }

            public void setQq_scan(QqScanBean qq_scan) {
                this.qq_scan = qq_scan;
            }

            public ABCBean getABC() {
                return ABC;
            }

            public void setABC(ABCBean ABC) {
                this.ABC = ABC;
            }

            public ICBCBean getICBC() {
                return ICBC;
            }

            public void setICBC(ICBCBean ICBC) {
                this.ICBC = ICBC;
            }

            public CCBBean getCCB() {
                return CCB;
            }

            public void setCCB(CCBBean CCB) {
                this.CCB = CCB;
            }

            public BCOMBean getBCOM() {
                return BCOM;
            }

            public void setBCOM(BCOMBean BCOM) {
                this.BCOM = BCOM;
            }

            public BOCBean getBOC() {
                return BOC;
            }

            public void setBOC(BOCBean BOC) {
                this.BOC = BOC;
            }

            public CMBBean getCMB() {
                return CMB;
            }

            public void setCMB(CMBBean CMB) {
                this.CMB = CMB;
            }

            public CMBCBean getCMBC() {
                return CMBC;
            }

            public void setCMBC(CMBCBean CMBC) {
                this.CMBC = CMBC;
            }

            public CEBBBean getCEBB() {
                return CEBB;
            }

            public void setCEBB(CEBBBean CEBB) {
                this.CEBB = CEBB;
            }

            public BOBBean getBOB() {
                return BOB;
            }

            public void setBOB(BOBBean BOB) {
                this.BOB = BOB;
            }

            public SHBBean getSHB() {
                return SHB;
            }

            public void setSHB(SHBBean SHB) {
                this.SHB = SHB;
            }

            public NBBBean getNBB() {
                return NBB;
            }

            public void setNBB(NBBBean NBB) {
                this.NBB = NBB;
            }

            public HXBBean getHXB() {
                return HXB;
            }

            public void setHXB(HXBBean HXB) {
                this.HXB = HXB;
            }

            public CIBBean getCIB() {
                return CIB;
            }

            public void setCIB(CIBBean CIB) {
                this.CIB = CIB;
            }

            public PSBCBean getPSBC() {
                return PSBC;
            }

            public void setPSBC(PSBCBean PSBC) {
                this.PSBC = PSBC;
            }

            public SPABANKBean getSPABANK() {
                return SPABANK;
            }

            public void setSPABANK(SPABANKBean SPABANK) {
                this.SPABANK = SPABANK;
            }

            public SPDBBean getSPDB() {
                return SPDB;
            }

            public void setSPDB(SPDBBean SPDB) {
                this.SPDB = SPDB;
            }

            public ECITICBean getECITIC() {
                return ECITIC;
            }

            public void setECITIC(ECITICBean ECITIC) {
                this.ECITIC = ECITIC;
            }

            public HZBBean getHZB() {
                return HZB;
            }

            public void setHZB(HZBBean HZB) {
                this.HZB = HZB;
            }

            public GDBBean getGDB() {
                return GDB;
            }

            public void setGDB(GDBBean GDB) {
                this.GDB = GDB;
            }

            public static class WeixinScanBean {
                /**
                 * title : 微信扫码支付
                 * type : scan
                 * mode : QRCode
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class AlipayScanBean {
                /**
                 * title : 支付宝扫码支付
                 * type : scan
                 * mode : QRCode
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class QqScanBean {
                /**
                 * title : QQ钱包扫码支付
                 * type : scan
                 * mode : QRCode
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class ABCBean {
                /**
                 * title : 农业银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class ICBCBean {
                /**
                 * title : 工商银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class CCBBean {
                /**
                 * title : 建设银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class BCOMBean {
                /**
                 * title : 交通银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class BOCBean {
                /**
                 * title : 中国银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class CMBBean {
                /**
                 * title : 招商银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class CMBCBean {
                /**
                 * title : 民生银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class CEBBBean {
                /**
                 * title : 光大银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class BOBBean {
                /**
                 * title : 北京银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class SHBBean {
                /**
                 * title : 上海银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class NBBBean {
                /**
                 * title : 宁波银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class HXBBean {
                /**
                 * title : 华夏银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class CIBBean {
                /**
                 * title : 兴业银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class PSBCBean {
                /**
                 * title : 中国邮政银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class SPABANKBean {
                /**
                 * title : 平安银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class SPDBBean {
                /**
                 * title : 浦发银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class ECITICBean {
                /**
                 * title : 中信银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class HZBBean {
                /**
                 * title : 杭州银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }

            public static class GDBBean {
                /**
                 * title : 广发银行
                 * type : banking
                 * mode : direct
                 */

                private String title;
                private String type;
                private String mode;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }
            }
        }
    }
}
