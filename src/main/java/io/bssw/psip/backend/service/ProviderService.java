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

import static org.yaml.snakeyaml.env.EnvScalarConstructor.ENV_FORMAT;
import static org.yaml.snakeyaml.env.EnvScalarConstructor.ENV_TAG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.env.EnvScalarConstructor;

import io.bssw.psip.backend.model.ProviderConfiguration;
import io.bssw.psip.backend.model.ProviderContent;

@Service
public class ProviderService {
    private static final String PROVIDER_FILE = "/providers.yml";

    private List<ProviderConfiguration> providerConfigurations;

        /**
     * Get a list of all know repository providers.
     * 
     * @return list of repository providers
     */
    public List<ProviderConfiguration> getProviderConfigurations() {
        if (providerConfigurations == null) {
            InputStream inputStream = getClass().getResourceAsStream(PROVIDER_FILE);
            load(inputStream);
        }
        return providerConfigurations;
    }

    private void load(InputStream inputStream) {
		Yaml yaml = new Yaml(new EnvScalarConstructor(new TypeDescription(ProviderContent.class),
        new ArrayList<TypeDescription>(), new LoaderOptions()));
        yaml.addImplicitResolver(ENV_TAG, ENV_FORMAT, "$");
		try {
			ProviderContent content = yaml.load(inputStream);
			providerConfigurations = content.getProviders();
        } catch (Exception e) {
            providerConfigurations = new ArrayList<>();
        	System.out.println(e.getLocalizedMessage());
        }
	}
}
