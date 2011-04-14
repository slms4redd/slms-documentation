/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.codehaus.org/
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

package it.geosolutions.geobatch.catalog.file;

import it.geosolutions.geobatch.catalog.impl.BaseCatalog;

/**
 * A Catalog based on an xml marshalled file.
 * 
 * @author Simone Giannecchini, GeoSolutions
 */
@SuppressWarnings("unchecked")
public class FileBasedCatalogImpl extends BaseCatalog implements FileBaseCatalog {

    public FileBasedCatalogImpl(String id, String name, String description) {
        super(id, name, description);
    }

    /**
     * baseDirectory: represents the base directory where the xml files are located. The workingDirecotry will be relative to this base directory unless an absolute path has been specified.
     */
    private String baseDirectory;

    /**
     * Getter for the baseDirectory.
     * @uml.property  name="baseDirectory"
     */
    public String getBaseDirectory() {
        return this.baseDirectory;
    }

    /**
     * Setter for the baseDirectory.
     * @uml.property  name="baseDirectory"
     */
    public void setBaseDirectory(final String baseDirectory) {
        this.baseDirectory = baseDirectory;

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + baseDirectory + "]";
    }

}
