package com.transplantados.shared;

public class Routes {

    public static final String BASE_ROUTE = "/api";

    public static class Authentication {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/auth";

        public static final String LOGIN = "/login";

        public static final String LOGOUT = "/logout";

        public static final String REFRESH = "/refresh";

    }

    public static class Patient {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/patient";

        public static final String GET_ALL = "";

        public static final String CREATE = "";

        public static final String REMOVE = "/{patientId}";

        public static final String UPDATE = "";

        public static final String CHANGE_PASSWORD = "/change-password";

    }

    public static class Transplant {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/transplant";

        public static final String LOGBOOK = "/logbook";

        public static final String CREATE = "";

        public static final String REMOVE = "/{transplantId}";

        public static final String UPDATE = "";

        public static final String GET_ALL = "";

    }

    public static class Variable {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/variable";

        public static final String GET_ALL = "";

        public static final String CREATE = "";

        public static final String REMOVE = "/{variableId}";

    }

}
