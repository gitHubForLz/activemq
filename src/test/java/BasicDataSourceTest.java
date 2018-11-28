import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class BasicDataSourceTest {
    static BasicDataSource basicDataSource = new BasicDataSource();
    static {   basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/activemq?relaxAutoCommit=true");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("rootroot");
        basicDataSource.setPoolPreparedStatements(Boolean.TRUE);
        basicDataSource.setMaxTotal(10);
    }

    public static void main(String[] args) {
        System.out.println( basicDataSource);
    }

}
