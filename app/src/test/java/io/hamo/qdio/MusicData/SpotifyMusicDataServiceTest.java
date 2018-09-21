package io.hamo.qdio.MusicData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.music.Artist;
import io.hamo.qdio.music.Track;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.TracksPager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyMusicDataServiceTest {

    SpotifyMusicDataService spotifyMusicDataService;
    @Mock
    SpotifyService spotifyService;

    @Before
    public void setUp() throws Exception {
        spotifyMusicDataService = new SpotifyMusicDataService(spotifyService);
    }

    @Test
    public void searchForTrack() throws Exception {

        kaaes.spotify.webapi.android.models.Track tApi = MusicData.getInstance().getTestTrack();
        Track t = new Track(tApi);
        String query = t.getName();
        assertEquals(query, tApi.name);

        TracksPager tracksPager = new TracksPager();
        tracksPager.tracks = new Pager<>();
        tracksPager.tracks.items = Collections.singletonList(tApi);
        when(spotifyService.searchTracks(query)).thenReturn(tracksPager);

        List<Track> call = spotifyMusicDataService.searchForTrack(query).call();
        assertEquals(t, call.get(0));
    }

    @Test
    public void getArtistsFromUris() throws Exception {
        kaaes.spotify.webapi.android.models.Artist aApi = MusicData.getInstance().getTestArtist();
        kaaes.spotify.webapi.android.models.Artist bApi = MusicData.getInstance().getTestArtist();
        Artist a = new Artist(aApi);
        Artist b = new Artist(bApi);
        String aURI = a.getURI();
        String bURI = b.getURI();
        when(spotifyService.getArtist(aURI)).thenReturn(aApi);
        when(spotifyService.getArtist(bURI)).thenReturn(bApi);

        Map<String, Artist> gotFromApi = spotifyMusicDataService.getArtistsFromUris(Arrays.asList(aURI, bURI)).call();
        assertTrue(gotFromApi.containsKey(aURI));
        assertTrue(gotFromApi.containsKey(bURI));
        Artist aFromApi = gotFromApi.get(aURI);
        Artist bFromApi = gotFromApi.get(bURI);

        assertEquals(aFromApi, a);
        assertEquals(bFromApi, b);
        assertNotEquals(aFromApi, b);
        assertNotEquals(bFromApi, a);

    }

    @Test
    public void getArtist() throws Exception {
        kaaes.spotify.webapi.android.models.Artist apiArtist = MusicData.getInstance().getTestArtist();
        Artist a = new Artist(apiArtist);
        String  aURI = a.getURI();
        when(spotifyService.getArtist(aURI)).thenReturn(apiArtist);
        Artist fromService = spotifyMusicDataService.getArtist(aURI).call();

        assertEquals(a, fromService);
    }

    @Test
    public void getAlbumFromUri() {
    }
}