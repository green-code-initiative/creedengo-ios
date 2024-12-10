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

import io.ecocode.ios.swift.SwiftRuleCheck;
import io.ecocode.ios.swift.antlr.generated.Swift5Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sonar.check.Rule;

import static io.ecocode.ios.swift.checks.CheckHelper.isExpressionPresent;


/**
 * Check the use of "CLLocationManager#pausesLocationUpdatesAutomatically" and triggers when set to false.
 */
@Rule(key = "EC533")
public class LocationUpdatesDisabledCheck extends SwiftRuleCheck {
    private static final String DEFAULT_ISSUE_MESSAGE = "Do not disable location updates pause, unless absolutely necessary";
    public static final String LOCATION_UPDATES_DISABLE_EXPRESSION = ".pausesLocationUpdatesAutomatically=false";

    @Override
    public void apply(ParseTree tree) {
        if(isExpressionPresent(tree, LOCATION_UPDATES_DISABLE_EXPRESSION)){
            Swift5Parser.ExpressionContext id = (Swift5Parser.ExpressionContext) tree;
            this.recordIssue(id.getStart().getStartIndex(), DEFAULT_ISSUE_MESSAGE);
        }
    }
}
