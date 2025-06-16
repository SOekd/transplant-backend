package com.transplantados.shared;

public class Routes {

    public static final String BASE_ROUTE = "/api";

    public static class Authentication {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/auth";

        public static final String LOGIN = "/login";

    }

    public static class Patient {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/patient";

        public static final String GET_ALL = "";

        public static final String CREATE = "";

        public static final String REMOVE = "/{patientId}";

        public static final String UPDATE = "";

        public static final String CHANGE_PASSWORD = "/change-password";

        public static final String MEDICATION_BASE_ROUTE = BASE_ROUTE + "/{patientId}/medication";

        public static final String MEDICATION_GET_ALL = "";

        public static final String MEDICATION_CREATE = "";

        public static final String MEDICATION_REMOVE = "/{medicationId}";

        public static final String MEDICATION_CONSUME = "/{medicationId}/consume";

        public static final String UPDATE_TRANSPLANTS = "/{patientId}/transplants";

    }

    public static class Transplant {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/transplant";

        public static final String LOGBOOK = "/logbook";

        public static final String GET_ALL = "";

    }

    public static class Variable {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/variable";

        public static final String GET_ALL = "";

    }

    public static class Alert {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/alert";

        public static final String GET_ALL = "";

        public static final String CREATE = "";

        public static final String CONFIRM = "/confirm/{alertId}";

        public static final String REMOVE = "/{alertRuleId}";

        public static final String GET_ALL_RULES = "/rules";

    }

    public static class Notification {

        public static final String BASE_ROUTE = Routes.BASE_ROUTE + "/notification";

        public static final String GET_ALL = "";

        public static final String MARK_SENT = "/{notificationId}";

    }

}
