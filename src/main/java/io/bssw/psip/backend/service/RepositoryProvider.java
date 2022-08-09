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
package io.bssw.psip.backend.service;

import java.io.IOException;
import java.util.Map;

import com.vaadin.flow.component.html.Image;

public interface RepositoryProvider {
    /*
     * Get the name of the provider for display purposes
     */
    String getName();

    /*
     * Get an image that can be used for display purposes
     */
    Image getImage();

    /*
     * Log in using the provider for authorization
     */
    boolean login() throws IOException;

    /*
     * Log out from the provider
     */
    boolean logout();

    /*
     * Get the user attribute map supplied by the provider
     */
    Map<String, String> getUserInfo();

    /*
     * Get the contents of a file from branch 'ref' in a repoistory 'repo' that is owned by 'owner'. 
     * 
     * @Return null if no file exists, otherwise the contents as a string
     */
    String getFile(String owner, String repo, String ref, String path);

    /*
     * Put the string 'content' into a file specified by 'path' on branch 'ref' in a repository 'repo' owned by 'owner'. 
     * If the file does not exist, it will be created. If it does exist, the contents will be replaced with the new value.
     */
    boolean putFile(String owner, String repo, String ref, String path, String message, String content);
}
