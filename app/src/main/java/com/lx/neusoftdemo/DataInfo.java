package com.lx.neusoftdemo;

import java.util.List;

/**
 * Created by lixiang on 2017/2/28.
 */

public class DataInfo {

    /**
     * success : true
     * reason :
     * data : [{"time":"2016-04-26 20:36:01","context":"客户 签收人 :本人 已签收 感谢使用圆通速递，期待再次为您服务"},{"time":"2016-04-26 19:56:01","context":"【广西省梧州市蝶山区新兴分部公司】 派件人 :丘萍. 派件中 派件员电话18977485591"},{"time":"2016-04-26 15:00:16","context":"【广西省梧州市蝶山区新兴分部公司】 已收入"},{"time":"2016-04-26 14:08:04","context":"【广西省梧州市公司】 已发出 下一站 【广西省梧州市蝶山区新兴分部公司】"},{"time":"2016-04-26 06:30:10","context":"【玉林中转公司】 已发出 下一站 【广西省梧州市公司】"},{"time":"2016-04-25 00:29:03","context":"【桂林转运中心】 已发出 下一站 【玉林中转公司】"},{"time":"2016-04-25 00:28:08","context":"【桂林转运中心】 已收入"},{"time":"2016-04-23 22:29:32","context":"【上海转运中心】 已发出 下一站 【南宁转运中心】"},{"time":"2016-04-23 22:09:05","context":"【上海转运中心】 已收入"},{"time":"2016-04-23 20:35:35","context":"【上海市嘉定区徐行公司】 已打包"},{"time":"2016-04-23 20:35:26","context":"【上海市嘉定区徐行公司】 已发出 下一站 【上海转运中心】"},{"time":"2016-04-23 14:58:57","context":"【上海市嘉定区徐行公司】  已收件"}]
     * status : 6
     * exname : yuantong
     * ico : http://www.kuaidi.com/data/upload/201407/yt_logo.gif
     * phone : 021-69777888
     * url : http://www.yto.net.cn
     * nu : 806820160474
     * company : 圆通速递
     */

    private boolean success;
    private String reason;
    private int status;
    private String exname;
    private String ico;
    private String phone;
    private String url;
    private String nu;
    private String company;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExname() {
        return exname;
    }

    public void setExname(String exname) {
        this.exname = exname;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2016-04-26 20:36:01
         * context : 客户 签收人 :本人 已签收 感谢使用圆通速递，期待再次为您服务
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
