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

import org.junit.Test;
import java.nio.charset.Charset;
import static org.assertj.core.api.Assertions.assertThat;

public class SourceLinesProviderTest {

    private static final String MAIN_SRC = "/antlr/main.swift";

    @Test
    public void getLines() {


        SourceLinesProvider provider = new SourceLinesProvider();
        SourceLine[] lines = provider.getLines(this.getClass().getResourceAsStream(MAIN_SRC), Charset.defaultCharset());
        assertThat(lines).hasSize(6);
    }
}
