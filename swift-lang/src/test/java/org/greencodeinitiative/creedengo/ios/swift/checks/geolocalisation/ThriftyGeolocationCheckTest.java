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
package org.greencodeinitiative.creedengo.ios.swift.checks.geolocalisation;

import org.greencodeinitiative.creedengo.ios.swift.checks.CheckTestHelper;
import org.junit.Test;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.batch.sensor.issue.IssueLocation;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ThriftyGeolocationCheckTest {
    @Test
    public void Geo_trigger() {
        SensorContextTester context = CheckTestHelper.analyzeTestFile("checks/geolocalisation/Geolocalisation_trigger.swift");
        assertThat(context.allIssues()).hasSize(1);
        Optional<Issue> issue = context.allIssues().stream().findFirst();
        issue.ifPresent(i -> {
            assertThat(i.ruleKey().rule()).isEqualTo("GCI524");

            assertThat(i.ruleKey().repository()).isEqualTo("creedengo-swift");
            IssueLocation location = i.primaryLocation();
            assertThat(location.textRange().start().line()).isEqualTo(1);
        });
    }

    @Test
    public void Geo_no_trigger() {
        SensorContextTester context = CheckTestHelper.analyzeTestFile("checks/geolocalisation/Geolocalisation_no_trigger.swift");
        assertThat(context.allIssues()).isEmpty();
    }

    @Test
    public void Geo_no_trigger_on_desired_accuracy() {
        SensorContextTester context = CheckTestHelper.analyzeTestFile("checks/geolocalisation/Geolocalisation_no_trigger_desired_accuracy.swift");
        assertThat(context.allIssues()).isEmpty();
    }
}
