package com.sd.holidays.util.sealed

sealed class Routes(val route: String) {
    data object Drawer : Routes("drawer")
    data object Info : Routes("info")
    data object LongWeekEnd : Routes("longWeekEnd")
    data object PublicHoliday : Routes("publicHoliday")
    data object NextHoliday7Days : Routes("nextHoliday7Days")
}