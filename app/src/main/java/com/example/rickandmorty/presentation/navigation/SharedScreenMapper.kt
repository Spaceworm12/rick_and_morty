package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.x5.checklist.presentation.*
import ru.x5.core.navigation.SharedScreen
import ru.x5.feature_counters.presentation.CountersScreen
import ru.x5.feature_direct_task.presentation.TaskListScreen
import ru.x5.feature_feedback.FeedbackScreen
import ru.x5.feature_fix_task.presentation.FixTaskScreen
import ru.x5.feature_fix_task.presentation.TaskScreen
import ru.x5.feature_planning.presentation.PlanningScreen
import ru.x5.feature_product_representation.ProductRepresentationScreen
import ru.x5.feature_sb_act.SbActScreen
import ru.x5.feature_subscriptions.SubscriptionScreen


class SharedScreenMapper : (SharedScreen) -> FragmentScreen {

    override fun invoke(screen: SharedScreen): FragmentScreen = when (screen) {
        is SharedScreen.ChecklistPlanning -> PlanningScreen(
            userId = screen.userId,
            date = screen.date,
            addShopByIp = screen.addShopByIp
        )
        is SharedScreen.SelectDate -> Screens.SelectDateScreen(
            currentChecklistDate = screen.currentChecklistDate,
            checklistServerId = screen.checklistServerId,
            resolverId = screen.resolverId
        )
        is SharedScreen.TodoScreen -> TodoChecklistByServerIdScreen(screen.checkListServerId)
        is SharedScreen.DirectorChecklist -> DirectorChecklistScreen(screen.checkListId)
        is SharedScreen.DirectorRcChecklist -> DirectorQuestionsForRcScreen(screen.checkListServerId)
        is SharedScreen.QuestionsRcChecklist -> QuestionsForRcScreen(screen.checkListServerId)
        is SharedScreen.TaskListScreen -> TaskListScreen(screen.openTaskId)
        is SharedScreen.SbActScreen -> SbActScreen(screen.actId)
        is SharedScreen.AcceptChecklistsScreen -> AcceptChecklistsScreen(
            isShowInDrawer = screen.isShowInDrawer,
            openChecklistId = screen.openChecklistId
        )
        is SharedScreen.FeedbackScreen -> FeedbackScreen(userLogin = screen.userLogin)
        SharedScreen.SubscriptionsScreen -> SubscriptionScreen()
        is SharedScreen.ChecklistScreenByServerId -> ChecklistByServerIdScreen(screen.checkListServerId)
        is SharedScreen.ChecklistScreenByLocalId -> ChecklistScreen(screen.checkListId)
        SharedScreen.ProductRepresentationScreen -> ProductRepresentationScreen()
        is SharedScreen.FixTaskListScreen -> FixTaskScreen(openTaskId = screen.openTaskId)
        is SharedScreen.FixTaskScreen -> TaskScreen(task = screen.fixTask)
        is SharedScreen.CountersScreen -> CountersScreen(shopId = screen.shopId)
    }
}
