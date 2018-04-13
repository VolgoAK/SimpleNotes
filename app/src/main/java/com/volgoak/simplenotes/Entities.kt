package com.volgoak.simplenotes

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

/**
 * Created by alex on 4/12/18.
 */

sealed class BaseEntity

@Entity
class Note(var name : String = "New note",
                var text : String = "",
                @Id var id : Long = 0L,
                var date : Long = System.currentTimeMillis()) : BaseEntity(){
    lateinit var notebook : ToOne<NoteBook>

    override fun toString(): String {
        return "Note name $name text $text"
    }
}

@Entity
class NoteBook(var name : String = "New notebook",
                    @Id var id : Long = 0L) : BaseEntity(){
    @Backlink
    lateinit var notes : ToMany<Note>
}
