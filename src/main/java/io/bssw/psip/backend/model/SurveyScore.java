/******************************************************************************
*
* Copyright 2020-, UT-Battelle, LLC. All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
*  o Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
*    
*  o Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
*    
*  o Neither the name of the copyright holder nor the names of its
*    contributors may be used to endorse or promote products derived from
*    this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
* FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*******************************************************************************/
package io.bssw.psip.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class SurveyScore {
    private String timestamp = "";
    private String version = "";
    @JsonIgnore
    private String friendlyTimestamp = "";
    private List<CategoryScore> categoryScores = new ArrayList<>();

    public String getTimestamp() {
        return timestamp;
    }
    @JsonIgnore
    public String getFriendlyTimestamp() {
        //TODO: see if it's possible to pass in a locale, everything is in UTC
        /*NOTE: assuming that the formatting of the date and time don't change
            these hard-coded numbers should work.
         */
        if (friendlyTimestamp.equals("")) {
            String dateStamp = timestamp.substring(0, 10);
            String timeStamp = timestamp.substring(11, 19);
            LocalDate date = LocalDate.parse(dateStamp);
            String newDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            LocalTime time = LocalTime.parse(timeStamp);
            String newTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            return newDate + " at " + newTime;
        }
        return friendlyTimestamp;
    }
    @JsonIgnore
    public void setFriendlyTimestamp(String friendlyTimestamp) {
        this.friendlyTimestamp = friendlyTimestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public List<CategoryScore> getCategoryScores() {
        return categoryScores;
    }
    public void setCategoryScores(List<CategoryScore> categoryScores) {
        this.categoryScores = categoryScores;
    }

}
