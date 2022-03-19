package de.wittenbude.exportify.connectors

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import de.wittenbude.exportify.models.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import org.litote.kmongo.util.KMongoUtil
import org.litote.kmongo.util.ObjectMappingConfiguration
import org.litote.kmongo.util.UpdateConfiguration


object Database {
    //private val connectionString: ConnectionString = ConnectionString("mongodb://localhost:27017/exportify?retryWrites=false")
    private val CLIENT = KMongo.createClient()
    private val DATABASE: MongoDatabase = CLIENT.getDatabase("exportify")
    val EXPORTIFY_USER = DATABASE.getCollection<ExportifyUser>()
    val SPOTIFY_USER = DATABASE.getCollection<SpotifyUser>()
    val PLAYLIST = DATABASE.getCollection<Playlist>()
    val TRACK = DATABASE.getCollection<Track>()
    val ARTIST = DATABASE.getCollection<Artist>()
    val ALBUM = DATABASE.getCollection<Album>()
}

fun configureKMongo() {
    ObjectMappingConfiguration.serializeNull = false
    UpdateConfiguration.updateOnlyNotNullProperties = true
}

fun <T : Any>MongoCollection<T>.upsert(document: T) {
    val id = KMongoUtil.getIdValue(document)
    if (id == null) {
        insertOne(document)
    } else {
        updateOneById(id, document, UpdateOptions().upsert(true))
    }
}



