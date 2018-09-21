package io.hamo.qdio.TestUtil;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.Collections;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Track;

public class MusicData {
    private static final MusicData ourInstance = new MusicData();
    private final Faker faker = new Faker();

    public static MusicData getInstance() {
        return ourInstance;
    }

    private MusicData() {
    }

    public Track getTestTrack() {
        Track t = new Track();
        Album album = getTestAlbum();
        t.album = album;
        t.artists = album.artists;
        t.name = faker.howIMetYourMother().catchPhrase();
        t.duration_ms = faker.number().randomNumber();
        t.id = faker.idNumber().valid();
        t.uri = t.id;
        return t;
    }

    public Album getTestAlbum() {
        Album album = new Album();
        album.album_type = "single";
        album.artists = Collections.singletonList((ArtistSimple)getTestArtist());
        album.name = faker.rockBand().name();
        album.id = faker.idNumber().valid();
        album.uri = album.id;
        return album;
    }

    public Artist getTestArtist() {
        Artist artist = new Artist();
        artist.name = faker.artist().name();
        artist.id = faker.idNumber().valid();
        artist.uri = artist.id;
        return artist;
    }
}
