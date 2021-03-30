package database;

import static database.Constants.Tables.*;

/**
 * Created by Alex on 11/03/2017.
 */
public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case CLIENT_INFO:
                return "CREATE TABLE IF NOT EXISTS clientInfo (" +
                        "  id int NOT NULL AUTO_INCREMENT," +
                        "  name varchar(500) NOT NULL," +
                        "  cnp varchar(13) NOT NULL," +
                        "  address varchar(500) DEFAULT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id)" +
                        ")";
            case CLIENT_ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS clientAccount (" +
                        "  id int NOT NULL AUTO_INCREMENT," +
                        "  idClient int NOT NULL," +
                        "  type varchar(100) NOT NULL," +
                        "  moneyAmount int(11) DEFAULT NULL," +
                        "`creationDate` DATETIME NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE," +
                        "  INDEX `id_idx` (`idclient` ASC) VISIBLE," +
                        "  CONSTRAINT `id`" +
                        "    FOREIGN KEY (`idclient`)" +
                        "    REFERENCES `clientinfo` (`id`)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case ACTIVITY:
                return "CREATE TABLE IF NOT EXISTS activity (" +
                        "  id int NOT NULL AUTO_INCREMENT," +
                        "  idUser int NOT NULL," +
                        "  action VARCHAR(200) NOT NULL,"+
                        "  date DATETIME NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE," +
                        "  INDEX `idUser_idx` (`idUser` ASC) VISIBLE," +
                        "  CONSTRAINT `idUser`" +
                        "    FOREIGN KEY (`idUser`)" +
                        "    REFERENCES `user` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case USER:
                return "CREATE TABLE IF NOT EXISTS user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";

            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case USER_ROLE:
                return "\tCREATE TABLE IF NOT EXISTS user_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  user_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX user_id_idx (user_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  CONSTRAINT user_fkid" +
                        "    FOREIGN KEY (user_id)" +
                        "    REFERENCES user (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT role_fkid" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            default:
                return "";

        }
    }

}
