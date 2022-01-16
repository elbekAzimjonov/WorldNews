package com.elbek.worldnews.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "worldNews")
class UserNews:Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = null

    @ColumnInfo(name = "url")
    var url: String? = null

    @Ignore
    constructor(title:String?,description:String?,imageUrl:String?,url:String?){
        this.title = title
        this.description = description
        this.imageUrl = imageUrl
        this.url = url
    }
    constructor()
}
