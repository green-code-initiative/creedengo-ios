/*
 * ecoCode iOS plugin - Help the earth, adopt this green plugin for your applications
 * Copyright © 2023 green-code-initiative (https://www.ecocode.io/)
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

package io.ecocode.ios.swift.checks.sobriety;

import io.ecocode.ios.swift.checks.CheckTestHelper;
import org.assertj.core.api.ObjectAssert;
import org.junit.Test;
import org.sonar.api.batch.fs.TextPointer;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.batch.sensor.issue.IssueLocation;
import org.sonar.api.rule.RuleKey;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationLeakCheckTest {

    private static final String TEST_CASE_MISSING_RELEASE_CALL = "checks/sobriety/LocationLeakCheck_missing_release_trigger.swift";
    private static final String TEST_CASE_COMPLIANT = "checks/sobriety/LocationLeakCheck_compliant_no_trigger.swift";
    @Test
    public void locationLeakCheck_missing_release_trigger(){
        SensorContextTester context = CheckTestHelper.analyzeTestFile(TEST_CASE_MISSING_RELEASE_CALL);
        ObjectAssert<Issue> issue = assertThat(context.allIssues()).hasSize(1)
                .first();
        issue.extracting(Issue::ruleKey).extracting(RuleKey::rule).isEqualTo("EC513");
        issue.extracting(Issue::ruleKey).extracting(RuleKey::repository)
                .isEqualTo("ecoCode-swift");
        issue.extracting(Issue::primaryLocation)
                .extracting(IssueLocation::textRange)
                .extracting(TextRange::start)
                .extracting(TextPointer::line)
                .isEqualTo(11);
    }

    @Test
    public void locationLeakCheck_compliant_no_trigger(){
        SensorContextTester context = CheckTestHelper.analyzeTestFile(TEST_CASE_COMPLIANT);
        assertThat(context.allIssues()).isEmpty();
    }
}
