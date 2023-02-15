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
package io.bssw.psip;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import io.bssw.psip.backend.model.ProviderConfiguration;
import io.bssw.psip.backend.service.ProviderService;

/*
 * This class is responsible for configuring the Spring Boot OAuth2 client registration.
 * Insted of reading the registration and provider information from the application.properties,
 * which only allows one registration per provider, we want to be able to read it from a 
 * `repository.yml` file that allows multiple configurations for each type of provider.
 * 
 * NOTE: all fields in repository.yml are required 
 */
@Configuration
public class ProviderRegistrationConfig {
    @Autowired
	private ProviderService providerService;
    
	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = new ArrayList<>();
		for (ProviderConfiguration configuration : providerService.getProviderConfigurations()) {
            if (!configuration.getClientId().isEmpty()) {
                registrations.add(ClientRegistration.withRegistrationId(configuration.getRegistrationId())
                    .clientId(configuration.getClientId())
                    .clientSecret(configuration.getClientSecret())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(configuration.getRedirectUri())
                    .scope(configuration.getScope())
                    .authorizationUri(configuration.getAuthorizationUri())
                    .tokenUri(configuration.getTokenUri())
                    .userInfoUri(configuration.getUserInfoUri())
                    .userNameAttributeName(configuration.getUserNameAttributeName())
                    .clientName(configuration.getClientName())
                    .build());
            }
		}
        return new InMemoryClientRegistrationRepository(registrations);
    }
}
