package com.haomai.ywj.yobook.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25.
 */

public class TreeBean {
        /**
         * result : Y address :
         * [{"name":"北京","custId":"010","area":["东城区","西城区","崇文区"
         * ,"宣武区","延庆县"]},{"name"
         * :"上海","custId":"021","area":["黄浦区","卢湾区","徐汇区","长宁区","静安区","其他"]}]
         */
        private String result;
        /**
         * name : 北京 custId : 010 area : ["东城区","西城区","崇文区","宣武区","延庆县"]
         */

        private List<AddressEntity> address;

        public void setResult(String result) {
            this.result = result;
        }

        public void setAddress(List<AddressEntity> address) {
            this.address = address;
        }

        public String getResult() {
            return result;
        }

        public List<AddressEntity> getAddress() {
            return address;
        }

        public static class AddressEntity {
            private String name;
            private String custId;
            private List<String> area;

            public void setName(String name) {
                this.name = name;
            }

            public void setCustId(String custId) {
                this.custId = custId;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }

            public String getName() {
                return name;
            }

            public String getCustId() {
                return custId;
            }

            public List<String> getArea() {
                return area;
            }
        }


}
