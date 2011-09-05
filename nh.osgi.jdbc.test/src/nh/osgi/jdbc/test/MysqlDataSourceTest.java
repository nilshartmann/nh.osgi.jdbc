/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Nils Hartmann - initial API and implementation
 ******************************************************************************/
package nh.osgi.jdbc.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class MysqlDataSourceTest extends AbstractDataSourceTest {

  public void test_createDataSource() throws Exception {

    // obtain the DataSource Factory from Service Registry
    DataSourceFactory dataSourceFactory = getDataSourceFactory("com.mysql.jdbc.MysqlDataSource");

    // obtain a data source
    DataSource dataSource = dataSourceFactory.createDataSource(getDataSourceProperties());
    assertNotNull(dataSource);

    // execute a query to make sure retrieved datasource works
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.execute("select version()");
  }

  protected Properties getDataSourceProperties() {

    // SQL to create database:
    // create database jdbc_osgi;
    // GRANT ALL PRIVILEGES ON jdbc_osgi.* TO 'monty'@'%' IDENTIFIED BY 'jdbcpasswd' WITH GRANT OPTION;

    Properties props = new Properties();
    // (assume mysql runs on host 'mysql.nils')
    props.put(DataSourceFactory.JDBC_URL, "jdbc:mysql://mysql.nils/jdbc_osgi");
    props.put(DataSourceFactory.JDBC_USER, "monty");
    props.put(DataSourceFactory.JDBC_PASSWORD, "jdbcpasswd");

    return props;
  }

}
