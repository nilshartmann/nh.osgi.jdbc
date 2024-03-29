/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
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
public class H2DataSourceTest extends AbstractDataSourceTest {

  public void test_createDataSource() throws Exception {

    // obtain the H2 DataSource Factory from Service Registry
    DataSourceFactory dataSourceFactory = getDataSourceFactory("org.h2.jdbcx.JdbcDataSource");

    // obtain a data source
    DataSource dataSource = dataSourceFactory.createDataSource(getDataSourceProperties());
    assertNotNull(dataSource);

    // execute a query to make sure retrieved datasource works
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.execute("CREATE TABLE osgi_test(primary_key VARCHAR(256))");
  }

  protected Properties getDataSourceProperties() {

    // Properties for an in-memory Database
    Properties props = new Properties();
    props.put(DataSourceFactory.JDBC_URL, "jdbc:h2:mem:db1");
    props.put(DataSourceFactory.JDBC_USER, "sa");
    props.put(DataSourceFactory.JDBC_PASSWORD, "");

    return props;
  }

}
