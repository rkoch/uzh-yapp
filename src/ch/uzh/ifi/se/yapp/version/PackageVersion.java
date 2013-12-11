/*
 * The MIT License (MIT)
 * Copyright © 2013 different authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ch.uzh.ifi.se.yapp.version;

/**
 * Contains version information for the package TERCO Client Hub.
 *
 * @formatter:off
 *
 * @author auto-generated
 * **** NEVER CHANGE THE FOLLOWING LINE ****
 * @version 0.1.0
 */
public final class PackageVersion {

    /** The name of the package (YAPP Platform). */
    private static final String PACKAGE_NAME = "YAPP Platform";

    /** The version of the package (0.1.0). */
    private static final String VERSION     = "0.1.0";

    /** The build date of the package (09.10.2013 11:17:03). */
    private static final String BUILD_DATE   = "09.10.2013 11:17:03";


    /**
     * Gets the package name.
     *
     * @return String The package name.
     */
    public static String getPackageName() {
        return PACKAGE_NAME;
    }

    /**
     * Gets the package version.
     *
     * @return String The package version.
     */
    public static String getPackageVersion() {
        return PACKAGE_NAME + " v" + VERSION + " [" + BUILD_DATE + "]";
    }

    /**
     * Gets the version.
     *
     * @return String The version.
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Gets the build date.
     *
     * @return String The build date.
     */
    public static String getBuildDate() {
        return BUILD_DATE;
    }

}
