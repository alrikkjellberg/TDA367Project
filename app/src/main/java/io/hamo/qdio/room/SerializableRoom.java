package io.hamo.qdio.room;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;


public class SerializableRoom {
    private final List<String> queueList = new ArrayList<>();
    private final List<String> historyList = new ArrayList<>();
    private final String currentTrackURI;

    public SerializableRoom(SongQueueList songQueueList,
                            SongHistory historyList,
                            Track currentTrack) {
        for(Track track : songQueueList.getAsList()) {
            queueList.add(track.getURI());
        }
        for(Track track: historyList.getPlaybackHistory()) {
            historyList.add(track);
        }
        currentTrackURI = currentTrack.getURI();
    }

    public List<String> getQueueList() {
        return queueList;
    }

    public List<String> getHistoryList() {
        return historyList;
    }

    public String getCurrentTrackURI() {
        return currentTrackURI;
    }
}
