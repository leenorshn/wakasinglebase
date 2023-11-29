import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.data.model.toVideoModel
import com.wakabase.CompetitionQuery
import com.wakabase.CompetitionsQuery

data class CompetitionModel(
    val id:String,
    val name:String,
    val owner:UserModel,
    val price:Double,
    val starAt:String,
    val endAt:String,
    val banner:String?,
    val detail:String,
    val isArchived:Boolean,
    val createdAt:Long,
    val participants:List<UserModel> =emptyList(),
    val videos:List<VideoModel> = emptyList(),
)

fun CompetitionsQuery.AllCompetition.toCompetitionModel():CompetitionModel{
    return CompetitionModel(
        id=id,
        name=name,
        owner=UserModel(
            uid = owner.id,
            name=owner.name,
            profilePic = owner.profilePic
        ),
        banner=banner,
        detail = details,
        isArchived=isArchived,
        createdAt=createdAt.toLong(),
        price=price,
        participants = participants.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        videos = videos.map {
           it.toVideoModel()
        },
        endAt = endAt,
        starAt = startAt,
    )
}

fun CompetitionQuery.Competition.toCompetitionModel():CompetitionModel{
    return CompetitionModel(
        id=id,
        name=name,
        owner=UserModel(
            uid = owner.id,
            name=owner.name,
            profilePic = owner.profilePic
        ),
        banner=banner,
        detail = details,
        isArchived=isArchived,
        createdAt=createdAt.toLong(),
        price=price,
        participants = participants.map {
            UserModel(
                uid = it.id,
                name=it.name,
                profilePic = it.profilePic
            )
        },
        videos = videos.map {
            it.toVideoModel()
        },
        endAt = endAt,
        starAt = startAt,
    )
}