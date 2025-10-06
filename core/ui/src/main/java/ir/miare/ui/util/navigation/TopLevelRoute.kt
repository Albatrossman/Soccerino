package ir.miare.ui.util.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ir.miare.ui.R
import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelRoute(
    @StringRes val labelResId: Int,
    @DrawableRes val selectedIconResId: Int,
    @DrawableRes val unselectedIconResId: Int,
) {

    @Serializable
    data object Ranking : TopLevelRoute(
        labelResId = R.string.title_ranking,
        selectedIconResId = R.drawable.ic_primary_rank_bulk_24,
        unselectedIconResId = R.drawable.ic_primary_rank_twotone_24
    )

    @Serializable
    data object Following : TopLevelRoute(
        labelResId = R.string.title_following,
        selectedIconResId = R.drawable.ic_primary_star_bulk_24,
        unselectedIconResId = R.drawable.ic_primary_star_twotone_24
    )

}