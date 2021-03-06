package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;


public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank_sd";
        public static final String PRODUCTION = "bank_sd";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String CLIENT_INFO = "client_info";
        public static final String CLIENT_ACCOUNT = "client_account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String ACTIVITY= "activity";
        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                CLIENT_INFO,CLIENT_ACCOUNT,ACTIVITY};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_CLIENTINFO = "create_client_info";
        public static final String UPDATE_CLIENTINFO = "update_client_info";

        public static final String CREATE_CLIENTACCOUNT = "create_client_account";
        public static final String DELETE_CLIENTACCOUNT = "delete_client_account";
        public static final String UPDATE_CLIENTACCOUNT = "update_client_account";


        public static final String[] RIGHTS = new String[]{CREATE_CLIENTINFO, UPDATE_CLIENTINFO,
                CREATE_CLIENTACCOUNT, DELETE_CLIENTACCOUNT, UPDATE_CLIENTACCOUNT, CREATE_USER, DELETE_USER, UPDATE_USER};
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENTACCOUNT, DELETE_CLIENTACCOUNT, UPDATE_CLIENTACCOUNT,
                CREATE_CLIENTINFO, UPDATE_CLIENTINFO));


        return ROLES_RIGHTS;
    }

}
