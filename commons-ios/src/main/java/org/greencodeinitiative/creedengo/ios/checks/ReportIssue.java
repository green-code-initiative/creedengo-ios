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
package org.greencodeinitiative.creedengo.ios.checks;

import javax.annotation.Nullable;
import java.util.Objects;

public class ReportIssue {

    private final String ruleId;
    private final String message;
    @Nullable
    private final String filePath;
    @Nullable
    private final Integer lineNumber;

    public ReportIssue(String ruleId, String message, @Nullable String filePath, @Nullable Integer lineNumber) {
        this.ruleId = ruleId;
        this.message = Objects.requireNonNull(message);
        this.filePath = filePath;
        this.lineNumber = lineNumber;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getMessage() {
        return message;
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportIssue that = (ReportIssue) o;
        return Objects.equals(lineNumber, that.lineNumber) &&
                Objects.equals(ruleId, that.ruleId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, message, filePath, lineNumber);
    }
}
