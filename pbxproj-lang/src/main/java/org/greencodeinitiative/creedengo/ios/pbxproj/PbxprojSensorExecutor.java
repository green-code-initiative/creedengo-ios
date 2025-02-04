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
package org.greencodeinitiative.creedengo.ios.pbxproj;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

import org.greencodeinitiative.creedengo.ios.pbxproj.antlr.PbxprojAntlrContext;
import org.greencodeinitiative.creedengo.ios.checks.RuleLoader;
import org.greencodeinitiative.creedengo.ios.antlr.ParseTreeAnalyzerFactory;
import org.greencodeinitiative.creedengo.ios.antlr.ParseTreeAnalyzer;

public class PbxprojSensorExecutor {
    private final RuleLoader<PbxprojRuleCheck> ruleLoader;
    private final ParseTreeAnalyzerFactory analyzerFactory;

    public PbxprojSensorExecutor(RuleLoader<PbxprojRuleCheck> ruleLoader, ParseTreeAnalyzerFactory analyzerFactory) {
        this.ruleLoader = ruleLoader;
        this.analyzerFactory = analyzerFactory;
    }

    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor
                .onlyOnLanguage(PbxprojLanguage.KEY)
                .name("Creedengo pbxproj Sensor")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    public void execute(SensorContext sensorContext) {
        final PbxprojAntlrContext antlrContext = new PbxprojAntlrContext();
        ParseTreeAnalyzer analyzer = analyzerFactory.create(PbxprojLanguage.KEY, InputFile.Type.MAIN, antlrContext, sensorContext);
        analyzer.analyze(new CreedengoPbxprojVisitor(ruleLoader));
    }
}