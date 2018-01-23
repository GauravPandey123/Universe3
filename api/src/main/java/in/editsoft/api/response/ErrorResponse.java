package in.editsoft.api.response;

/**
 * Created by jeevan on 25/05/17.
 */

public class ErrorResponse {

    /**
     * admin : Please verify your account
     * mobile : Please verify your account
     * web : Please verify your account
     */

    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        private String admin;
        private String mobile;
        private String web;

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }
    }
}
