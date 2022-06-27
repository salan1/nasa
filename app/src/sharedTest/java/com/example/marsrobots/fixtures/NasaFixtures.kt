import com.example.api.dto.*
import com.example.storage.entities.NasaEntity
import org.threeten.bp.Instant

val dataItemDto = NasaDataItemDto(
    nasaId = "some-id",
    description = "description",
    title = "title",
    dateCreated = Instant.ofEpochMilli(1225445171924),
    mediaType = "mediaType",
    center = "center"
)

val linkDto = NasaItemLinkDto(
    href = "some-link",
    rel = "rel",
    render = "render"
)

val nasaItemDto = NasaItemDto(
    href = "href",
    data = listOf(dataItemDto),
    links = listOf(linkDto)
)


val collectionDto = CollectionDto(
    version = "",
    href = "",
    items = listOf(nasaItemDto),
    links = listOf()
)

val nasaDto = NasaDto(collection = collectionDto)

val nasaEntity = NasaEntity(
    id = dataItemDto.nasaId,
    title = dataItemDto.title,
    description = dataItemDto.description,
    date = dataItemDto.dateCreated,
    imageUrl = linkDto.href
)
