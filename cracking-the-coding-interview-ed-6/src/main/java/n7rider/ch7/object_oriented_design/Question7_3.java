package n7rider.ch7.object_oriented_design;

import java.util.Map;

/**
 * Jukebox: Design a musical jukebox using object-oriented principles.
 *
 * After finishing:
 * Not sure what kind of jukebox this is - play a song for a coin type, or an app, or a home music player?
 * Assuming it's of the first type
 *
 *
 * After comparing with solution:
 * The solution assumed the user will choose songs and pay later, so it added PlayLists, and Credit information.
 *
 * In that case:
 * SongBrowser will let user add songs,
 * then instead of CoinSlot, PaymentSystem would show up asking for payment.
 * Rest can be the same.
 */
public class Question7_3 {
    class Jukebox {
        // Singleton

        Song[] songs;
        Metadata[] metadata;
        AlbumArt[] albumArts;
        CoinSlot coinSlot;
        PlaybackEngine playbackEngine;

        // Constructor or Maintenance mode
        // create dictionary of metadata

        SongBrowser songBrowser;
    }

    abstract class SongBrowser {
        abstract Song[] showBy(String metadataType);
    }

    class Song {
        byte[] audio;
        Metadata metadata;
    }

    class Metadata {
        String songName;
        String artistName;
        String albumName;
        int year;
        String albumArtKey;
    }

    class AlbumArt {
        Map<String, byte[]> albumArtDictionary;
    }

    abstract class PlaybackEngine {
        abstract void play(Song song);
        abstract void setVolume(int volumeLevel);
    }

    abstract class CoinSlot {
        abstract boolean acceptAndValidateCoin(Coin coin);
    }

    class Coin {
        int value;
        Map<String, String> coinData;
    }
}

/**
 * Components:
 * Jukebox // A singleton
 * Song
 * Metadata: Album Name, Artist Name, Genre, Year, Album Art Link
 *
 * AlbumArtRepository
 * CoinSlot
 * CoinValidator
 * PlaybackEngine
 * SongRepository
 *
 * Fields:
 * Jukebox // Singleton
 *  CoinSlot // Singleton
 *      receiveInput +  Validate
 *  PlaybackEngine // Singleton
 *      play
 *      pause
 *      change-volume-level
 *  Song[]
 *      Audio
 *      Album Art Link/Index
 *      Artist Name
 *      Genre
 *      Year
 *      Album Art Link
 *  AlbumArt[]
 *      Index
 *      Image
 *  Display
 *  MetadataDict
 *
 *
 *
 * Interaction:
 *
 * Insert coin -
 *  coinslot.receiveInput
 *  display.show
 *      sortBy() // have a database/dict of songs sorted by each metadata
 *      play()
 *  playbackEngine.play()
 *
 *
 *  sortBy()
 *    for each song
 *       for each metadata
 *          upsert to table_metadata_name - Song // Can be table or map
 *
 */