/*
 * Cognifide AET :: Job Common
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cognifide.aet.job.common.comparators.permormance.clientside;

import com.cognifide.aet.job.common.comparators.permormance.clientside.parser.ClientSidePerformanceComparatorResultData;
import com.cognifide.aet.job.common.comparators.permormance.clientside.report.ClientSidePerformanceReport;
import com.cognifide.aet.vs.ComparatorProperties;
import com.cognifide.aet.vs.ComparatorResult;

public class ClientSidePerformanceComparatorResult extends ComparatorResult {

	private static final long serialVersionUID = -1499779646296015656L;

	private final ClientSidePerformanceReport report;

	public ClientSidePerformanceComparatorResult(ClientSidePerformanceComparatorResultData comparatorResultData,
												 ComparatorProperties comparatorProperties) {
		super(comparatorResultData.getResult(), comparatorProperties, false);
		this.report = comparatorResultData.getReport();
	}

	public ClientSidePerformanceReport getReport() {
		return report;
	}
}