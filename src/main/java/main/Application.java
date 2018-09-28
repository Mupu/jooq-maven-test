package main;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.jooq.generated.Tables.*;

public class Application {
    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
//        System.setProperty("jdbc.user", "mupu");
//        System.setProperty("jdbc.password", "mupu");
//        System.setProperty("jdbc.url", "jdbc:mysql://192.168.2.119:3306/meetnow?serverTimezone=UTC");
//        System.setProperty("jdbc.driver", "com.mysql.cj.jdbc.Driver");
        String user = System.getProperty("jdbc.user");
        String password = System.getProperty("jdbc.password");
        String url = System.getProperty("jdbc.url");
        String driver = System.getProperty("jdbc.driver");


        Class.forName(driver).newInstance();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            Result<Record2<String, String>> result = dslContext.select(PERSON.NACHNAME, PERSON.VORNAME).from(PERSON).orderBy(PERSON.VORNAME.asc()).fetch();

            for (Record2 r : result) {
                String firstName = r.getValue(PERSON.VORNAME);
                String lastName = r.getValue(PERSON.NACHNAME);

                System.out.println(firstName + " : " + lastName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
