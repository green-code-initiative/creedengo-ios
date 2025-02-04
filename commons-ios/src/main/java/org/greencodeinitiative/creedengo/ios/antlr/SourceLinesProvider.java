/*
 * Creedengo iOS plugin - Help the earth, adopt this green plugin for your applications
 * Copyright © 2024 green-code-initiative (https://green-code-initiative.org/)
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
package org.greencodeinitiative.creedengo.ios.antlr;

import org.apache.commons.io.input.BOMInputStream;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SourceLinesProvider {
    private static final Logger LOGGER = Loggers.get(SourceLinesProvider.class);

    public SourceLine[] getLines(final InputStream inputStream, final Charset charset) {
        if (inputStream == null) {
            return new SourceLine[0];
        }
        final List<SourceLine> sourceLines = new ArrayList<>();


        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(BOMInputStream.builder().setInputStream(inputStream).setInclude(false).setCharset(charset).get()))) {
            int totalLines = 1;
            int global = 0;
            int count = 0;

            int currentChar;
            while ((currentChar = bufferedReader.read()) != -1) {
                global++;
                count++;
                if (currentChar == 10) {
                    sourceLines.add(new SourceLine(totalLines, count, global - count, global));
                    totalLines++;
                    count = 0;
                }

            }
            sourceLines.add(new SourceLine(totalLines, count, global - count, global));
        } catch (final Exception e) {
            LOGGER.warn("Error occurred reading file", e);
        }

        return sourceLines.toArray(new SourceLine[0]);
    }

}
