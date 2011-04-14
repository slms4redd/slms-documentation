/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://code.google.com/p/geobatch/
 *  Copyright (C) 2007-2008-2009 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.egeos.wind;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.catalog.impl.BaseService;
import it.geosolutions.geobatch.flow.event.action.ActionService;
import it.geosolutions.geobatch.metocs.commons.MetocActionConfiguration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Public class to generate E-GEOS::SAR Wind Derived Products Services
 * 
 */
public class SARWindGeneratorService extends BaseService implements ActionService<FileSystemEvent, MetocActionConfiguration> {

    public SARWindGeneratorService(String id, String name, String description) {
        super(id, name, description);
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(SARWindGeneratorService.class.toString());

    /**
     * Action creator
     * 
     * @param configuration
     *            The data base action configuration
     * @return new SARWindFileConfiguratorAction()
     */
    public SARWindAction createAction(MetocActionConfiguration configuration) {
        try {
            return new SARWindAction(configuration);
        } catch (IOException e) {
            if (LOGGER.isInfoEnabled())
                LOGGER.info(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public boolean canCreateAction(MetocActionConfiguration configuration) {
        return true;
    }

//    @Override
//    public boolean canCreateAction(MetocActionConfiguration configuration) {
//        final boolean superRetVal = super.canCreateAction(configuration);
//        if (configuration.getServiceId()==null){
//            return true; //try to use 
//        }
//        else if (this.getClass().getName().endsWith(configuration.getServiceID())){
//            return true;
//        }
//        else
//            return false;
//    }

}