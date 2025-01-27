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

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.util.List;

public class ReportIssueRecorder {

    private static final Logger LOGGER = Loggers.get(ReportIssueRecorder.class);
    private final SensorContext sensorContext;

    public ReportIssueRecorder(SensorContext sensorContext) {
        this.sensorContext = sensorContext;
    }

    public void recordIssues(List<ReportIssue> issues, String repository) {

        final FileSystem fs = sensorContext.fileSystem();
        final FilePredicates predicates = fs.predicates();
        FilePredicate mainPredicate = predicates.hasType(InputFile.Type.MAIN);

        // Record issues
        for (ReportIssue issue : issues) {

            // The issue to be record
            NewIssue sonarIssue = sensorContext
                    .newIssue()
                    .forRule(RuleKey.of(repository, issue.getRuleId()));
            // The location of the issue to be record
            NewIssueLocation sonarIssueLocation = sonarIssue.newLocation()
                    .message(issue.getMessage());

            final String filePath = issue.getFilePath();
            // We have a file location associated with the generated issue
            if (filePath != null) {
                final FilePredicate relativePathPredicate = predicates.hasRelativePath(filePath);
                final FilePredicate absolutePathPredicate = predicates.hasAbsolutePath(filePath);
                final FilePredicate pathPredicate = predicates.or(absolutePathPredicate, relativePathPredicate);
                final FilePredicate filePredicate = predicates.and(pathPredicate, mainPredicate);
                InputFile inputFile = fs.inputFile(filePredicate);
                // Making sure the file is part of SonarQube FS
                if (fs.hasFiles(filePredicate) && inputFile != null) {
                    // Adding the location of the file
                    sonarIssueLocation.on(inputFile);
                    final Integer lineNumber = issue.getLineNumber();
                    // We have a line number for that file
                    if (lineNumber != null) {
                        // Adding the line number
                        sonarIssueLocation.at(inputFile.selectLine(lineNumber));
                    }
                    // Associating the location to the issue and saving it.
                    sonarIssue.at(sonarIssueLocation).save();
                } else {
                    LOGGER.warn("File not included in SonarQube sources {}", filePath);
                }
            } else {
                // No location specified, so a global issue.
                // Setting the project as location.
                sonarIssueLocation.on(sensorContext.project());
                // Associating the location to the issue and saving it.
                sonarIssue.at(sonarIssueLocation).save();
            }
        }
    }
}
