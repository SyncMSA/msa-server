package com.syncfitouterapiservice.infra.config.spotify;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Component
@Slf4j
public class SpotifyConfig {

    private final SpotifyApi spotifyApi;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private SpotifyConfig() {
        this.spotifyApi =
                new SpotifyApi.Builder()
                        .setClientId(clientId)
                        .setClientSecret(clientSecret)
                        .build();

    }

    public String generateAccessToken() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | ParseException e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.AUTH_NOT_FOUND);
        } catch (SpotifyWebApiException e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.SPOTIFY_EXCEPTION);
        }

    }
}
