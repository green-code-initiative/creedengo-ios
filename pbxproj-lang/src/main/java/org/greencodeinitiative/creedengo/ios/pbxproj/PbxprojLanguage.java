/*
 * Creedengo iOS plugin - Help the earth, adopt this green plugin for your applications
 * Copyright © 2023 green-code-initiative (https://green-code-initiative.org/)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.greencodeinitiative.creedengo.ios.pbxproj;

import org.sonar.api.resources.AbstractLanguage;

public class PbxprojLanguage extends AbstractLanguage {

    public static final String KEY = "pbxproj";

    public static final String PROFILE_PATH = "creedengo_pbxproj_profile.json";

    public PbxprojLanguage() {
        super(KEY, "Pbxproj");
    }
    @Override
    public String[] getFileSuffixes() {
        return new String[] { "pbxproj" };
    }
}