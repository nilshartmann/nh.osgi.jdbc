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

import junit.framework.TestCase;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractDataSourceTest extends TestCase {

  protected final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();

    // dump installed bundles for debugging
    dumpBundles();
  }

  protected DataSourceFactory getDataSourceFactory(String osgiJdbcDriverClass) throws Exception {
    // Get service reference to JdbcDataSource
    ServiceReference[] serviceReferences = context.getServiceReferences(DataSourceFactory.class.getName(), "("
        + DataSourceFactory.OSGI_JDBC_DRIVER_CLASS + "=" + osgiJdbcDriverClass + ")");
    assertNotNull(serviceReferences);
    assertEquals(1, serviceReferences.length);

    // get the DataSourceFactory-service
    ServiceReference serviceReference = serviceReferences[0];
    DataSourceFactory dataSourceFactory = (DataSourceFactory) context.getService(serviceReference);
    assertNotNull(dataSourceFactory);

    return dataSourceFactory;
  }

  protected void dumpBundles() throws Exception {
    Bundle[] bundles = context.getBundles();

    System.out.println("Installed bundles: ");
    for (Bundle bundle : bundles) {
      System.out.println("Bundle: " + bundle.getSymbolicName() + " (" + getStateDescription(bundle.getState()) + ")");
      ServiceReference[] registeredServices = bundle.getRegisteredServices();
      if (registeredServices != null) {
        for (ServiceReference serviceReference : registeredServices) {
          System.out.printf("  Service: %s%n", serviceReference.toString());
        }
      }
    }
  }

  private String getStateDescription(int state) {

    switch (state) {
    case 1:
      return "UNINSTALLED";
    case 2:
      return "INSTALLED";
    case 4:
      return "RESOLVED";
    case 8:
      return "STARTING";
    case 16:
      return "STOPPING";
    case 32:
      return "ACTIVE";
    default:
      return "UNDEFINED";
    }
  }

}
