package br.ufpa.smartufpa.utils

import br.ufpa.smartufpa.models.overpass.Element
import java.util.*
import kotlin.collections.HashMap


object ElementParser {

    private val daysofWeek_ptBr: HashMap<String, String> =
            hashMapOf("Mo" to "Seg",
                    "Tu" to "Ter",
                    "We" to "Qua",
                    "Th" to "Qui",
                    "Fr" to "Sex",
                    "Sa" to "Sáb",
                    "Su" to "Dom"
            )

    fun getName(element: Element): String? {
        element.tags.let {
            return it?.name
        }
    }

    fun getShortName(element: Element): String? {
        element.tags.let {
            return it?.shortName
        }
    }

    fun getLocalName(element: Element): String? {
        element.tags.let {
            return it?.locName
        }
    }

    fun getWebsite(element: Element): String? {
        element.tags.let {
            return it?.website
        }
    }

    fun getOpeningHours(element: Element): HashMap<String, String?>? {
        element.tags.let {
            if (it?.openingHours != null)
                return parseOpeningHours(it.openingHours)
            return null
        }
    }

    private fun parseOpeningHours(openingHours: String?): HashMap<String, String?> {
        val split = openingHours?.split(" ")
        val daysOfWeek = split?.get(0)?.split("-")
        val hours = split?.get(1)?.split("-")

        val openingDay = daysofWeek_ptBr.get(daysOfWeek!![0])
        val closingDay = daysofWeek_ptBr.get(daysOfWeek[1])

        val hashMap: HashMap<String, String?> = hashMapOf(
                Constants.OpeningHours.OPENING_DAY to openingDay,
                Constants.OpeningHours.CLOSING_DAY to closingDay,
                Constants.OpeningHours.OPENING_HOUR to hours!![0],
                Constants.OpeningHours.CLOSING_HOUR to hours[1]
        )

        return hashMap
    }

    private fun getDayOfWeekInPtBr(dayInEn: String): String {
        return daysofWeek_ptBr[dayInEn]!!
    }

    fun getDescription(element: Element): String? {
        element.tags.let {
            return it?.description
        }
    }

}