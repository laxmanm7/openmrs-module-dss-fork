package org.openmrs.module.dss;

import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.Activator;
import org.openmrs.module.dss.util.Util;

/**
 * Purpose: Checks that module specific global properties have been set
 *
 * @author Tammy Dugan
 *
 */
public class DssActivator implements Activator {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @see org.openmrs.module.Activator#startup()
     */
    @Override
    public void startup() {
        this.log.info("Starting Dss Module");

        //check that all the required global properties are set
        checkGlobalProperties();
    }

    private void checkGlobalProperties() {
        try {
            AdministrationService adminService = Context.getAdministrationService();
            Context.authenticate(adminService
                    .getGlobalProperty("scheduler.username"), adminService
                    .getGlobalProperty("scheduler.password"));
            Iterator<GlobalProperty> properties = adminService
                    .getAllGlobalProperties().iterator();
            GlobalProperty currProperty;
            String currValue;
            String currName;

            while (properties.hasNext()) {
                currProperty = properties.next();
                currName = currProperty.getProperty();
                if (currName.startsWith("dss")) {
                    currValue = currProperty.getPropertyValue();
                    if (currValue == null || currValue.length() == 0) {
                        this.log.error("You must set a value for global property: "
                                + currName);
                    }
                }
            }
        } catch (ContextAuthenticationException e) {
            this.log.error("Error checking global properties for dss module");
            this.log.error(e.getMessage());
            this.log.error(Util.getStackTrace(e));

        }
    }

    /**
     * @see org.openmrs.module.Activator#shutdown()
     */
    @Override
    public void shutdown() {
        this.log.info("Shutting down Dss Module");
    }
}
