/**
 * Automated Exploratory Tests
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cognifide.aet.job.common.datafilters.statuscodesfilter;

import com.cognifide.aet.job.api.datafilter.AbstractDataModifierJob;
import com.cognifide.aet.job.api.exceptions.ParametersException;
import com.cognifide.aet.job.api.exceptions.ProcessingException;
import com.cognifide.aet.job.common.collectors.statuscodes.StatusCode;
import com.cognifide.aet.job.common.collectors.statuscodes.StatusCodesCollectorResult;
import com.cognifide.aet.job.common.utils.ParamsHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

abstract class StatusCodesFilter extends AbstractDataModifierJob<StatusCodesCollectorResult> {

  private static final String PARAM_URL = "url";

  private static final String PARAM_PATTERN = "pattern";

  private Pattern regexPattern;

  private String patternParam;

  private String urlParam;

  @Override
  public void setParameters(Map<String, String> params) throws ParametersException {
    regexPattern = ParamsHelper.getPatternFromPatternParameterOrPlainText(PARAM_PATTERN, PARAM_URL, params);
    ParamsHelper.atLeastOneIsProvided(regexPattern);
    patternParam = ParamsHelper.getParamAsString(PARAM_PATTERN, params); //just for logging
    urlParam = ParamsHelper.getParamAsString(PARAM_URL, params); //just for logging

  }

  @Override
  public StatusCodesCollectorResult modifyData(StatusCodesCollectorResult data) throws ProcessingException {
    for (StatusCode statusCode : data.getStatusCodes()) {
      if (regexPattern != null && matchPattern(statusCode.getUrl())) {
        statusCode.setExcluded(removeIfMatches());
      }
    }
    return data;
  }

  protected boolean matchUrl(String paramValue, String statusCodeUrl) {
    return StringUtils.endsWithIgnoreCase(statusCodeUrl, paramValue);

  }

  protected boolean matchPattern(String statusCodeUrl) {
    return regexPattern.matcher(statusCodeUrl).matches();
  }

  @Override
  public String getInfo() {
    return getName() + " DataModifier with parameters: " + PARAM_URL + ": " + urlParam + " " + PARAM_PATTERN
        + ": " + patternParam;
  }

  protected abstract String getName();

  protected abstract boolean removeIfMatches();

  private boolean provided(String paramValue) {
    return !StringUtils.isEmpty(paramValue);
  }
}
