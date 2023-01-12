package me.redtea.namesmanager.schema.user.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.redtea.namesmanager.domain.UserDto
import me.redtea.namesmanager.schema.user.UserSchemaStrategy
import java.nio.file.Path
import java.util.*
import kotlin.io.path.*

class UserJsonSchemaStrategy(private val dataDir: Path) :
    UserSchemaStrategy {
    private val files = mutableMapOf<UUID, Path>()
    private val json = Json { prettyPrint = true }

    init {
        init()
    }

    private fun loadFromDir() {
        if (dataDir.notExists()) dataDir.createDirectory();
        dataDir.toFile().listFiles()?.forEach {
            val uuid = UUID.fromString(it.name.replace(".json", ""))
            files[uuid] = it.toPath()
        }
    }

    private fun userFromFile(uuid: UUID): UserDto = userFromFile(files[uuid]!!)

    private fun userFromFile(file: Path): UserDto = json.decodeFromString(file.readText())

    override fun all(): Collection<UserDto> = files.values.map(::userFromFile)


    override fun get(key: UUID): UserDto = userFromFile(key)

    override fun insert(value: UserDto) {
        val file = files[value.uuid] ?: dataDir.resolve("${value.uuid}.json")
        file.writeText(json.encodeToString(value))
    }

    override fun remove(key: UUID) {
        files[key]?.deleteIfExists()
    }

    override fun reload() {
        close()
        init()
    }

    override fun init() {
        loadFromDir()
    }

    override fun close() {
        files.clear()
    }
}