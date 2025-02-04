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
package org.greencodeinitiative.creedengo.ios.swift.checks.sobriety;

import org.greencodeinitiative.creedengo.ios.swift.checks.CheckTestHelper;
import org.junit.Test;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.batch.sensor.issue.IssueLocation;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AudioRecorderLeakCheckTest {
    private static final String TEST_AUDIO_CASE_MISSING_RELEASE_CALL = "checks/sobriety/AudioRecorderLeak_trigger.swift";
    private static final String TEST_AUDIO_CASE_COMPLIANT = "checks/sobriety/AudioRecorderLeak_no_trigger.swift";

    private static final String TESTED_RULE_ID = "GCI515";
    private static final String TEST_REPOSITORY = "creedengo-swift";

    @Test
    public void audioLeakCheck_missing_release_trigger() {
        SensorContextTester context = CheckTestHelper.analyzeTestFile(TEST_AUDIO_CASE_MISSING_RELEASE_CALL);
        assertThat(context.allIssues()).hasSize(1);
        Optional<Issue> issue = context.allIssues().stream().findFirst();
        issue.ifPresent(i -> {
            assertThat(i.ruleKey().rule()).isEqualTo(TESTED_RULE_ID);
            assertThat(i.ruleKey().repository()).isEqualTo(TEST_REPOSITORY);
            IssueLocation location = i.primaryLocation();
            assertThat(location.textRange().start().line()).isEqualTo(15);
        });
    }

    @Test
    public void audioLeakCheck_no_trigger() {
        SensorContextTester context = CheckTestHelper.analyzeTestFile(TEST_AUDIO_CASE_COMPLIANT);
        assertThat(context.allIssues()).isEmpty();
    }
}